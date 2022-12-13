/**
 * ȸ������ ȭ��.  
 * ��й�ȣ�� ���������� ���������� ��� �����޼��� ���. 
 * ���̵� �ߺ��� ��� ErrorMessagePanel�� ���� ��.
 */
package client.frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controller.Controller;
import enums.CommonWord;
import server.userdb.User;
import util.UserInfoPanel;
import server.userdb.UserDAO;
import java.awt.Color;

@SuppressWarnings("serial")
public class JoinMembershipPanel extends UserInfoPanel {

	private static final String Passwrod_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{8,16}$";
	private Pattern pattern;
	private Matcher matcher;
	private String hex;
	
	private final String SIGN_UP = "�����ϱ�";

	private ArrayList<JTextField> userInfos = new ArrayList<JTextField>();

	private User user;	

	public JoinMembershipPanel() {
		setBackground(new Color(255, 215, 0));

		showFormTitle(CommonWord.SIGN_UP_MEMBERSHIP.getText());
		writeUserInfo();		
		showSignUpButton();
	
	}
	
	UserDAO userDAO;


	/* ȸ������ �� ���� */
	public void writeUserInfo() {

		int y_value = 120;
		for (CommonWord commonWord : CommonWord.values()) {
			if (commonWord.getNum() >= CommonWord.ID.getNum() && commonWord.getNum() <= CommonWord.BIRTH.getNum()) {
				
				formTitleLabel = new JLabel(commonWord.getText());
				formTitleLabel.setFont(new Font("���� ���", Font.BOLD, 14));
				formTitleLabel.setBounds(30, y_value, 200, 50);
				add(formTitleLabel);

				if(commonWord.getNum() == CommonWord.PASSWORD.getNum()) {
					
					userInfoPasswordField = new JPasswordField(10);
					userInfoPasswordField.setBounds(30, y_value + 35, 325, 30);
					add(userInfoPasswordField);
					
					userInfos.add(userInfoPasswordField);
					
				} else {
					
					userInfoTextField = new JTextField(10);
					userInfoTextField.setBounds(30, y_value + 35, 325, 30);
					add(userInfoTextField);
					
					userInfos.add(userInfoTextField);
				}
				
				y_value += 55;
				
				
			} else {
				continue;
			}
		}
		
	
	}

	//ȸ������ ��ư
	private void showSignUpButton() {

		JButton signupButton = showFormButton(SIGN_UP);
		signupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(checkError() == 1) {
					createUser();
				}
				
				
			}
		});
	}

	//��й�ȣ�� ���ȼ� üũ 
	public boolean PasswordValidate(String hex) {
		hex = userInfoPasswordField.getText();
		pattern = Pattern.compile(Passwrod_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
	
	//ȸ������ â���� ������ ã�� method
	private int checkError() {
			
		//userDAO = new UserDAO();
		if(userInfos.get(0).getText().equals("") || userInfos.get(1).getText().equals("") ||
				userInfos.get(2).getText().equals("") || userInfos.get(3).getText().equals("") ||
				userInfos.get(4).getText().equals("") || userInfos.get(5).getText().equals("")) {
			JOptionPane.showMessageDialog(this,"�ٽ� �Է����ּ���.");
			return 0;
		}
		
		if(PasswordValidate(userInfos.get(1).getText()) == false) {
			JOptionPane.showMessageDialog(this,"8�� ~ 16�ڻ��� ������ ȥ��,Ư������ �н������Է����ּ���.");
			return 0;
		}
		
		/*
		if(userDAO.findUid(userInfos) > 0) {
			JOptionPane.showMessageDialog(this,"�ߺ�ID�Դϴ�!");
			return 0;
		}
		*/
		return 1;
		
	}
	
	//ȸ�������� ���� DB�� ����
	private void createUser() {

		user = new User(userInfos.get(0).getText(), userInfos.get(1).getText(), userInfos.get(2).getText(),
				userInfos.get(3).getText(), userInfos.get(4).getText(), userInfos.get(5).getText());

		Controller controller = Controller.getInstance();
		controller.insertDB(user);
	}
}
