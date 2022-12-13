/*
 * DB�� ������ ������ ���� class
 */
package server.userdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTextField;

public class UserDAO {

	private String driver = "com.mysql.cj.jdbc.Driver";

	private String jdbcurl = "jdbc:mysql://localhost/main";

	private Connection conn;

	private PreparedStatement pstmt, pstmt2;

	public String username = null;

	//jdbc�� �̿��� connection�� ����.
	public void connect() {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(jdbcurl, "root", "Spring030!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//DB���� ���� ���� 
	public void disconnect() {

		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//ȸ�� ������ DB�� insert 
	//������ true ���н� false�� ��ȯ.
	public boolean insertDB(User user) {

		connect();
		String sql = "insert into member_table1 values(?,?,?,?,?,?)";

		boolean isInsert = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUid());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getUname());
			pstmt.setString(4, user.getUnickname());
			pstmt.setString(5, user.getUemail());
			pstmt.setString(6, user.getUbirth());
			pstmt.executeUpdate();

			isInsert = true;

		} catch (SQLException e) {
			isInsert = false;
		}
		disconnect();

		return isInsert;

	}

	//ģ�� �����̼ǿ� ģ���� ���̵� ����. ���� ģ���� ���� �� �ֵ��� ����.
	public boolean addFriendDB(String friendid) {

		String userid = findUserId();
		String fid = friendid;
		connect();
		String sql = "insert into friendList1(userid, friendid) values (?,?)";

		boolean isAdd = false;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, fid);
			pstmt.executeUpdate();

			isAdd = true;

		} catch (SQLException e) {
			isAdd = false;
		}

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fid);
			pstmt.setString(2, userid);
			pstmt.executeUpdate();
			
			isAdd = true;
		} catch (SQLException e) {
			isAdd = false;
		}

		disconnect();

		return isAdd;

	}

	// �ش� ���̵� ������ �ִ� user�� �ִ��� ������ Ȯ��.
	public int findUserInfo(String userid) {

		connect();
		String sql = "select * from member_table1 where uid = ?";
		String id = userid;

		int result = 0;

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = 1; // ������
			} else {
				result = 0; // ������������
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return result;
	}

	//���̵�� ��й�ȣ�� ��ġ�ϴ� user�� �̸��� ��ȯ.
	public String findUser(ArrayList<JTextField> userInfos) {

		connect();
		String sql = "select uname from member_table1 where uid =? and password=?";
		String uid = userInfos.get(0).getText();
		String password = userInfos.get(1).getText();

		String uname = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				uname = rs.getString("uname");
			}

			username = uname;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		disconnect();

		return username;
	}

	//ģ�� �����̼ǰ� ��� �����̼��� ���� ģ���� �ξ����ִ� user���� ��ȯ
	public ArrayList<String> friendList() {

		String uid = findUserId();
		connect();
		ArrayList<String> friends = new ArrayList<String>();
		String sql = "select m.uname from member_table1 m, friendList1 f where m.uid = f.friendid and f.userid = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				friends.add(rs.getString("uname"));
			}
		} catch (SQLException e) {
		}
		disconnect();
		System.out.println(friends.size());
		return friends;
	}

	//�ش� �̸��� ���� user�� id�� ��ȯ
	private String findUserId() {

		connect();
		String sql = "select uid from member_table1 where uname = ?";
		String uid = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				uid = rs.getString("uid");
			}
		} catch (SQLException e) {
		}
		disconnect();
		return uid;
	}

	


}
