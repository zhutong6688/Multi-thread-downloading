package book.io;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class CreateFileUtil {
   
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            //System.out.println("���������ļ�" + destFileName + "ʧ�ܣ�Ŀ���ļ��Ѵ��ڣ�");
            JOptionPane.showMessageDialog(null, "���������ļ�" + destFileName + "ʧ�ܣ�Ŀ���ļ��Ѵ��ڣ�");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            //System.out.println("���������ļ�" + destFileName + "ʧ�ܣ�Ŀ���ļ�����ΪĿ¼��");
            JOptionPane.showMessageDialog(null, "���������ļ�" + destFileName + "ʧ�ܣ�Ŀ���ļ�����ΪĿ¼��");
            return false;
        }
        //�ж�Ŀ���ļ����ڵ�Ŀ¼�Ƿ����
        if(!file.getParentFile().exists()) {
        	//���Ŀ���ļ����ڵ�Ŀ¼�����ڣ��򴴽���Ŀ¼
            //System.out.println("Ŀ���ļ�����Ŀ¼�����ڣ�׼����������");
            JOptionPane.showMessageDialog(null, "Ŀ���ļ�����Ŀ¼�����ڣ�׼����������");
            String ParentFile = file.getParentFile().toString();
            CreateFileUtil.createDir(ParentFile,destFileName,file); //�����˲����ڵ�Ŀ¼
            
            if(!file.getParentFile().mkdirs()) {
                //System.out.println("����Ŀ���ļ�����Ŀ¼ʧ�ܣ�");
                JOptionPane.showMessageDialog(null, "����Ŀ���ļ��ɹ���");
                return true;
            }
        }
        //����Ŀ���ļ�
        try {
            if (file.createNewFile()) {
                //System.out.println("���������ļ�" + destFileName + "�ɹ���");
                JOptionPane.showMessageDialog(null, "���������ļ�" + destFileName + "�ɹ���");
                return true;
            } else {
                //System.out.println("���������ļ�" + destFileName + "ʧ�ܣ�");
                JOptionPane.showMessageDialog(null, "���������ļ�" + destFileName + "ʧ��1��");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("���������ļ�" + destFileName + "ʧ�ܣ�" + e.getMessage());
            JOptionPane.showMessageDialog(null, "���������ļ�" + destFileName + "ʧ�ܣ�"+ e.getMessage());
            return false;
        }
    }
   
    public static boolean createDir(String destDirName , String destFileName,File file) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            //System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�Ѿ�����");
            JOptionPane.showMessageDialog(null, "����Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�Ѿ�����");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //����Ŀ¼
        if (dir.mkdirs()) {
        	
            //System.out.println("����Ŀ¼" + destDirName + "�ɹ���");
            JOptionPane.showMessageDialog(null, "����Ŀ¼" + destDirName + "�ɹ���");
            CreateFileUtil.SecondCreateFile(file,destFileName); //Ŀ¼������ɺ󣬴����ļ�
            return true;
        } else {
            //System.out.println("����Ŀ¼" + destDirName + "ʧ�ܣ�");
            JOptionPane.showMessageDialog(null, "����Ŀ¼" + destDirName + "ʧ�ܣ�");
            return false;
        }
    }
   
    public static boolean SecondCreateFile(File file, String destFileName){
    	try {
            if (file.createNewFile()) {
                //System.out.println("���������ļ�" + destFileName + "�ɹ���");
                JOptionPane.showMessageDialog(null, "���������ļ�" + destFileName + "�ɹ���");
                return true;
            } else {
                //System.out.println("���������ļ�" + destFileName + "ʧ��2��");
                JOptionPane.showMessageDialog(null, "���������ļ�" + destFileName + "ʧ�ܣ�");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("���������ļ�" + destFileName + "ʧ�ܣ�" + e.getMessage());
            JOptionPane.showMessageDialog(null, "���������ļ�" + destFileName + "ʧ�ܣ�"+ e.getMessage());
            return false;
        }
    	
    }
}
