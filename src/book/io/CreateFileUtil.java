package book.io;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class CreateFileUtil {
   
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            //System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            JOptionPane.showMessageDialog(null, "创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            //System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            JOptionPane.showMessageDialog(null, "创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
        	//如果目标文件所在的目录不存在，则创建父目录
            //System.out.println("目标文件所在目录不存在，准备创建它！");
            JOptionPane.showMessageDialog(null, "目标文件所在目录不存在，准备创建它！");
            String ParentFile = file.getParentFile().toString();
            CreateFileUtil.createDir(ParentFile,destFileName,file); //创建此不存在的目录
            
            if(!file.getParentFile().mkdirs()) {
                //System.out.println("创建目标文件所在目录失败！");
                JOptionPane.showMessageDialog(null, "创建目标文件成功！");
                return true;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                //System.out.println("创建单个文件" + destFileName + "成功！");
                JOptionPane.showMessageDialog(null, "创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                //System.out.println("创建单个文件" + destFileName + "失败！");
                JOptionPane.showMessageDialog(null, "创建单个文件" + destFileName + "失败1！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            JOptionPane.showMessageDialog(null, "创建单个文件" + destFileName + "失败！"+ e.getMessage());
            return false;
        }
    }
   
    public static boolean createDir(String destDirName , String destFileName,File file) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            //System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            JOptionPane.showMessageDialog(null, "创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
        	
            //System.out.println("创建目录" + destDirName + "成功！");
            JOptionPane.showMessageDialog(null, "创建目录" + destDirName + "成功！");
            CreateFileUtil.SecondCreateFile(file,destFileName); //目录创建完成后，创建文件
            return true;
        } else {
            //System.out.println("创建目录" + destDirName + "失败！");
            JOptionPane.showMessageDialog(null, "创建目录" + destDirName + "失败！");
            return false;
        }
    }
   
    public static boolean SecondCreateFile(File file, String destFileName){
    	try {
            if (file.createNewFile()) {
                //System.out.println("创建单个文件" + destFileName + "成功！");
                JOptionPane.showMessageDialog(null, "创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                //System.out.println("创建单个文件" + destFileName + "失败2！");
                JOptionPane.showMessageDialog(null, "创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            JOptionPane.showMessageDialog(null, "创建单个文件" + destFileName + "失败！"+ e.getMessage());
            return false;
        }
    	
    }
}
