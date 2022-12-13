package controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import client.datacommunication.ClientSocket;
import client.frame.ErrorMessagePanel;
import client.frame.IndexPanel;
import client.frame.MainPanel;
import client.frame.addFriendPanel;
import server.userdb.User;
import server.userdb.UserDAO;

public class Controller {

	private static Controller singleton = new Controller();

	public String username = null;

	public ArrayList<String> userinfo = null;

	public ClientSocket clientSocket;
	
	public String msg = null;

	UserDAO userDAO;

	private Controller() {

		clientSocket = new ClientSocket();

		userDAO = new UserDAO();

	}

	public static Controller getInstance() {

		return singleton;
	}

	//ȸ�������� ���� DB�� ȸ�������� �ִ´�.
	public void insertDB(User user) {

		boolean isInsert = userDAO.insertDB(user);

		if (isInsert) {
			MainPanel mainPanel = new MainPanel(MainPanel.frame);
			MainPanel.frame.change(mainPanel);
			JOptionPane.showMessageDialog(mainPanel, "ȸ������ ����!!!", "ȸ������", JOptionPane.WARNING_MESSAGE);
		} else {
			ErrorMessagePanel errorPanel = new ErrorMessagePanel("ȸ������");
			MainPanel.frame.change(errorPanel);
		}

	}

	//�α����� ���� DB���� ��ġ�ϴ� ������ �ִ��� Ȯ��
	public void findUser(ArrayList<JTextField> userInfos) {

		username = userDAO.findUser(userInfos);

		if (username != null) {
			IndexPanel indexPanel = new IndexPanel();
			MainPanel.frame.change(indexPanel);
			clientSocket.startClient();
			JOptionPane.showMessageDialog(indexPanel, "�α��� ����!!!", "�α���", JOptionPane.WARNING_MESSAGE);
		} else if (username == null) {
			ErrorMessagePanel err = new ErrorMessagePanel("�α���");
			MainPanel.frame.change(err);
		}
	}
	
	//���ټҰ�


	//ģ���߰��� ���� DB�� ģ�������� �ִ��� Ȯ��.
	public void addFriendDB(String friendId) {

		int already = userDAO.findUserInfo(friendId);

		boolean isAdd = false;

		if (already == 0) {
			addFriendPanel addfriendPanel = new addFriendPanel();
			JOptionPane.showMessageDialog(addfriendPanel, "�������� �ʴ� ���̵��Դϴ�.", "����", JOptionPane.WARNING_MESSAGE);
		} else if (already == 1){
			
			isAdd =userDAO.addFriendDB(friendId);
			
			if (isAdd) {
				IndexPanel indexPanel = new IndexPanel();
				MainPanel.frame.change(indexPanel);
				JOptionPane.showMessageDialog(indexPanel, "ģ���߰� ����!", "ģ���߰�", JOptionPane.WARNING_MESSAGE);
			} else {
				ErrorMessagePanel errorPanel = new ErrorMessagePanel("ģ���߰�");
				MainPanel.frame.change(errorPanel);
			}
		}

	}

	//�� ģ�� ����Ʈ�� return
	public ArrayList<String> friendList() {

		ArrayList<String> friends = new ArrayList<String>();
		friends = userDAO.friendList();

		return friends;
	}

}
