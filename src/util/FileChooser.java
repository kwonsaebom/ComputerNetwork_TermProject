/*
 * ���� ������Ʈ�� �����ϴ� class
 */
package util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
  
  public static File showFile() {
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter =
        new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
    chooser.setFileFilter(filter);
    int ret = chooser.showOpenDialog(null);
    if (ret != JFileChooser.APPROVE_OPTION) {
      JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
    }
    File file = chooser.getSelectedFile();
    
    return file;
  }

}
