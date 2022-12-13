package client.datacommunication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalTime;

import javax.swing.JOptionPane;

import client.frame.ChatWindowFrame;
import client.frame.ChatWindowPanel;
import client.frame.IndexPanel;
import controller.Controller;
import server.datacommunication.Message;

public class ClientSocket {

	Socket socket;

	public void startClient() {
		
		Thread thread = new Thread(() -> {
			try {
				socket = new Socket(); // ���� ����
				socket.connect(new InetSocketAddress("localhost", 5000)); // ���� ��û
				System.out.println("���� ��û");
				// -> socket ���� �� ���� ��û
			} catch (IOException e) {
				System.out.println("���� ��� �ȵ�");
				e.printStackTrace();
			}
			receive();
		});

		thread.start();

	}

	public void stopClient() {

		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
		}
	}

	// �������� ���� �����͸� �޴� ����
	public void receive() {

		while (true) {
			// ���� ����
			byte[] recvBuffer = new byte[1024];
			// �����κ��� �ޱ� ���� �Է� ��Ʈ�� ����
			InputStream inputStream;
			try {
				inputStream = socket.getInputStream();
				int readByteCount = inputStream.read(recvBuffer);
				if (readByteCount == -1) {
					throw new IOException();
				}
				
				// �޼��� ���·� ��ȯ
				Message message = toMessage(recvBuffer, Message.class);
				Controller controller = Controller.getInstance();

				
				if (message.getMessageType().equals("request")
						&& message.getReceiveFriendName().equals(controller.username)) {
					// �޼��� Ÿ���� '��û' �̸鼭, ���ΰ� �� �޼����� ��� 
					
					// ��, �ƴϿ��� ������ �� �ְԲ� Dialog�� ����
					int result = JOptionPane.showConfirmDialog(null, message.getSendUserName() + "�԰� ��ȭ�Ͻðڽ��ϱ�?",
							"��ȭ ��û", JOptionPane.YES_NO_OPTION);

					if (result == JOptionPane.YES_OPTION) {
						// '��'�� ������ ��� ��� ��ȭâ�� ����, ��û�� �� ���濡�� ���� �޼����� ������
						Message reply = new Message(message.getReceiveFriendName(), "��ȭ ����", LocalTime.now(), "accept",
								message.getSendUserName());
						send(reply);

						Message smessage = new Message(controller.username, controller.username + "���� �����Ͽ����ϴ�.",
								LocalTime.now(), "text", message.getSendUserName());

						ChatWindowPanel c = new ChatWindowPanel(null, message.getSendUserName());
						new ChatWindowFrame(c, controller.username);
						IndexPanel.chatPanelName.add(c);

						controller.clientSocket.send(smessage);

					} else {
						// '�ƴϿ�'�� ������ ���, ��û�� �� ���濡�� ���� �޼����� ������
						Message reply = new Message(controller.username, "reject", LocalTime.now(), "reject",
								message.getSendUserName());
						send(reply);
					}
				} else if (message.getMessageType().equals("accept")
						&& message.getReceiveFriendName().equals(controller.username)) {
					// �޼��� Ÿ���� '����'�̸鼭, ���ο��� �� �޼����� ��� ��ȭâ�� ����
					Message smessage = new Message(controller.username, controller.username + "���� �����Ͽ����ϴ�.",
							LocalTime.now(), "text", message.getSendUserName());

					ChatWindowPanel c = new ChatWindowPanel(null, message.getSendUserName());
					new ChatWindowFrame(c, controller.username);
					IndexPanel.chatPanelName.add(c);

					controller.clientSocket.send(smessage);
				} else if (message.getMessageType().equals("reject")
						&& message.getReceiveFriendName().equals(controller.username)) {
					// �޼��� Ÿ���� '����'�̸鼭, ���ο��� �� �޼����� ���
					JOptionPane.showMessageDialog(null, "������ ��ȭ�� �����Ͽ����ϴ�");
				} else {
					// �Ϲ����� ��ȭ �޼����� ���
					ChatWindowPanel.displayComment(message);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Message toMessage(byte[] recvBuffer, Class<Message> class1) {

		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(recvBuffer);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} catch (Exception e) {
		}
		return class1.cast(obj);
	}

	// ������ �޽����� ������ ����
	public void send(Message messageInfo) {

		Thread thread = new Thread(() -> {
			// ��ü�� byte array�� ��ȯ
			byte[] bytes = null;
			ByteArrayOutputStream bos = new ByteArrayOutputStream(); // ����Ʈ �迭�� �����͸� ������ϴµ� ���Ǵ� ��Ʈ��.
																		// �����͸� �ӽ÷� ����Ʈ �迭�� ��Ƽ� ��ȯ �� �۾� ���
			try {
				ObjectOutputStream oos = new ObjectOutputStream(bos); // ��ü�� ����ȭ.
				oos.writeObject(messageInfo); // ��ü�� ����ȭ�ϱ� ���� �޼ҵ� ���
				oos.flush(); // ���ۿ� �ܷ��ϴ� ��� ����Ʈ ���
				oos.close();
				bos.close();
				bytes = bos.toByteArray(); // byteArray�� ��ȯ
			} catch (IOException e) {
			}
			// message��ü�� byte�� ��ȯ �� ������ ���� ����
			try {
				byte[] data = bytes;
				OutputStream outputStream = socket.getOutputStream(); // ��� ��Ʈ�� ���.
				outputStream.write(data);
				outputStream.flush();
				System.out.println("������ ������ �Ϸ�!");
			} catch (IOException e) {
				System.out.println("������ ��� �ȵ�");
				e.printStackTrace();
			}
		});
		thread.start();
	}
}
