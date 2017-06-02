package download;
import java.awt.AWTEvent;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import batchfile.*;
import book.io.*;
import disk_info.*;
import search_and_down.*;
public class FreshDownload extends JFrame {
	private JPanel mainPanel; // 网络快车的主面板
	private JTextField webField = new JTextField(); // 下载地址的文本框
	private JTextField localFile = new JTextField(); // 下载到本地的文本框
	private JTextField fileNameField = new JTextField(); // 文件名对应的文本框
	private JTextField categoryField = new JTextField(); // 分类对应的文本框
	private JButton button = new JButton(); // 下载按钮
	private JButton button1 = new JButton(); // 取消下载按钮
	private JLabel targetLabel = new JLabel(); // 目标标签
	private JLabel localLabel = new JLabel(); // 下载到本地标签
	private JLabel fileName = new JLabel(); // 下载的文件名
	private JLabel category = new JLabel(); // 分类
	private JLabel content = new JLabel(); // 信息内容
	private JLabel tips = new JLabel(); // 信息提示
	private JTextArea textArea = new JTextArea(); // 显示下载记录的文本域
	private String urlPath = new String(); // 下载地址
	private String saveFileAs = new String(); // 另存为
	public FreshDownload() { // 构造方法进行初始化
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initPanel(); // 调用方法初始化面板
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void initPanel() throws Exception { // 初始化面板
		mainPanel = (JPanel) this.getContentPane(); // 创建面板
		mainPanel.setLayout(null); // 没有设置面板布局
		this.setSize(new Dimension(480, 420)); // 面板的大小
		this.setLocation(100, 100); // 面板位置
		this.setTitle("模仿网络快车多线程下载"); // 面板标题
		targetLabel.setBounds(new Rectangle(20, 20, 120, 20)); // 标签的位置
		targetLabel.setText("下载网址： ");
		webField.setBounds(new Rectangle(100, 20, 250, 20)); // 设置文本框的位置
		// 设置默认下载路径
	webField.setText("http://sw.bos.baidu.com/sw-search-sp/av_aladdin/3fd4b386c05c8/rj_up1994.exe");
		fileName.setText("文件名：");
		fileName.setBounds(20, 50, 120, 20);// 文件名标签的位置
		fileNameField.setText("java实例.jar");// 文件文本框中默认的内容
		fileNameField.setBounds(100, 50, 120, 20);// 文件文本框中的位置
		category.setText("分类：");
		category.setBounds(20, 80, 120, 20);// 分类标签的位置
		categoryField.setText("其他");
		categoryField.setBounds(100, 80, 120, 20);// 分类文本框中的位置
		localLabel.setBounds(new Rectangle(20, 110, 120, 20)); // 标签的位置
		localLabel.setText("下载到： ");
		localFile.setBounds(new Rectangle(100, 110, 120, 20)); // 设置文本框的位置
		localFile.setText("E:\\Downloads");// 设置默认另存为
		button.setBounds(new Rectangle(230, 110, 60, 20)); // 按钮的位置
		button.setText("下载");
		button1.setBounds(new Rectangle(290, 110, 60, 20)); // 按钮的位置
		button1.setText("取消");
		tips.setBounds(new Rectangle(20, 130, 120, 20)); // 标签的位置
		tips.setText("信息提示： ");
		content.setText("F盘可用空间71.7GB");
		content.setBounds(100, 130, 280, 20);
		button.addActionListener(new ActionListener() { // 按钮添加监听事件
					public void actionPerformed(ActionEvent e) {
						getActionPerformed(e); // 调用事件
					}
				});
		// 创建有滑动条的面板将文本域放在上面
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(new Rectangle(20, 160, 400, 200)); // 面板的位置
		textArea.setEditable(false); // 不可编辑
		mainPanel.add(webField, null); // 将文本框添加到面板中
		mainPanel.add(localFile, null); // 将文本框添加到面板中
		mainPanel.add(fileName, null);// 将文件名标签添加到面板中
		mainPanel.add(fileNameField, null);// 将文件名文本框添加到面板中
		mainPanel.add(category, null);// 将分类标签添加到面板中
		mainPanel.add(categoryField, null);// 将分类文本框添加到面板中
		mainPanel.add(targetLabel, null); // 将标签添加到面板中
		mainPanel.add(localLabel, null); // 将标签添加到面板中
		mainPanel.add(button, null); // 将下载按钮添加到面板中
		mainPanel.add(button1, null); // 将取消按钮添加到面板中
		mainPanel.add(tips, null); // 将标签添加到面板中
		mainPanel.add(content, null); // 将标签添加到面板中
		mainPanel.add(scrollPane, null); // 将滑动条添加到面板中
		urlPath = webField.getText(); // 获得文本框中的文本
		saveFileAs = localFile.getText();// 获得文本框中的文本
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 设置默认关闭操作
	}
	// 点击事件触发方法，启动分析下载文件的进程
	public void getActionPerformed(ActionEvent e) {
		urlPath = webField.getText(); // 获得目标文件的网址
		String thisFileName = fileNameField.getText(); //获取文件名
		saveFileAs = localFile.getText(); // 获得另存为的地址
		content.setText(FreeDiskSpace.free_disk_info(saveFileAs,urlPath));
		String destFileName = saveFileAs + "\\" + thisFileName;
		CreateFileUtil.createFile(destFileName);
		if (urlPath.compareTo("") == 0)
			textArea.setText("请输入要下载的文件完整地址");
		else if (saveFileAs.compareTo("") == 0) {
			textArea.setText("请输入保存文件完整地址");
		} else {
			try {
				SearchAndDown downFile = new SearchAndDown(urlPath, destFileName, 5, textArea); // 传参数实例化下载文件对象
				downFile.start(); // 启动下载文件的线程
				textArea.append("主线程启动...");
			} catch (Exception ec) { // 捕获异常
				System.out.println("下载文件出错：" + ec.getMessage());
			}
		}
	}
	public static void main(String[] args) { // java程序主入口处
		FreshDownload frame = new FreshDownload(); // 实例化对象进行初始化
		frame.setVisible(true); // 设置窗口可视
	}
}



