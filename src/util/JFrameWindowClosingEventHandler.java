package util;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import client.frame.FriendListPanel;
import client.frame.IndexPanel;
import controller.Controller;

public class JFrameWindowClosingEventHandler extends WindowAdapter {

  private String frameName;

  public JFrameWindowClosingEventHandler(String frameName) {

    this.frameName = frameName;
  }

  public void windowClosing(WindowEvent e) {

    JFrame frame = (JFrame) e.getWindow();
    frame.dispose();
    
    Controller controller = Controller.getInstance();
    // ���� ä�ù��϶�
    if (controller.username.equals(frameName)) {
      IndexPanel.userProfileButton.setText(frameName);
    }
    // ģ�� ä�ù��϶�
    for (JButton j : FriendListPanel.friendButtons) {
      if (j.getText().contains(frameName)) {
        j.setText(frameName);
      }
    }
  }
}

