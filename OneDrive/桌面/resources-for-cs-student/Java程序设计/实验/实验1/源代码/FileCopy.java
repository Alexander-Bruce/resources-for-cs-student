import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
    public static boolean flag = true;
    public static void SetTrue(){
        flag = true;
    }
    public static void SetFalse(){
        flag = false;
    }
    public static boolean GetFlag(){
        return flag;
    }
    public static void main(String [] args)
    {
        int index = 0, flag = 0;
        String []origin = new String[3];
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].equals("--#") || args[i].equals("#--")) {
                origin[1] = args[i];
                flag = 1;
                index = i;
                if(i == 0 || i == args.length - 1) flag = 2;
                break;
            }
        }
        if(args.length == 0) System.out.println("您输入的参数不够。至少要有3个参数:源路径、--#、目的目录路径");
        else if(flag == 2) System.out.println("拷贝方向符号只能出现在参数列表的中间，不能出现在两端");
        else if(flag == 0) System.out.println("未出现拷贝方向符号--#或者#--作为拷贝标记的独立参数。");
        else{
            StringBuilder result = new StringBuilder();
            for(int i = 0; i < index; i++)
            {
                if(i != index - 1)
                    result.append(args[i] + " ");
                else
                    result.append(args[i]);
            }
            origin[0] = result.toString();
            result.setLength(0);
            for(int i = index + 1; i < args.length; i++)
            {
                if(i != args.length - 1)
                    result.append(args[i] + " ");
                else
                    result.append(args[i]);
            }
            origin[2] = result.toString();
            if(origin[1].equals("--#"))
            {
                File f1 = new File(origin[0]);//source
                File f2 = new File(origin[2]);//destination
                if(f1.exists() && f2.exists()) {
                    if(f2.isDirectory()) {
                        if(formerIsAncestor(f1, f2) == false) {
                            if(copyTo(f1, f2) == true)
                                System.out.println("文件拷贝成功");
                            else
                                System.out.println("文件拷贝过程中出现异常，拷贝不成功或不完全成功");
                        }
                        else
                            System.out.println("源文件夹 " + f1.getPath() +"是目的文件夹 "+ f2.getPath() +"本身或其祖先，会出现无限循环拷贝");
                    }
                    else
                        System.out.println("目地文件夹" + f2.getPath() + "不存在");
                }
                else
                    System.out.println("源文件" + f1.getPath() + "不存在");
            }
            else if(origin[1].equals("#--")) {
                File f1 = new File(origin[2]);
                File f2 = new File(origin[0]);
                if (f1.exists() && f2.exists()) {
                    if(f2.isDirectory()) {
                        if(formerIsAncestor(f1, f2) == false){
                            if(copyTo(f1, f2) == true)
                                System.out.println("文件拷贝成功");
                            else
                                System.out.println("文件拷贝过程中出现异常，拷贝不成功或不完全成功");
                        }
                        else
                            System.out.println("源文件夹 " + f1.getPath() +"是目的文件夹 "+ f2.getPath() +"本身或其祖先，会出现无限循环拷贝");
                    }
                    else
                        System.out.println("目地文件夹" + f2.getPath() + "不存在");
                }
                else
                    System.out.println("源文件" + f1.getPath() + "不存在");
            }
        }

    }
    private static boolean formerIsAncestor(File f1, File f2) {
        if(f2 == null)
            return false;
        if(f1.equals(f2.getParentFile()) || f1.equals(f2)) {
            return true;
        }
        else {
            return formerIsAncestor(f1, f2.getParentFile());
        }
    }
    private static boolean copyTo(File source, File destDir) {
        String destPath = destDir.toString();
        copyFile(source, destPath);
        return GetFlag();
    }

    public static void copyFile(File source, String currentTargetPath) {
        String TargetDestination = currentTargetPath + "/" + source.getName();
        File TargetDestinationFile = new File(TargetDestination);
        if(source.equals(TargetDestinationFile)){
            SetFalse();
            return;
        }
        else if(GetFlag() == true) {
            File []resourcesFileList = source.listFiles();
            if(source.isDirectory()) {
                String CurrentDestinationPath = currentTargetPath + "/" + source.getName();
                File target = new File(CurrentDestinationPath);
                target.mkdir();
                if(resourcesFileList != null) {
                    for (File resourcefile : resourcesFileList) {
                        copyFile(resourcefile, CurrentDestinationPath);
                    }
                }
            }
            else{
                String destinationPath = currentTargetPath + "/" + source.getName();
                 copyTargetFile(source.toString(),destinationPath);
            }
        }
    }

public static boolean copyTargetFile(String sourcePath, String destinationPath){
        File source = new File(sourcePath);
        File target = new File(destinationPath);
        try(FileInputStream reader = new FileInputStream(source);
            FileOutputStream writer = new FileOutputStream(target)){
            byte [] inputbuffer = new byte[2048];
            int length;
            while((length = reader.read(inputbuffer)) > 0)
            {
                writer.write(inputbuffer, 0,length);
            }
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
}

}