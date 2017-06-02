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
	private JPanel mainPanel; // ����쳵�������
	private JTextField webField = new JTextField(); // ���ص�ַ���ı���
	private JTextField localFile = new JTextField(); // ���ص����ص��ı���
	private JTextField fileNameField = new JTextField(); // �ļ�����Ӧ���ı���
	private JTextField categoryField = new JTextField(); // �����Ӧ���ı���
	private JButton button = new JButton(); // ���ذ�ť
	private JButton button1 = new JButton(); // ȡ�����ذ�ť
	private JLabel targetLabel = new JLabel(); // Ŀ���ǩ
	private JLabel localLabel = new JLabel(); // ���ص����ر�ǩ
	private JLabel fileName = new JLabel(); // ���ص��ļ���
	private JLabel category = new JLabel(); // ����
	private JLabel content = new JLabel(); // ��Ϣ����
	private JLabel tips = new JLabel(); // ��Ϣ��ʾ
	private JTextArea textArea = new JTextArea(); // ��ʾ���ؼ�¼���ı���
	private String urlPath = new String(); // ���ص�ַ
	private String saveFileAs = new String(); // ���Ϊ
	public FreshDownload() { // ���췽�����г�ʼ��
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			initPanel(); // ���÷�����ʼ�����
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void initPanel() throws Exception { // ��ʼ�����
		mainPanel = (JPanel) this.getContentPane(); // �������
		mainPanel.setLayout(null); // û��������岼��
		this.setSize(new Dimension(480, 420)); // ���Ĵ�С
		this.setLocation(100, 100); // ���λ��
		this.setTitle("ģ������쳵���߳�����"); // ������
		targetLabel.setBounds(new Rectangle(20, 20, 120, 20)); // ��ǩ��λ��
		targetLabel.setText("������ַ�� ");
		webField.setBounds(new Rectangle(100, 20, 250, 20)); // �����ı����λ��
		// ����Ĭ������·��
	webField.setText("http://sw.bos.baidu.com/sw-search-sp/av_aladdin/3fd4b386c05c8/rj_up1994.exe");
		fileName.setText("�ļ�����");
		fileName.setBounds(20, 50, 120, 20);// �ļ�����ǩ��λ��
		fileNameField.setText("javaʵ��.jar");// �ļ��ı�����Ĭ�ϵ�����
		fileNameField.setBounds(100, 50, 120, 20);// �ļ��ı����е�λ��
		category.setText("���ࣺ");
		category.setBounds(20, 80, 120, 20);// �����ǩ��λ��
		categoryField.setText("����");
		categoryField.setBounds(100, 80, 120, 20);// �����ı����е�λ��
		localLabel.setBounds(new Rectangle(20, 110, 120, 20)); // ��ǩ��λ��
		localLabel.setText("���ص��� ");
		localFile.setBounds(new Rectangle(100, 110, 120, 20)); // �����ı����λ��
		localFile.setText("E:\\Downloads");// ����Ĭ�����Ϊ
		button.setBounds(new Rectangle(230, 110, 60, 20)); // ��ť��λ��
		button.setText("����");
		button1.setBounds(new Rectangle(290, 110, 60, 20)); // ��ť��λ��
		button1.setText("ȡ��");
		tips.setBounds(new Rectangle(20, 130, 120, 20)); // ��ǩ��λ��
		tips.setText("��Ϣ��ʾ�� ");
		content.setText("F�̿��ÿռ�71.7GB");
		content.setBounds(100, 130, 280, 20);
		button.addActionListener(new ActionListener() { // ��ť��Ӽ����¼�
					public void actionPerformed(ActionEvent e) {
						getActionPerformed(e); // �����¼�
					}
				});
		// �����л���������彫�ı����������
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(new Rectangle(20, 160, 400, 200)); // ����λ��
		textArea.setEditable(false); // ���ɱ༭
		mainPanel.add(webField, null); // ���ı�����ӵ������
		mainPanel.add(localFile, null); // ���ı�����ӵ������
		mainPanel.add(fileName, null);// ���ļ�����ǩ��ӵ������
		mainPanel.add(fileNameField, null);// ���ļ����ı�����ӵ������
		mainPanel.add(category, null);// �������ǩ��ӵ������
		mainPanel.add(categoryField, null);// �������ı�����ӵ������
		mainPanel.add(targetLabel, null); // ����ǩ��ӵ������
		mainPanel.add(localLabel, null); // ����ǩ��ӵ������
		mainPanel.add(button, null); // �����ذ�ť��ӵ������
		mainPanel.add(button1, null); // ��ȡ����ť��ӵ������
		mainPanel.add(tips, null); // ����ǩ��ӵ������
		mainPanel.add(content, null); // ����ǩ��ӵ������
		mainPanel.add(scrollPane, null); // ����������ӵ������
		urlPath = webField.getText(); // ����ı����е��ı�
		saveFileAs = localFile.getText();// ����ı����е��ı�
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // ����Ĭ�Ϲرղ���
	}
	// ����¼������������������������ļ��Ľ���
	public void getActionPerformed(ActionEvent e) {
		urlPath = webField.getText(); // ���Ŀ���ļ�����ַ
		String thisFileName = fileNameField.getText(); //��ȡ�ļ���
		saveFileAs = localFile.getText(); // ������Ϊ�ĵ�ַ
		content.setText(FreeDiskSpace.free_disk_info(saveFileAs,urlPath));
		String destFileName = saveFileAs + "\\" + thisFileName;
		CreateFileUtil.createFile(destFileName);
		if (urlPath.compareTo("") == 0)
			textArea.setText("������Ҫ���ص��ļ�������ַ");
		else if (saveFileAs.compareTo("") == 0) {
			textArea.setText("�����뱣���ļ�������ַ");
		} else {
			try {
				SearchAndDown downFile = new SearchAndDown(urlPath, destFileName, 5, textArea); // ������ʵ���������ļ�����
				downFile.start(); // ���������ļ����߳�
				textArea.append("���߳�����...");
			} catch (Exception ec) { // �����쳣
				System.out.println("�����ļ�����" + ec.getMessage());
			}
		}
	}
	public static void main(String[] args) { // java��������ڴ�
		FreshDownload frame = new FreshDownload(); // ʵ����������г�ʼ��
		frame.setVisible(true); // ���ô��ڿ���
	}
}



