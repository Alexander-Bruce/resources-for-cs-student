package fss.executor;

import fss.common.Token;
import fss.common.TokenType;
import fss.exception.ScriptException;
import fss.exception.SemanticErrorException;
import fss.exception.SyntaxErrorException;
import fss.lexer.Lexer;
import fss.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * 命令执行器类，负责解析和执行各种文件系统命令。
 * 假定语义检查已由SemanticAnalyzer完成。
 */
public class CommandExecutor {

    // 字段和常量定义
    private int scriptExecutionDepth = 0; // 脚本执行深度
    public static final int MAX_SCRIPT_DEPTH = 10; // 最大脚本深度，公开以便SemanticAnalyzer访问
    private Path currentDirectory; // 当前工作目录
    private boolean shouldExit = false; // 是否退出标志
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd HH:mm"); // 日期格式化

    // 构造函数
    public CommandExecutor() {
        this.currentDirectory = Paths.get(".").toAbsolutePath().normalize(); // 初始化当前目录为绝对路径并规范化
        System.out.println("Initial Working Directory: " + currentDirectory);
    }

    // 访问器方法
    public boolean shouldExit() { return shouldExit; } // 获取退出状态
    public int getScriptExecutionDepth() { return scriptExecutionDepth; } // 获取脚本执行深度
    public Path getCurrentDirectoryPath() { return currentDirectory; } // 获取当前目录路径

    public String getCurrentDirectoryName() { // 获取当前目录名称
        Path current = this.currentDirectory;
        Path name = current.getFileName();
        return Objects.requireNonNullElse(name, current).toString();
    }

    // 私有方法：解析路径
    private Path resolvePath(String pathStr) { // 将输入路径解析为规范化路径
        Path inputPath = Paths.get(pathStr);
        if (inputPath.isAbsolute()) {
            return inputPath.normalize();
        } else {
            return currentDirectory.resolve(inputPath).normalize();
        }
    }

    // --- 命令执行方法（已移除语义检查） ---

    public void executePwd() { // 执行打印工作目录命令
        // 此处无需检查，仅打印当前目录
        System.out.println(currentDirectory);
    }

    public void executeLs(Set<String> options, String pathStr) { // 执行列出目录内容命令
        Path targetPath;
        if (pathStr == null || pathStr.isEmpty()) {
            targetPath = currentDirectory; // 默认使用当前目录
        } else {
            targetPath = resolvePath(pathStr); // 解析目标路径
        }

        boolean showAll = options.contains("-a"); // 是否显示所有文件（包括隐藏文件）
        boolean longFormat = options.contains("-l"); // 是否使用长格式显示

        try {
            if (!Files.isDirectory(targetPath)) {
                // 如果是文件，仅列出其信息
                listPathInfo(targetPath, longFormat);
                return;
            }

            // 是目录，继续列出内容
            System.out.println("Contents of " + targetPath + ":");
            try (Stream<Path> stream = Files.list(targetPath)) {
                stream.filter(p -> showAll || !p.getFileName().toString().startsWith("."))
                        .sorted()
                        .forEach(p -> listPathInfo(p, longFormat));
            } catch (IOException e) {
                // 处理运行时I/O错误
                throw new SemanticErrorException(
                        "ls: Error reading directory contents of '" + targetPath + "': " + e.getMessage()
                );
            } catch (SecurityException e) {
                // 处理运行时权限错误
                throw new SemanticErrorException(
                        "ls: Permission denied while reading directory '" + targetPath + "'"
                );
            }

        } catch (Exception e) {
            // 捕获执行期间的意外错误
            throw new SemanticErrorException(
                    "ls: Unexpected error accessing '" + targetPath + "': " + e.getMessage()
            );
        }
    }

