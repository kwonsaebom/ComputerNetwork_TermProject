/*
 * ����̵Ǵ� ���� �г�
 */
package util;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CommonPanel extends JPanel {

  protected JLabel logoLabel;
  
  //�ΰ���� �̿�
  protected Image logoImg = UseImageFile.getImage("C:/Users/KSB/Desktop/java/src/Image/label.png");

  //���� ����
  protected CommonPanel() {

    setBackground(ColorSet.talkBackgroundColor);
    setLayout(null);
 
  }

}
