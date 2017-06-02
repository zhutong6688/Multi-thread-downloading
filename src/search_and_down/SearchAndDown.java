package search_and_down;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JTextArea;

import batchfile.BatchFile;

	public class SearchAndDown extends Thread { // �������ص��ļ����������ؽ���
		static String urlPath; // �����ļ��ĵ�ַ
		String destFileName; // �ļ����Ϊ
		int threadCount; // �߳�����
		String log = new String(); // ���ع��̵���־��¼
		JTextArea textArea = new JTextArea(); // �����ı���
		long[] position;
		long[] start; // ÿ���߳̿�ʼλ��
		long[] end; // ÿ���߳̽���λ��
		BatchFile[] file; // ���̶߳���
		long fileLength; // ���ص��ļ��ĳ���
		public SearchAndDown(String urlPath, String destFileName, int threadCount, JTextArea textArea) { // ���췽�����г�ʼ��
			this.urlPath = urlPath;
			this.destFileName = destFileName;
			this.threadCount = threadCount;
			this.textArea = textArea;
			start = new long[threadCount];
			end = new long[threadCount];
		}
		public void run() { // ʵ��Thread��ķ���
			log = "Ŀ���ļ��� " + urlPath;
			textArea.append("\n" + log); // ��־д���ı���
			log = "\n �߳������� " + threadCount;
			textArea.append("\n" + log);
			try {
				fileLength = getSize(); // ����ļ�����
				if (fileLength == -1) { // ���ɻ�ȡ�ļ����Ȼ�û�ҵ���Դ
					textArea.append("\n ����֪���ļ����ȣ������ԣ���");
				} else {
					if (fileLength == -2) { // �޷���ȡ�ļ���û���ҵ���Դ
						textArea.append("\n �ļ��޷���ȡ,û���ҵ�ָ����Դ,�����ԣ���");
					} else { // ѭ����ÿ���̵߳Ŀ�ʼλ�ø�ֵ
						for (int i = 0; i < start.length; i++) {
							start[i] = (long) (i * (fileLength / start.length));
						}
						for (int i = 0; i < end.length - 1; i++)
							// ѭ����ÿ���̵߳Ľ���λ�ø�ֵ
							end[i] = start[i + 1];
						// ���һ�߳̽���λ�����ļ�����
						end[end.length - 1] = fileLength;
						for (int i = 0; i < start.length; i++) { // ѭ����ʾÿ�߳̿�ʼ�ͽ���λ��
							log = "�̣߳�" + i + "���ط�Χ��" + start[i] + "--" + end[i];
							textArea.append("\n" + log);
						}
						file = new BatchFile[start.length];
						for (int i = 0; i < start.length; i++) { // ����һ�����߳�
							file[i] = new BatchFile(urlPath, destFileName,start[i], end[i], i, textArea);
							log = "�߳� " + i + "����";
							textArea.append("\n" + log);
							file[i].start(); // �����߳�
						}
						boolean breakWhile = true;
						while (breakWhile) { // ������ʼ��Ϊtrueʱ����ѭ��
							Thread.sleep(500); // �߳�����
							breakWhile = false;
							for (int i = 0; i < file.length; i++) {
								if (!file[i].isDone) { // ѭ���ж�ÿ���߳��Ƿ����
									breakWhile = true;
									break;
								}
							}
						}
						textArea.append("\n�ļ����������");// �ļ��������
					}
				}
			} catch (Exception ex) { // �����쳣
				ex.printStackTrace();
			}
		}
		public static long getSize() { // ����ļ��ĳ��ȵķ���
			int fileLength = -1;
			try {
				URL url = new URL(urlPath); // ������ַ����URL����
				HttpURLConnection httpConnection = (HttpURLConnection) (url
						.openConnection()); // ����Զ�̶������Ӷ���
				int responseCode = httpConnection.getResponseCode();
				if (responseCode >= 400) { // û�л����Ӧ��Ϣ
					System.out.println("Web��������Ӧ����");
					return -2; // Web��������Ӧ����
				}
				String sHeader;
				for (int i = 1;; i++) { // ���ʶ�ļ������ļ�ͷ���ļ�����
					sHeader = httpConnection.getHeaderFieldKey(i);
					if (sHeader != null) {
						if (sHeader.equals("Content-Length")) {// ���ұ�ʶ�ļ����ȵ��ļ�ͷ
							fileLength = Integer.parseInt(httpConnection
									.getHeaderField(sHeader));
							break;
						}
					} else {
						break;
					}
				}
			} catch (Exception e) { // �����쳣
				System.out.println("�޷�����ļ�����:" + e.getMessage());
			}
			return fileLength;
		}
	}

