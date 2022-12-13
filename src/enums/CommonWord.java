/*
 * ����ϴ� �ܾ���� enum���� ����
 */
package enums;


public enum CommonWord {

  SIGN_UP_MEMBERSHIP("ȸ������", 0),
  LOGIN("�α���", 1),
  ID("���̵�", 2),
  PASSWORD("��й�ȣ", 3),
  NAME("�̸�", 4),
  NICKNAME("����", 5),
  EMAIL("�̸���", 6),
  BIRTH("�������", 7),  
  MYPROFILE("�� ������", 8),
  FRIEND("ģ��", 9),
  GOBACK("�ڷΰ���", 10),
  ADD("ģ���߰�",11),
  API("�ڷγ� Ȯ���ڼ�",12),
  ONLINE("�¶��� ģ��",13);


  private final String text;

  private final int num;


  CommonWord(String text, int num) {

    this.text = text;
    this.num = num;
  }

  public String getText() {

    return text;
  }

  public int getNum() {

    return num;
  }


}
