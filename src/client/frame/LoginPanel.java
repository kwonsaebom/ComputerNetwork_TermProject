/**
 * �α��� ȭ��
 * ���̵�� ��й�ȣ�� ���� �α��� �� �� ����. �α��ο� �����Ͽ��ٸ� ErrorMessagePanel�� ����ȴ�.
 */
package client.frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controller.Controller;
import enums.CommonWord;
import util.UserInfoPanel;
import java.awt.Color;

@SuppressWarnings("serial")
public class LoginPanel extends UserInfoPanel {

	private final String LOGIN = "�α���";

	private final String GOBACK = "�ڷΰ���";
	
	private ArrayList<JTextField> userInfos = new ArrayList<JTextField>(); // �̸��ϰ� ��й�ȣ

	public LoginPanel() {
		setBackground(new Color(255, 215, 0));

		showFormTitle(CommonWord.LOGIN.getText());
		writeUserInfo();
		showLoginButton();
	}

	public void writeUserInfo() {

		int y_value = 155;
		for (CommonWord commonWord : CommonWord.values()) {

			//���̵� �Է�â
			if (commonWord.getNum() == CommonWord.ID.getNum()) {
				formTitleLabel = new JLabel(commonWord.getText());
				formTitleLabel.setFont(new Font("���� ���", Font.BOLD, 14));
				formTitleLabel.setBounds(30, y_value, 200, 50);
				add(formTitleLabel);
				userInfoTextField = new JTextField(10);
				userInfoTextField.setBounds(30, y_value + 45, 325, 30);
				add(userInfoTextField);
				y_value += 100;

				userInfos.add(userInfoTextField);

			//��й�ȣ �Է�â 
			} else if (commonWord.getNum() == CommonWord.PASSWORD.getNum()) {

				formTitleLabel = new JLabel(commonWord.getText());
				formTitleLabel.setFont(new Font("���� ���", Font.BOLD, 14));
				formTitleLabel.setBounds(30, y_value, 200, 50);
				add(formTitleLabel);
				userInfoPasswordField = new JPasswordField(10);
				userInfoPasswordField.setBounds(30, y_value + 45, 325, 30);
				add(userInfoPasswordField);
				y_value += 100;

				userInfoPasswordField.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						loginUser();
					}
				});

				userInfos.add(userInfoPasswordField);
			} else {
				continue;
			}
		}
	}

	//�α��� ��ư
	private void showLoginButton() {

		JButton loginButton = showFormButton(LOGIN);
		loginButton.setBounds(100, 400, 180, 40);
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				loginUser();
			}
		});
	}

	//�α����� ���� DB���� ����
	private void loginUser() {
		Controller controller = Controller.getInstance();
		controller.findUser(userInfos);
	}
}
