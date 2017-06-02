package batchfile;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JTextArea;

public class BatchFile extends Thread {
	String urlPath; // 下载文件的地址
	String destFileName;
	long start; // 线程的开始位置
	long end; // 线程的结束位置
	int threadID;
	JTextArea textArea = new JTextArea(); // 创建文本域
	public boolean isDone = false; // 是否下载完毕
	RandomAccessFile random;
	public BatchFile(String urlPath, String destFileName,long nStart, long nEnd, int id, JTextArea textArea) {
		this.urlPath = urlPath;
        this.destFileName = destFileName;
		this.start = nStart;
		this.end = nEnd;
		this.threadID = id;
		this.textArea = textArea;
		try {
			random = new RandomAccessFile(destFileName, "rw"); // 创建随机访问对象，以读/写方式
			random.seek(start); // 定位文件指针到startPosition位置
		} catch (Exception e) { // 捕获异常
			System.out.println("创建随机访问对象出错：" + e.getMessage());
		}
	}
	public void run() { // 实现Thread类的方法
		try {
			URL url = new URL(urlPath); // 根据网址创建URL对象
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection(); // 创建远程对象连接对象
			String sProperty = "bytes=" + start + "-";
			httpConnection.setRequestProperty("RANGE", sProperty);
			textArea.append("\n 线程" + threadID + "下载文件!  请等待...");
			InputStream input = httpConnection.getInputStream();// 获得输入流对象
			byte[] buf = new byte[1024]; // 创建字节数据存储文件的数据
			int splitSpace;
			splitSpace = (int) end - (int) start; // 获得每个线程的间隔
			if (splitSpace > 1024)
				splitSpace = 1024;
			// 读取文件信息
			while (input.read(buf, 0, splitSpace) > 0 && start < end) {
				splitSpace = (int) end - (int) start;
				System.out.println((int) end);
				System.out.println((int) start);
				System.out.println(splitSpace);
				if (splitSpace > 1024)
					splitSpace = 1024;
				textArea.append("\n线程: " + threadID + " 开始位置: " + start
						+ "，  间隔长度: " + splitSpace);
				random.write(buf, 0, splitSpace); // 写入文件
				start += splitSpace; // 开始位置改变
			}
			textArea.append("\n 线程" + threadID + "下载完毕！！");
			random.close(); // 释放资源
			input.close();
			isDone = true;
		} catch (Exception e) { // 捕获异常
			System.out.println("多线程下载文件出错：" + e.getMessage());
		}
	  }
	}