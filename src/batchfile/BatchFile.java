package batchfile;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JTextArea;

public class BatchFile extends Thread {
	String urlPath; // �����ļ��ĵ�ַ
	String destFileName;
	long start; // �̵߳Ŀ�ʼλ��
	long end; // �̵߳Ľ���λ��
	int threadID;
	JTextArea textArea = new JTextArea(); // �����ı���
	public boolean isDone = false; // �Ƿ��������
	RandomAccessFile random;
	public BatchFile(String urlPath, String destFileName,long nStart, long nEnd, int id, JTextArea textArea) {
		this.urlPath = urlPath;
        this.destFileName = destFileName;
		this.start = nStart;
		this.end = nEnd;
		this.threadID = id;
		this.textArea = textArea;
		try {
			random = new RandomAccessFile(destFileName, "rw"); // ����������ʶ����Զ�/д��ʽ
			random.seek(start); // ��λ�ļ�ָ�뵽startPositionλ��
		} catch (Exception e) { // �����쳣
			System.out.println("����������ʶ������" + e.getMessage());
		}
	}
	public void run() { // ʵ��Thread��ķ���
		try {
			URL url = new URL(urlPath); // ������ַ����URL����
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection(); // ����Զ�̶������Ӷ���
			String sProperty = "bytes=" + start + "-";
			httpConnection.setRequestProperty("RANGE", sProperty);
			textArea.append("\n �߳�" + threadID + "�����ļ�!  ��ȴ�...");
			InputStream input = httpConnection.getInputStream();// �������������
			byte[] buf = new byte[1024]; // �����ֽ����ݴ洢�ļ�������
			int splitSpace;
			splitSpace = (int) end - (int) start; // ���ÿ���̵߳ļ��
			if (splitSpace > 1024)
				splitSpace = 1024;
			// ��ȡ�ļ���Ϣ
			while (input.read(buf, 0, splitSpace) > 0 && start < end) {
				splitSpace = (int) end - (int) start;
				System.out.println((int) end);
				System.out.println((int) start);
				System.out.println(splitSpace);
				if (splitSpace > 1024)
					splitSpace = 1024;
				textArea.append("\n�߳�: " + threadID + " ��ʼλ��: " + start
						+ "��  �������: " + splitSpace);
				random.write(buf, 0, splitSpace); // д���ļ�
				start += splitSpace; // ��ʼλ�øı�
			}
			textArea.append("\n �߳�" + threadID + "������ϣ���");
			random.close(); // �ͷ���Դ
			input.close();
			isDone = true;
		} catch (Exception e) { // �����쳣
			System.out.println("���߳������ļ�����" + e.getMessage());
		}
	  }
	}