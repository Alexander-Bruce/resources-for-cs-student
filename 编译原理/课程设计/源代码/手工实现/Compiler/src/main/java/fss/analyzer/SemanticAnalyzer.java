package fss.analyzer;

import fss.exception.SemanticErrorException;
import fss.executor.CommandExecutor; // 用于深度检查

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 在执行前对解析后的命令进行语义分析
 * 检查内容包括：文件存在性、类型正确性、权限
 * 以及命令特定的约束条件
 */
public class SemanticAnalyzer {

    private final Path currentDirectory;  // 当前工作目录
    private final CommandExecutor executor; // 用于SOURCE命令的递归深度检查
    private final int lineNumber;        // 错误报告时的行号

    public SemanticAnalyzer(Path currentDirectory, CommandExecutor executor, int lineNumber) {
        this.currentDirectory = currentDirectory;
        this.executor = executor; // 存储executor引用
        this.lineNumber = lineNumber;
    }

    /**
     * 解析路径字符串（相对路径或绝对路径）
     * @param pathStr 路径字符串
     * @return 解析后的Path对象
     */
    private Path resolvePath(String pathStr) {
        if (pathStr == null || pathStr.isEmpty()) {
            // 处理意外传入空路径的情况
            throw new SemanticErrorException("Internal Error: Path string is null or empty.", lineNumber);
        }
        Path inputPath = Paths.get(pathStr);

        if (inputPath.isAbsolute()) {
            return inputPath.normalize();
        } else {
            return currentDirectory.resolve(inputPath).normalize();
        }
    }

    private void reportError(String message) {
        throw new SemanticErrorException(message, lineNumber);
    }

    // --- 语义检查方法 ---

    public void checkPwd() {
        // pwd命令本身不需要语义检查，语法检查由解析器处理
        return;
    }

    public void checkLs(Set<String> options, String pathStr) {
        Path targetPath;
        String displayPath = (pathStr != null && !pathStr.isEmpty()) ? pathStr : "."; // 错误信息显示的路径

        // 检查路径是否存在
        if (pathStr == null || pathStr.isEmpty()) {
            // 路径不存在默认当前目录
            targetPath = currentDirectory;
        } else {
            // 路径存在解析路径
            targetPath = resolvePath(pathStr);
        }

        // 查看目录是否存在
        if (!Files.exists(targetPath)) {
            reportError("ls: cannot access '" + displayPath + "': No such file or directory");
        }

        // 查看是不是目录并且可读
        if (Files.isDirectory(targetPath) && !Files.isReadable(targetPath)) {
            reportError("ls: cannot access '" + displayPath + "': is not readable");
        }

        // 检查选项是否合法
        Set<String> validOptions = new HashSet<>(
                List.of("-l", "-a")
        );

        for(String option : options){
            if(!validOptions.contains(option)) {
                reportError("ls: invalid option '" + option + "': is not accepted");
            }
        }
    }

