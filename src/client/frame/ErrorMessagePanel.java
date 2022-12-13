/**
 * ���� �޼����� ����ϴ� ȭ��.
 * ȸ������ ����, �α��� ���� �� ���� ȭ���� ���.  
 */
package client.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import enums.CommonWord;
import util.ColorSet;
import util.CommonPanel;

@SuppressWarnings("serial")
public class ErrorMessagePanel extends CommonPanel {

  private JLabel errorMessageLabel;

  private JButton backButton;

  public ErrorMessagePanel(String text) {
  	setBackground(new Color(255, 215, 0));

    showErrorMessage(text);
    backButton = getGoBackButton(CommonWord.GOBACK.getText());
    
    //ȸ������, �α���, ģ���߰� ��� ���н� �ڷΰ��� ��ư 
    backButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        if (text.equals(CommonWord.SIGN_UP_MEMBERSHIP.getText())) {
          JoinMembershipPanel memPanel = new JoinMembershipPanel();
          MainPanel.frame.change(memPanel);
        } else if (text.equals(CommonWord.LOGIN.getText())) {
          LoginPanel loginPanel = new LoginPanel();
          MainPanel.frame.change(loginPanel);
        } else if (text.equals(CommonWord.ADD.getText())) {
          addFriendPanel addPanel = new addFriendPanel();
          MainPanel.frame.change(addPanel);
        }
      }
    });
  }

  //���� �޼��� ���
  private void showErrorMessage(String text) {

    errorMessageLabel = new JLabel(text + "��(��) �����߽��ϴ�.");
    errorMessageLabel.setFont(new Font("���� ���", Font.BOLD, 18));
    errorMessageLabel.setBounds(80, 250, 500, 50);
    add(errorMessageLabel);
    
  }

  //�ڷΰ��� ��ư
  private JButton getGoBackButton(String text) {

    backButton = new JButton(text);
    backButton.setFont(new Font("���� ���", Font.BOLD, 14));
    backButton.setForeground(Color.WHITE);
    backButton.setBackground(ColorSet.BackButtonColor);
    backButton.setOpaque(true);
    backButton.setBorderPainted(false);
    backButton.setBounds(100, 480, 180, 40);
    add(backButton);
    return backButton;
  };
}