    // 私有方法：列出路径信息
    private void listPathInfo(Path path, boolean longFormat) { // 显示路径的详细信息
        String fileName = path.getFileName().toString();

        if (longFormat) {
            try {
                BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                // 根据属性确定类型（'d'为目录，'-'为文件）
                String type =
                        attrs.isDirectory() ? "d" : (attrs.isSymbolicLink() ? "l" : (attrs.isOther() ? "o" : "-"));

                // 尝试获取POSIX权限（如果可用）
                String permissions = "rwxrwxrwx"; // 默认回退权限
                if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
                    try {
                        Set<PosixFilePermission> posixPerms = Files.getPosixFilePermissions(path);
                        // 例如 "rwxr-xr--"
                        permissions = PosixFilePermissions.toString(posixPerms);
                    } catch (IOException | UnsupportedOperationException e) {
                        // 如果POSIX不支持，忽略并使用未知权限
                        permissions = "?????????";
                    }
                } else {
                    // 基本的Windows/非POSIX权限
                    permissions = (attrs.isDirectory() ? "d" : "-") +
                            (Files.isReadable(path) ? "r" : "-") +
                            (Files.isWritable(path) ? "w" : "-") +
                            (Files.isExecutable(path) ? "x" : "-") + "------"; // 简化表示
                }

                // 获取其他属性
                long linkCount = 1; // 硬编码链接计数以简化
                String owner = "user"; // 硬编码所有者
                String group = "group"; // 硬编码组
                try {
                    owner = Files.getOwner(path).getName();
                    if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
                        group = Files.readAttributes(path, PosixFileAttributes.class).group().getName();
                    }
                } catch (IOException | UnsupportedOperationException e) { /* 忽略 */ }

                String size = String.format("%10d", attrs.size());
                FileTime modTime = attrs.lastModifiedTime();
                String date = DATE_FORMAT.format(new Date(modTime.toMillis()));

                // 简化输出，不包含链接计数
                System.out.printf(
                        "%s%s %s %s %s %s %s%n",
                        type,
                        permissions,
                        owner,
                        group,
                        size,
                        date,
                        fileName
                );

            } catch (IOException e) {
                System.out.printf(
                        "? ? ? ? ? ? %s (Error reading attributes: %s)%n",
                        fileName,
                        e.getMessage()
                );
            }
        } else {
            System.out.println(fileName); // 简单输出文件名
        }
    }

    public void executeCd(String pathStr) { // 执行更改目录命令
        Path targetPath = resolvePath(pathStr);

        try {
            if (!Files.isDirectory(targetPath)) {
                throw new SemanticErrorException(
                        "cd: Internal inconsistency: '" + pathStr + "' is not a directory (should have been caught earlier)."
                );
            }

            currentDirectory = targetPath.toAbsolutePath().normalize(); // 确保是绝对路径并规范化
            System.out.println("Changed directory to: " + currentDirectory);
        } catch (SecurityException e) {
            throw new SemanticErrorException(
                    "cd: Permission denied changing to '" + pathStr + "'"
            );
        } catch (Exception e) {
            throw new SemanticErrorException(
                    "cd: Unexpected error changing to '" + pathStr + "': " + e.getMessage()
            );
        }
    }

    public void executeChmod(String modeStr, String pathStr) { // 执行更改权限命令
        Path targetPath = resolvePath(pathStr);

        String osName = System.getProperty("os.name").toLowerCase();
        boolean isWindows = osName.contains("win");

        try {
            if (isWindows) {
                System.out.println(
                        "Warning: Applying basic read/write/execute flags based on chmod mode on Windows. Full POSIX semantics not supported."
                );

                // 模式格式已由分析器验证
                String ownerModeStr = modeStr.length() == 4 ? modeStr.substring(1, 2) : modeStr.substring(0, 1);
                int ownerMode = Integer.parseInt(ownerModeStr); // 由分析器检查

                File fileIo = targetPath.toFile();
                // 使用ownerOnly=false尝试为所有人设置，接近POSIX意图
                boolean ownerRead = (ownerMode & 4) != 0;
                boolean ownerWrite = (ownerMode & 2) != 0;
                boolean ownerExecute = (ownerMode & 1) != 0;

                // 尝试设置权限 - 在Windows上可能静默失败或部分失败
                boolean success = true;
                success &= fileIo.setReadable(ownerRead, false);
                success &= fileIo.setWritable(ownerWrite, false);
                success &= fileIo.setExecutable(ownerExecute, false);

                if (!success) {
                    System.out.println(
                            "Warning: Setting some permissions on Windows for '" + targetPath + "' may have failed."
                    );
                }
                System.out.println(
                        "Attempted to set basic permissions for '" + targetPath + "' based on mode " + modeStr
                );

            } else if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
                // POSIX系统
                Set<PosixFilePermission> permissions = parsePosixPermissions(modeStr); // 保持解析逻辑
                Files.setPosixFilePermissions(targetPath, permissions);
                System.out.println(
                        "Changed permissions of '" + targetPath + "' to " + modeStr + " (" + PosixFilePermissions.toString(permissions) + ")"
                );
            } else {
                System.out.println(
                        "Warning: chmod command is not fully supported or effective on this non-POSIX, non-Windows system."
                );
            }
        } catch (IllegalArgumentException e) {
            // 应该由分析器捕获，但作为后备
            throw new SemanticErrorException(
                    "chmod: invalid mode '" + modeStr + "' (Internal Error: " + e.getMessage() + ")"
            );
        } catch (IOException | SecurityException e) {
            // 权限设置期间的运行时错误
            throw new SemanticErrorException(
                    "chmod: changing permissions of '" + pathStr + "' failed: " + e.getMessage()
            );
        } catch (UnsupportedOperationException e) {
            throw new SemanticErrorException(
                    "chmod: setting POSIX permissions not supported for '" + pathStr + "' on this filesystem."
            );
        }
    }

    // 私有方法：解析POSIX权限
    private Set<PosixFilePermission> parsePosixPermissions(String modeStr) { // 解析chmod的八进制权限字符串
        if (modeStr == null || !modeStr.matches("^[0-7]{3,4}$")) {
            throw new IllegalArgumentException("Mode must be a 3 or 4 digit octal number (0-7)");
        }

        String relevantMode = modeStr;

        if (modeStr.length() == 4 && modeStr.startsWith("0")) {
            relevantMode = modeStr.substring(1);
        } else if (modeStr.length() == 4) {
            System.out.println(
                    "Warning: Ignoring leading digit in 4-digit mode '" + modeStr + "'. Setuid/setgid/sticky bits not implemented via octal."
            );
            relevantMode = modeStr.substring(1);
        }

        if (relevantMode.length() != 3) {
            throw new IllegalArgumentException("Internal error parsing mode.");
        }
        Set<PosixFilePermission> perms = EnumSet.noneOf(PosixFilePermission.class);
        String owner = relevantMode.substring(0, 1);
        String group = relevantMode.substring(1, 2);
        String others = relevantMode.substring(2, 3);

        addPermissions(
                perms,
                owner,
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.OWNER_EXECUTE
        );
        addPermissions(
                perms,
                group,
                PosixFilePermission.GROUP_READ,
                PosixFilePermission.GROUP_WRITE,
                PosixFilePermission.GROUP_EXECUTE
        );
        addPermissions(
                perms,
                others,
                PosixFilePermission.OTHERS_READ,
                PosixFilePermission.OTHERS_WRITE,
                PosixFilePermission.OTHERS_EXECUTE
        );

        return perms;
    }

    // 私有方法：添加权限
    private void addPermissions(
            Set<PosixFilePermission> perms,
            String octalDigit,
            PosixFilePermission read,
            PosixFilePermission write,
            PosixFilePermission execute
    ) { // 根据八进制数字添加权限

        int digit = Integer.parseInt(octalDigit);
        if ((digit & 4) != 0) perms.add(read);
        if ((digit & 2) != 0) perms.add(write);
        if ((digit & 1) != 0) perms.add(execute);
    }

    public void executeMkdir(Set<String> options, String pathStr) { // 执行创建目录命令
        Path targetPath = resolvePath(pathStr);

        boolean createParent = options.contains("-p");

        if (
            !Files.exists(targetPath.getParent()) &&
            !createParent
        )
            throw new SemanticErrorException(
                    "mkdir: cannot create directory '" +
                    pathStr +
                    "': without option -p while parent directory does not exist."
            );

        try {
            // createDirectories处理父目录创建和已存在的目录
            Files.createDirectories(targetPath);
            System.out.println("Created/Ensured directory: " + targetPath);
        } catch (FileAlreadyExistsException e) {
            // 对于目录，createDirectories不会抛出此异常，但若路径作为文件存在可能抛出
            throw new SemanticErrorException(
                    "mkdir: cannot create directory '" + pathStr + "': Path exists as a file (or unexpected error)."
            );
        } catch (IOException e) {
            throw new SemanticErrorException(
                    "mkdir: cannot create directory '" + pathStr + "': " + e.getMessage()
            );
        } catch (SecurityException e) {
            throw new SemanticErrorException(
                    "mkdir: permission denied to create directory '" + pathStr + "'"
            );
        }
    }

    public void executeRm(Set<String> options, List<String> pathStrs) { // 执行删除命令
        boolean recursive = options.contains("-r"); // 是否递归删除
        boolean force = options.contains("-f"); // 是否强制删除

        int errors = 0;
        List<String> errorMessages = new ArrayList<>();

        for (String pathStr : pathStrs) {
            Path targetPath = resolvePath(pathStr);

            try {
                // 存在性检查，因为force会跳过分析器检查
                if (!Files.exists(targetPath)) {
                    if (force) {
                        continue; // 强制模式下忽略不存在的文件
                    } else {
                        errorMessages.add(
                                "rm: cannot remove '" + pathStr + "': No such file or directory (runtime check)"
                        );
                        errors++;
                        continue;
                    }
                }

                // 目录检查在此处必要，分析器只允许删除空目录并且无需-r
                if (Files.isDirectory(targetPath)) {
                    // 递归删除目录
                    if (recursive) {
                        System.out.println("Recursively removing: " + targetPath);
                        // 构建文件树
                        try (Stream<Path> walk = Files.walk(targetPath)) {
                            walk.sorted(Comparator.reverseOrder())
                                    .forEach(p -> {
                                        try {
                                            Files.delete(p);
                                        } catch (IOException e) {
                                            System.err.println("DEBUG: IOException caught while trying to delete: " + p);
                                            System.err.println("DEBUG: Exception type: " + e.getClass().getName());
                                            System.err.println("DEBUG: Exception message: " + e.getMessage());
                                            throw new RuntimeException(
                                                    "rm: failed to delete '" + p + "': " + e.getMessage(),
                                                    e
                                            );
                                        }
                                    });
                        } catch (RuntimeException e) {
                            errorMessages.add(e.getMessage());
                            errors++;
                            continue;
                        }

                        System.out.println("Removed directory recursively: " + targetPath);
                    } else {
                        // 非递归删除目录（应为空）
                        try {
                            Files.delete(targetPath);
                            System.out.println("Removed empty directory: " + targetPath);
                        } catch (DirectoryNotEmptyException e) { // 只能删除空目录，否则报错
                            errorMessages.add(
                                    "rm: cannot remove '" + pathStr + "': Directory not empty (use -r?)"
                            );
                            errors++;
                        }
                    }
                } else {
                    // 文件删除
                    Files.delete(targetPath);
                    System.out.println("Removed file: " + targetPath);
                }

            } catch (NoSuchFileException e) { // 如果没有-f，不能忽略非强制删除错误
                if (!force) {
                    errorMessages.add(
                            "rm: cannot remove '" + pathStr + "': No such file or directory (runtime check)"
                    );
                    errors++;
                }
            } catch (DirectoryNotEmptyException e) {
                errorMessages.add("rm: cannot remove '" + pathStr + "': Directory not empty");
                errors++;
            } catch (IOException e) {
                errorMessages.add("rm: cannot remove '" + pathStr + "': " + e.getMessage());
                errors++;
            } catch (SecurityException e) {
                errorMessages.add("rm: permission denied to remove '" + pathStr + "'");
                errors++;
            } catch (Exception e) {
                errorMessages.add("rm: unexpected error removing '" + pathStr + "': " + e.getMessage());
                errors++;
            }
        }

        if (errors > 0) {
            // 合并错误报告
            throw new SemanticErrorException(
                    "rm: completed with " + errors + " errors:\n - " + String.join("\n - ", errorMessages)
            );
        }
    }

    public void executeCp(Set<String> options, List<String> sourceStrs, String destStr) { // 执行复制命令
        boolean recursive = options.contains("-r"); // 是否递归复制
        boolean verbose = options.contains("-v"); // 是否详细输出

        Path destPath = resolvePath(destStr);
        int errors = 0;
        List<String> errorMessages = new ArrayList<>();

        boolean destIsDir = Files.isDirectory(destPath); // 在执行时检查目标类型

        // 遍历所有source文件
        for (String sourceStr : sourceStrs) {
            // 解析路径
            Path sourcePath = resolvePath(sourceStr);
            Path actualDestPath = (destIsDir) ? destPath.resolve(sourcePath.getFileName()) : destPath;

            try {
                // 文件不存在报错
                if (!Files.exists(sourcePath)) {
                    throw new NoSuchFileException(
                            "cp: cannot stat '" + sourceStr + "': No such file or directory (runtime check)"
                    );
                }

                copySingleItem(
                        sourcePath,
                        actualDestPath,
                        recursive,
                        verbose,
                        sourceStr,
                        actualDestPath.toString()
                );

            } catch (Exception e) {
                errorMessages.add(
                        "Error copying '" + sourceStr + "' to '" + actualDestPath + "': " + e.getMessage()
                );
                errors++;
            }
        }

        if (errors > 0) {
            throw new SemanticErrorException(
                    "cp: completed with " + errors + " errors:\n - " + String.join("\n - ",
                     errorMessages)
            );
        }
    }

    // 私有方法：复制单个项
    private void copySingleItem(
            Path sourcePath,
            Path actualDestPath,
            boolean recursive,
            boolean verbose,
            String sourceStrForError,
            String destStrForError
    ) throws IOException, SemanticErrorException { // 复制单个文件或目录

        try {
            // 判断是不是文件夹，不用判断-r，语义分析过程已检查
            if (Files.isDirectory(sourcePath)) {
                if (verbose) System.out.println(
                        "Copying directory '" + sourcePath + "' -> '" + actualDestPath + "'"
                );

                // 文件递归拷贝
                copyDirectoryRecursive(sourcePath, actualDestPath, verbose);
            } else {
                if (verbose) System.out.println(
                        "Copying file '" + sourcePath + "' -> '" + actualDestPath + "'"
                );

                // 正常文件拷贝
                Files.copy(
                        sourcePath,
                        actualDestPath,
                        StandardCopyOption.REPLACE_EXISTING, // 替换已经存在文件
                        StandardCopyOption.COPY_ATTRIBUTES
                );
            }
        } catch (FileAlreadyExistsException e) { // 处理无法替换存在文件错误
            throw new SemanticErrorException(
                    "cp: cannot overwrite '" +
                    destStrForError + "' with '" +
                    sourceStrForError + "': File exists (or type conflict)"
            );
        } catch (IOException e) {
            throw new IOException(
                    "cp: error during copy from '" +
                    sourceStrForError + "' to '" +
                    destStrForError + "': " + e.getMessage(),
                    e
            );
        } catch (SecurityException e) {
            throw new SecurityException(
                    "cp: permission denied during copy from '" +
                    sourceStrForError + "' to '" + destStrForError + "'"
            );
        }
    }

    // 私有方法：递归复制目录
    // 因为父函数有额外功能，故提取递归拷贝功能于此
    private void copyDirectoryRecursive(
            Path source,
            Path target,
            boolean verbose
    ) throws IOException { // 递归复制目录及其内容
        try {
            Files.createDirectories(target);
            Files.copy(
                    source,
                    target,
                    StandardCopyOption.COPY_ATTRIBUTES,
                    StandardCopyOption.REPLACE_EXISTING // 替换已经存在的文件
            );

            if (verbose) {
                System.out.println("  mkdir/attrs " + target);
            }

        } catch (FileAlreadyExistsException e) {
            if (!Files.isDirectory(target)) {
                throw new IOException(
                        "Cannot copy directory onto an existing file: " + target
                );
            }
        }

        try (Stream<Path> stream = Files.list(source)) {
            stream.forEach(sourcePath -> {
                Path targetPath = target.resolve(source.relativize(sourcePath));

                try {
                    if (Files.isDirectory(sourcePath)) {
                        if (verbose) System.out.println("  Entering " + sourcePath);

                        // 拷贝递归方法
                        copyDirectoryRecursive(sourcePath, targetPath, verbose);
                    } else {
                        if (verbose) System.out.println("  cp " + sourcePath + " -> " + targetPath);

                        // 普通文件拷贝
                        Files.copy(
                                sourcePath,
                                targetPath,
                                StandardCopyOption.REPLACE_EXISTING, // 替换存在文件
                                StandardCopyOption.COPY_ATTRIBUTES
                        );
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed during recursive copy: " + e.getMessage(), e);
                }
            });
        } catch (IOException | SecurityException e) {
            throw new IOException(
                    "Failed to list source directory for copy: " + source.toString() + ": " + e.getMessage(),
                    e
            );
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            } else {
                throw e;
            }
        }
    }

    public void executeMv(
            Set<String> options,
            List<String> sourceStrs,
            String destStr
    ) { // 执行移动/重命名命令
        boolean force = options.contains("-f"); // 是否强制覆盖
        boolean verbose = options.contains("-v"); // 是否详细输出

        Path destPath = resolvePath(destStr);
        int errors = 0;
        List<String> errorMessages = new ArrayList<>();

        boolean destIsDir = Files.isDirectory(destPath);

        // 遍历源文件
        for (String sourceStr : sourceStrs) {
            Path sourcePath = resolvePath(sourceStr);
            Path actualDestPath = (destIsDir) ? destPath.resolve(sourcePath.getFileName()) : destPath;

            try {
                // 源文件不存在无法移动
                if (!Files.exists(sourcePath)) {
                    throw new NoSuchFileException(
                            "mv: cannot stat '" + sourceStr + "': No such file or directory (runtime check)"
                    );
                }

                // 移动文件
                moveSingleItem(
                        sourcePath,
                        actualDestPath,
                        force,
                        verbose,
                        sourceStr,
                        actualDestPath.toString()
                );

            } catch (Exception e) {
                errorMessages.add(
                        "Error moving '" + sourceStr + "' to '" + actualDestPath + "': " + e.getMessage()
                );
                errors++;
            }
        }

        if (errors > 0) {
            throw new SemanticErrorException(
                    "mv: completed with " + errors + " errors:\n - " + String.join("\n - ", errorMessages)
            );
        }
    }

    // 私有方法：移动单个项
    private void moveSingleItem(
            Path sourcePath,
            Path actualDestPath,
            boolean force,
            boolean verbose,
            String sourceStrForError,
            String destStrForError
    ) throws SemanticErrorException { // 移动单个文件或目录

        CopyOption[] moveOptions = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING
        }; // 简化：目标存在时始终替换

        try {
            // 删除目标路径下面的文件夹内容
            if (Files.isDirectory(actualDestPath)) {

                // 如果强制移动需要覆盖目标目录
                if (force && Files.isDirectory(sourcePath)) {

                    // 非树形遍历目标文件目录，返回直接子项
                    try (Stream<Path> stream = Files.list(actualDestPath)) {
                        // 获取第一个的父文件夹，即当前文件夹，判断是不是空
                        if (stream.findFirst().isPresent()) {

                            System.out.println(
                                    "Warning: Force (-f) removing non-empty destination directory before move: " + actualDestPath
                            );

                            // 树形遍历，删除目标目录下面的所有文件
                            try (Stream<Path> walk = Files.walk(actualDestPath)) {
                                walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                                    try {
                                        Files.delete(p);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                            } catch (RuntimeException e) {
                                throw (IOException) e.getCause();
                            }

                        } else {
                            Files.delete(actualDestPath);
                        }
                    }
                }
            }

            // 文件移动
            Files.move(sourcePath, actualDestPath, moveOptions);
            if (verbose) {
                System.out.println("Moved '" + sourcePath + "' -> '" + actualDestPath + "'");
            }
        } catch (NoSuchFileException e) {
            throw new SemanticErrorException(
                    "mv: source '" + sourceStrForError + "' disappeared before move"
            );
        } catch (FileAlreadyExistsException e) {
            throw new SemanticErrorException(
                    "mv: cannot overwrite '" + destStrForError + "': File exists (or type/permission issue)"
            );
        } catch (DirectoryNotEmptyException e) {
            throw new SemanticErrorException(
                    "mv: cannot overwrite non-empty directory '" + destStrForError + "' (use -f?)"
            );
        } catch (AtomicMoveNotSupportedException e) {// 保证移动完整性
            System.err.println("Warning: Atomic move not supported, falling back (may be non-atomic).");
            try {
                // 非原子移动
                Files.move(
                        sourcePath,
                        actualDestPath,
                        StandardCopyOption.REPLACE_EXISTING
                );

                if (verbose) {
                    System.out.println(
                            "Moved (non-atomically) '" + sourcePath + "' -> '" + actualDestPath + "'"
                    );
                }
            } catch (IOException ioEx) {
                throw new SemanticErrorException(
                        "mv: non-atomic move failed for '" + sourceStrForError + "' to '" + destStrForError + "': " + ioEx.getMessage()
                );
            }
        } catch (IOException e) {
            throw new SemanticErrorException(
                    "mv: cannot move '" + sourceStrForError + "' to '" + destStrForError + "': " + e.getMessage()
            );
        } catch (SecurityException e) {
            throw new SemanticErrorException(
                    "mv: permission denied during move of '" + sourceStrForError + "' to '" + destStrForError + "'"
            );
        }
    }

    public void executeRead(String pathStr) { // 执行读取文件命令
        Path targetPath = resolvePath(pathStr);

        try {
            String content = Files.readString(targetPath);
            System.out.println("--- Content of " + targetPath + " ---");
            System.out.println(content);
            System.out.println("--- End of Content ---");
        } catch (IOException e) {
            throw new SemanticErrorException("read: Error reading file '" + pathStr + "': " + e.getMessage());
        } catch (SecurityException e) {
            throw new SemanticErrorException("read: Permission denied reading file '" + pathStr + "'");
        } catch (OutOfMemoryError e) {
            throw new SemanticErrorException("read: File '" + pathStr + "' is too large to read into memory.");
        }
    }

    public void executeWrite(Set<String> options, String pathStr, String content) { // 执行写入文件命令
        Path targetPath = resolvePath(pathStr);
        boolean append = options.contains("-a"); // 是否追加内容

        StandardOpenOption[] writeOptions;
        if (append) {
            writeOptions = new StandardOpenOption[]{
                    StandardOpenOption.APPEND, // 追加
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE
            };
        } else {
            writeOptions = new StandardOpenOption[]{
                    StandardOpenOption.TRUNCATE_EXISTING, // 覆盖
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE
            };
        }

        try {
            // 在写入前确保父目录存在
            Path parentDir = targetPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                try {
                    // 没有父目录，创建父目录
                    Files.createDirectories(parentDir);
                } catch (IOException ioex) {
                    throw new SemanticErrorException(
                            "write: failed to create parent directory '" + parentDir + "': " + ioex.getMessage()
                    );
                }
            }

            // 文件依据权限写入
            Files.writeString(targetPath, content, writeOptions);
            System.out.println((append ? "Appended" : "Wrote") + " content to: " + targetPath);

        } catch (IOException e) {
            throw new SemanticErrorException(
                    "write: Error writing to file '" + pathStr + "': " + e.getMessage()
            );
        } catch (SecurityException e) {
            throw new SemanticErrorException(
                    "write: Permission denied writing to file '" + pathStr + "'"
            );
        }
    }

    public void executeSource(String scriptPathStr, int sourceCommandline) { // 执行脚本文件命令
        Path scriptPath = resolvePath(scriptPathStr);

        try {
            List<String> lines = Files.readAllLines(scriptPath);

            // 增加递归深度
            this.scriptExecutionDepth++;
            System.out.println("--- Executing script: " + scriptPath + " (Depth: " + scriptExecutionDepth + ") ---");

            // 从第一行调用执行方法进行执行
            executeScriptContent(lines, scriptPath.toString(), 1);

            System.out.println("--- Finished script: " + scriptPath + " ---");

            // 递归深度减少
            this.scriptExecutionDepth--;

        } catch (IOException e) {
            throw new SemanticErrorException(
                    "source: error reading script file '" + scriptPathStr + "': " + e.getMessage(), sourceCommandline
            );
        } catch (SecurityException e) {
            throw new SemanticErrorException(
                    "source: permission denied while reading script '" + scriptPathStr + "': " + e.getMessage(),
                    sourceCommandline
            );
        } catch (Exception e) {
            this.scriptExecutionDepth--; // 错误退出时确保深度减少
            throw e;
        }
    }

    // 私有方法：执行脚本内容
    public void executeScriptContent(
            List<String> lines,
            String scriptName,
            int startingLineNumber
    ) { // 处理脚本内容的递归执行循环

        int currentLineNum = startingLineNumber - 1;

        for (String line : lines) {
            currentLineNum++;
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                continue; // 跳过空行和注释
            }

            try {
                Lexer lexer = new Lexer(trimmedLine);
                List<Token> tokens = lexer.tokenize();

                // Token错误检查，有错误抛出异常，终止执行
                for (Token t : tokens) {
                    if (t.type == TokenType.ERROR) {
                        System.err.println(
                                "Lexer Error in " + scriptName + " on line " + currentLineNum + ": " + t.value
                        );
                        throw new SyntaxErrorException("Lexer error encountered.", currentLineNum);
                    }
                }
                // Token为空跳过
                if (tokens.isEmpty() || tokens.getFirst().type == TokenType.EOF) continue;

                Parser parser = new Parser(tokens, this, currentLineNum);
                parser.parse();

                // 子层调用Exit影响父层
                if (shouldExit) {
                    System.out.println(
                            "EXIT command encountered in " + scriptName + " on line " + currentLineNum + ". Stopping this script."
                    );
                    return;
                }
            } catch(SyntaxErrorException | SemanticErrorException e){
                return;
            } catch(ScriptException e) {
                System.err.println(
                        "Error in " + scriptName + " near line " + currentLineNum + ": " + e.getMessage()
                );
                System.err.println(
                        "Stopping execution of script " + scriptName + " due to error."
                );
                throw e;
            } catch (Exception e) {
                System.err.println(
                        "Runtime Error in " + scriptName + " near line " + currentLineNum + ": " + e.getMessage()
                );
                System.err.println(
                        "Stopping execution of script " + scriptName + " due to runtime error."
                );

                throw new RuntimeException(
                        "Runtime error during script execution: " + e.getMessage(),
                        e
                );
            }
        }
    }

    public void executeHelp() { // 执行帮助命令
        // 不需要检查，仅打印帮助信息
        System.out.println("--- File System Script Help ---");
        System.out.println("Available Commands:");
        System.out.println();
        System.out.println("  Directory Navigation & Information:");
        System.out.println("    PWD                      : Print Working Directory");
        System.out.println("    LS [-la] [path]          : List directory contents (default: current)");
        System.out.println("        -l                   : Use long listing format (POSIX style where possible)");
        System.out.println("        -a                   : Do not ignore entries starting with .");
        System.out.println("    CD <path>                : Change Directory");
        System.out.println();
        System.out.println("  File & Directory Creation/Deletion:");
        System.out.println("    MKDIR [-p] <path>        : Make Directory");
        System.out.println("        -p                   : Create parent directories as needed (default behavior)");
        System.out.println("    RM [-rf] <path> [path...]: Remove files or directories");
        System.out.println("        -r                   : Remove directories and their contents recursively");
        System.out.println("        -f                   : Force (ignore non-existent files, attempt risky operations like overwriting)");
        System.out.println();
        System.out.println("  File & Directory Copying/Moving:");
        System.out.println("    CP [-rv] <source...> <destination> : Copy files or directories");
        System.out.println("        -r                   : Copy directories recursively (required for directories)");
        System.out.println("        -v                   : Verbose - explain what is being done");
        System.out.println("    MV [-fv] <source...> <destination> : Move/Rename files or directories");
        System.out.println("        -f                   : Force (attempt to overwrite non-empty dirs, use with caution)");
        System.out.println("        -v                   : Verbose - explain what is being done");
        System.out.println();
        System.out.println("  File Content & Permissions:");
        System.out.println("    READ <path>              : Read and display file content");
        System.out.println("    WRITE [-a] <path> \"<content>\" : Write content to file");
        System.out.println("        -a                   : Append content to the end of the file");
        System.out.println("    CHMOD <mode> <path>      : Change file permissions (e.g., 755, 644)");
        System.out.println("                             (Note: Behavior depends on OS - POSIX recommended)");
        System.out.println();
        System.out.println("  Scripting & Control:");
        System.out.println("    SOURCE <script_path>     : Execute commands from another script file");
        System.out.println("    HELP                     : Display this help message");
        System.out.println("    EXIT                     : Exit the interpreter or current script");
        System.out.println();
        System.out.println("General Notes:");
        System.out.println("  - Paths or content with spaces must be enclosed in double quotes (\").");
        System.out.println("  - Options (like -r, -f) generally precede path arguments.");
        System.out.println("  - Combined options (e.g., -rf, -la) are supported.");
        System.out.println("  - For CP and MV: If multiple <source> args are given, <destination> MUST be an existing directory.");
        System.out.println("  - Use # at the beginning of a line for comments.");
        System.out.println("-----------------------------");
    }

    public void executeExit() { // 执行退出命令
        // 不需要检查，仅设置退出标志（交互模式使用）
        this.shouldExit = true;
        System.out.println("Exiting requested.");
    }
}