    public void checkCd(String pathStr) {
        // 解析路径
        Path targetPath = resolvePath(pathStr);

        // 查看路径是否存在
        if (!Files.exists(targetPath)) {
            reportError("cd: cannot access '" + pathStr + "': No such file or directory");
        }

        // 查看是否为目录
        if (!Files.isDirectory(targetPath)) {
            reportError("cd: cannot change to '" + pathStr + "': Not a directory");
        }

        // 检查目录可执行性（即是否可进入）
        if (!Files.isExecutable(targetPath)) {
            // 使用文件夹流获取文件夹内内容，检查是否有进入权限
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(targetPath)) {
                // 如果成功则说明有权限进入
            } catch (IOException | SecurityException e) {
                reportError("cd: cannot change to '" + pathStr + "': Permission denied");
            }
        }
    }

    public void checkChmod(String modeStr, String pathStr) {
        // 解析路径
        Path targetPath = resolvePath(pathStr);

        // 查看路径文件是否存在
        if (!Files.exists(targetPath)) {
            reportError("chmod: cannot access '" + pathStr + "': No such file or directory");
        }

        // 验证权限模式格式
        if (modeStr == null || !modeStr.matches("^[0-7]{3,4}$")) {
            reportError("chmod: invalid mode '" + modeStr + "' (must be 3 or 4 octal digits)");
        }
    }

    public void checkMkdir(Set<String> options, String pathStr) {
        // 解析路径
        Path targetPath = resolvePath(pathStr);

        // 查看父路径
        Path parentDir = targetPath.getParent();

        // 检查路径是否存在且不是目录（是文件）
        if (Files.exists(targetPath) && !Files.isDirectory(targetPath)) {
            reportError("mkdir: cannot create directory '" + pathStr + "': A file with that name exists");
        }

        // 检查父目录是否为目录，存在且可写
        if (parentDir != null && Files.exists(parentDir) && !Files.isDirectory(parentDir)) {
            reportError(
                    "mkdir: cannot create directory '" + pathStr + "': Parent path '" + parentDir + "' is not a directory"
            );
        }

        // 检查选项是否正确
        Set<String> validOptions = new HashSet<>(
                List.of("-p")
        );

        for(String option : options){
            if(!validOptions.contains(option)) {
                reportError("ls: invalid option '" + option + "': is not accepted");
            }
        }

    }

    public void checkRm(Set<String> options, List<String> pathStrs) {
        // 检查选项是否正确
        Set<String> validOptions = new HashSet<>(
                List.of("-r", "-f")
        );

        for(String option : options){
            if(!validOptions.contains(option)) {
                reportError("ls: invalid option '" + option + "': is not accepted");
            }
        }

        boolean recursive = options.contains("-r");
        boolean force = options.contains("-f");

        // 检查路径是否为空
        if (pathStrs.isEmpty()) {
            reportError("rm: missing operand"); // 此处作为安全措施
        }

        // 对于每个目标进行分析
        for (String pathStr : pathStrs) {
            // 解析路径
            Path targetPath = resolvePath(pathStr);

            // 看文件是否存在
            if (!Files.exists(targetPath)) {
                if (!force) { // 非强制模式才报错
                    reportError("rm: cannot remove '" + pathStr + "': No such file or directory");
                }
                continue;
            }

            // 如果是目录且非递归模式
            if (Files.isDirectory(targetPath) && !recursive) {
                // 获取所有子项流
                try (Stream<Path> entries = Files.list(targetPath)) {
                    // 如果目录不为空，需要报错
                    if (entries.findFirst().isPresent()) {
                        reportError("rm: cannot remove '" + pathStr + "': Is a non-empty directory (use -r)");
                    }
                } catch (IOException e) {
                    reportError("rm: cannot check directory contents for '" + pathStr + "': " + e.getMessage());
                } catch (SecurityException e) {
                    reportError("rm: cannot check directory contents for '" + pathStr + "': Permission denied");
                }
            }

        }
    }

    public void checkCp(Set<String> options, List<String> sourceStrs, String destStr) {
        // 检查文件选项是否正确
        Set<String> validOptions = new HashSet<>(
                List.of("-r", "-v")
        );

        for(String option : options){
            if(!validOptions.contains(option)) {
                reportError("ls: invalid option '" + option + "': is not accepted");
            }
        }

        boolean recursive = options.contains("-r");
        Path destPath = resolvePath(destStr);

        // 查看源文件是否为空
        if (sourceStrs.isEmpty()) {
            reportError("cp: missing file operand"); // 此处为安全处理，方便下方处理
        }

        // --- 目标路径检查 ---
        boolean destIsDir = Files.isDirectory(destPath);
        boolean destExists = Files.exists(destPath);

        if (sourceStrs.size() > 1) {
            // 多个源文件：目标必须是已存在的目录

            // 目标不存在报错
            if (!destExists) {
                reportError("cp: target '" + destStr + "' is not a directory (and does not exist)");
            }

            // 目标不是目录报错
            if (!destIsDir) {
                reportError("cp: target '" + destStr + "' is not a directory");
            }

            // 目标不可写报错
            if (!Files.isWritable(destPath)) {
                reportError("cp: cannot write to destination directory '" + destStr + "': Permission denied");
            }
        } else if (sourceStrs.size() == 1) {
            // 单个源文件：目标可以是文件或目录
            Path parent = destPath.getParent();

            // 如果父文件不存在、不是文件夹需要报错
            if (parent != null && Files.exists(parent) && !Files.isDirectory(parent)) {
                reportError("cp: cannot create regular file '" + destStr + "': Parent path is not a directory");
            }

            // 如果父文件不存在需要报错
            if (parent != null && !Files.exists(parent)) {
                reportError("cp: cannot create regular file '" + destStr + "': Parent directory '" + parent + "' does not exist");
            }
        }

        // --- 源文件检查 ---
        for (String sourceStr : sourceStrs) {
            // 路径解析
            Path sourcePath = resolvePath(sourceStr);
            String displaySource = sourceStr; // 错误信息显示的路径

            // 源文件不存在报错
            if (!Files.exists(sourcePath)) {
                reportError("cp: cannot stat '" + displaySource + "': No such file or directory");
            }

            // 源文件不可写报错
            if (!Files.isReadable(sourcePath)) {
                reportError("cp: cannot open '" + displaySource + "' for reading: Permission denied");
            }

            // 检查是否尝试复制目录但未使用-r选项
            if (Files.isDirectory(sourcePath) && !recursive) {
                reportError("cp: omitting directory '" + displaySource + "' (use -r option)");
            }

            // 确定实际目标路径，方便文件夹对比
            Path actualDestPath = destIsDir ? destPath.resolve(sourcePath.getFileName()) : destPath;

            // 检查是否复制到自身
            try {
                // 源文件和目标文件进行对比，查看相似度
                if (Files.exists(actualDestPath) && Files.isSameFile(sourcePath, actualDestPath)) {
                    reportError("cp: '" + displaySource + "' and '" + destStr + "' are the same file");
                }
            } catch (IOException e) {
                reportError("cp: error checking paths '" + displaySource + "' and '" + destStr + "': " + e.getMessage());
            }

            // 检查是否将目录复制到自身子目录（通过startwith对比路径）
            if (Files.isDirectory(sourcePath) && recursive && actualDestPath.startsWith(sourcePath)) {
                reportError("cp: cannot copy a directory, '" + displaySource + "', into itself, '" + actualDestPath + "'");
            }
        }
    }

    public void checkMv(Set<String> options, List<String> sourceStrs, String destStr) {
        // 检查选项是否错误
        Set<String> validOptions = new HashSet<>(
                List.of("-v", "-f")
        );

        for(String option : options){
            if(!validOptions.contains(option)) {
                reportError("ls: invalid option '" + option + "': is not accepted");
            }
        }

        boolean force = options.contains("-f");
        Path destPath = resolvePath(destStr);

        // 查看源文件是否为空
        if (sourceStrs.isEmpty()) {
            reportError("mv: missing file operand"); // 应由解析器捕获
        }

        // --- 目标路径检查 ---
        boolean destIsDir = Files.isDirectory(destPath);
        boolean destExists = Files.exists(destPath);

        if (sourceStrs.size() > 1) {
            // 多个源文件：目标必须是已存在的目录

            // 目标不存在报错
            if (!destExists) {
                reportError("mv: target '" + destStr + "' is not a directory (and does not exist)");
            }

            // 目标不是目录报错
            if (!destIsDir) {
                reportError("mv: target '" + destStr + "' is not a directory");
            }

            // 目标不可写报错
            if (!Files.isWritable(destPath)) {
                reportError("mv: cannot write to destination directory '" + destStr + "': Permission denied");
            }
        } else if (sourceStrs.size() == 1) {
            // 单个源文件：目标可以是文件或目录
            Path parent = destPath.getParent();

            // 文件的父目录不存在或不是目录报错
            if (parent != null && Files.exists(parent) && !Files.isDirectory(parent)) {
                reportError("mv: cannot create regular file '" + destStr + "': Parent path is not a directory");
            }

            // 文件的父目录不存在报错
            if (parent != null && !Files.exists(parent)) {
                reportError("mv: cannot create regular file '" + destStr + "': Parent directory '" + parent + "' does not exist");
            }
        }

        // --- 源文件检查及与目标的关系 ---
        for (String sourceStr : sourceStrs) {
            Path sourcePath = resolvePath(sourceStr);
            String displaySource = sourceStr; // 错误信息显示的路径

            // 源文件不存在报错
            if (!Files.exists(sourcePath)) {
                reportError("mv: cannot stat '" + displaySource + "': No such file or directory");
            }

            // 源文件基本读写权限检查
            if (!Files.isReadable(sourcePath)) {
                reportError("mv: cannot read '" + displaySource + "': Permission denied");
            }

            Path sourceParent = sourcePath.getParent();
            // 源文件的父目录不可写报错
            if (sourceParent != null && !Files.isWritable(sourceParent)) {
                reportError("mv: cannot remove source entry for '" + displaySource + "' in '" + sourceParent + "': Permission denied");
            }

            // 确定实际目标路径
            Path actualDestPath = destIsDir ? destPath.resolve(sourcePath.getFileName()) : destPath;

            // 检查是否移动到自身
            try {
                if (Files.exists(actualDestPath) && Files.isSameFile(sourcePath, actualDestPath)) {
                    reportError("mv: '" + displaySource + "' and '" + actualDestPath + "' are the same file");
                }
            } catch (IOException e) {
                reportError("mv: error checking paths '" + displaySource + "' and '" + actualDestPath + "': " + e.getMessage());
            }

            // 检查是否将目录移动到自身子目录（startwith路径判断）
            if (Files.isDirectory(sourcePath) && actualDestPath.startsWith(sourcePath)) {
                reportError("mv: cannot move a directory, '" + displaySource + "', into itself, '" + actualDestPath + "'");
            }

            // 检查是否覆盖非空目录（非强制模式）
            if (Files.isDirectory(actualDestPath)) {
                // 非树形遍历子项
                try (Stream<Path> entries = Files.list(actualDestPath)) {
                    // 查看第一个的父文件夹，是否为空
                    if (entries.findFirst().isPresent() && !force) {
                        reportError("mv: cannot overwrite non-empty directory '" + actualDestPath + "'");
                    }
                } catch (IOException e) {
                    reportError("mv: cannot check contents of destination directory '" + actualDestPath + "': " + e.getMessage());
                } catch (SecurityException e) {
                    reportError("mv: cannot check contents of destination directory '" + actualDestPath + "': Permission denied");
                }
            }
        }
    }

    public void checkRead(String pathStr) {
        Path targetPath = resolvePath(pathStr);

        // 文件不存在报错
        if (!Files.exists(targetPath)) {
            reportError("read: cannot open '" + pathStr + "': No such file or directory");
        }

        // 文件是文件夹报错
        if (Files.isDirectory(targetPath)) {
            reportError("read: cannot read '" + pathStr + "': Is a directory");
        }

        // 文件不可读报错
        if (!Files.isReadable(targetPath)) {
            reportError("read: cannot open '" + pathStr + "': Permission denied");
        }
    }

    public void checkWrite(Set<String> options, String pathStr, String content) {
        Path targetPath = resolvePath(pathStr);
        Path parentDir = targetPath.getParent();

        // 检查父目录
        if (parentDir != null) {
            // 父目录不是文件夹报错
            if (!Files.isDirectory(parentDir)) {
                reportError("write: cannot create file '" + pathStr + "': Parent path '" + parentDir + "' is not a directory");
            }

            // 父目录不可写报错
            if (!Files.isWritable(parentDir)) {
                reportError("write: cannot create file in directory '" + parentDir + "': Permission denied");
            }
        }

        // 检查目标路径本身
        if (Files.exists(targetPath)) {
            // 是文件夹报错
            if (Files.isDirectory(targetPath)) {
                reportError("write: cannot write to '" + pathStr + "': Is a directory");
            }

            // 不可写报错
            if (!Files.isWritable(targetPath)) {
                reportError("write: cannot write to file '" + pathStr + "': Permission denied");
            }
        }

        // 检查选项
        Set<String> validOptions = new HashSet<>(
                List.of("-a")
        );

        for(String option : options){
            if(!validOptions.contains(option)) {
                reportError("ls: invalid option '" + option + "': is not accepted");
            }
        }
    }

    public void checkSource(String scriptPathStr) {
        // 首先检查递归深度
        if (
                executor.getScriptExecutionDepth() >= CommandExecutor.MAX_SCRIPT_DEPTH
        ) {
            reportError("Maximum script inclusion depth (" + CommandExecutor.MAX_SCRIPT_DEPTH + ") exceeded. Possible infinite loop involving '" + scriptPathStr + "'");
        }

        Path scriptPath = resolvePath(scriptPathStr);

        // 文件不存在报错
        if (!Files.exists(scriptPath)) {
            reportError("source: cannot find script '" + scriptPathStr + "'");
        }

        // 不是正常文件报错
        if (!Files.isRegularFile(scriptPath)) {
            reportError("source: '" + scriptPathStr + "' is not a regular file");
        }

        // 不是可读文件报错
        if (!Files.isReadable(scriptPath)) {
            reportError("source: cannot read script '" + scriptPathStr + "': Permission denied");
        }
    }

    public void checkHelp() {
        // 无需语义检查
        return;
    }

    public void checkExit() {
        // 无需语义检查
        return;
    }
}