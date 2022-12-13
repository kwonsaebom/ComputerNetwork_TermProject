/**
 * ģ�� ����Ʈ.
 * �� ģ���� ���̵� �ش��ϴ� �̸��� ������ JButton�� �̿��ؼ� ������  
 */
package client.frame;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.Controller;
import server.datacommunication.Message;
import util.ColorSet;
import util.UseImageFile;
import util.UserProfileButton;
import java.awt.Color;

@SuppressWarnings("serial")
public class FriendListPanel extends JPanel {

	private ArrayList<String> friends; // ģ���� �̸� ����

	private ArrayList<ImageIcon> friendIcons = new ArrayList<ImageIcon>(); // ģ���� ������ �̹��� ����

	public static ArrayList<JButton> friendButtons = new ArrayList<JButton>(); // ģ���� ���� ��ư ����

	private final int FRIEND_PROFILE_IMG_MAX = 8;

	private final int FRIEND_PROFILE_IMG_MIN = 1;

	public FriendListPanel() {

		setBackground(new Color(255, 215, 0));
		Controller controller = Controller.getInstance();
		friends = controller.friendList();
		int friendNum = friends.size();
		
		//ģ���� 0�ΰ�� "ģ���� �����ϴ�" ���
		if (friendNum == 0) {
			setLayout(new GridLayout(1, 0));
			Random rand = new Random();
			int randomNum = rand.nextInt((FRIEND_PROFILE_IMG_MAX - FRIEND_PROFILE_IMG_MIN) + FRIEND_PROFILE_IMG_MIN)
					+ 1;
			Image img = UseImageFile.getImage("C:/Users/KSB/Desktop/java/src/Image/profile" + randomNum + ".png");
			ImageIcon imageIcon = new ImageIcon(img);
			UserProfileButton userprofileButton = new UserProfileButton(imageIcon);
			userprofileButton.setText("ģ���� �����ϴ�.");
			add(userprofileButton);
			friendIcons.add(imageIcon);
			friendButtons.add(userprofileButton);
        
		} else {
			setLayout(new GridLayout(friendNum, 0));
			for (int index = 0; index < friendNum; index++) {
				Random rand = new Random();
				int randomNum = rand.nextInt((FRIEND_PROFILE_IMG_MAX - FRIEND_PROFILE_IMG_MIN) + FRIEND_PROFILE_IMG_MIN)
						+ 1;
				Image img = UseImageFile.getImage("C:/Users/KSB/Desktop/java/src/Image/profile" + randomNum + ".png");
				ImageIcon imageIcon = new ImageIcon(img);
				UserProfileButton userprofileButton = new UserProfileButton(imageIcon);
				userprofileButton.setText(friends.get(index));
				add(userprofileButton);
				friendIcons.add(imageIcon);
				friendButtons.add(userprofileButton);
			}
			for (int i = 0; i < friendNum; i++) {
                friendButtons.get(i).putClientProperty("page", i);
                friendButtons.get(i).addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // ģ���� �������� Ŭ���Ͽ��� ��,

                        // ģ���� ������ �����´�
                        int idx = (Integer) ((JButton) e.getSource()).getClientProperty("page");

                        // '��û' �޼����� ������ 
                        Message message = new Message(controller.username, "request", LocalTime.now(), "request",
                                friends.get(idx));

                        Controller controller = Controller.getInstance();
                        controller.clientSocket.send(message);
                    }
                });
            }

		}

	}

}
