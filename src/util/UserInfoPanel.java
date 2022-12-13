package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public abstract class UserInfoPanel extends CommonPanel {

  protected JLabel formTitleLabel;

  protected JTextField userInfoTextField;

  protected JPasswordField userInfoPasswordField;
  
  protected JButton formButton;

  protected abstract void writeUserInfo();

  protected void showFormTitle(String text) {

    formTitleLabel = new JLabel(text);
    formTitleLabel.setFont(new Font("���� ���", Font.BOLD, 18));
    formTitleLabel.setBounds(30, 70, 200, 50);
    add(formTitleLabel);
  };


  protected JButton showFormButton(String text) {

    formButton = new JButton(text);
    formButton.setFont(new Font("���� ���", Font.BOLD, 14));
    formButton.setForeground(Color.WHITE);
    formButton.setBackground(ColorSet.signUpButtonColor);
    formButton.setBounds(100, 480, 180, 40);
    add(formButton);

    return formButton;
  };

  public void paint(Graphics g) {

    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;
    Line2D lin = new Line2D.Float(30, 115, 350, 115);
    g2.draw(lin);
  }
}
