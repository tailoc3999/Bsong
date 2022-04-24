package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import constants.DefineConstant;
import models.User;
import utils.ConnectDBUlti;


public class UserDAO {
	
	private Connection conn;
	
	private PreparedStatement pst;
	
	private Statement st;
	
	private ResultSet rs;
	
	public List<User> getItem(){
		List<User> users = new ArrayList<>();
		String SQL = "SELECT id, username, password, fullname FROM users ORDER BY id DESC";
		conn = ConnectDBUlti.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				int id = rs.getInt("id");
				String userName = rs.getString("username");
				String password = rs.getString("password");
				String fullName = rs.getString("fullname");
				User user = new User(id, userName, password, fullName);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return users;
	}

	public int addItem(User item) {
		String SQL = "INSERT INTO users (username, password, fullname) VALUES (?, ?, ?)";
		int result = 0;
		
		try {
			conn = ConnectDBUlti.getConnection();
			pst = conn.prepareStatement(SQL);
			pst.setString(1, item.getUserName());
			pst.setString(2, item.getPassWord());
			pst.setString(3, item.getFullName());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst);
		} 
		
		return result;
	}

	public boolean hasUser(String username) {
		String SQL = "SELECT * FROM users WHERE username = ?";
		
		try {
			conn = ConnectDBUlti.getConnection();
			pst = conn.prepareStatement(SQL);
			pst.setString(1, username);
			rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		} 
		
		return false;
	}

	public User getItem(int id) {
		String SQL = "SELECT * FROM users WHERE id = ?";
		conn = ConnectDBUlti.getConnection();
		User item = null;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				int id2 = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String fullname = rs.getString("fullname");
				item = new User(id2, username, password, fullname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return item;
	}

	public int editItem(User item) {
		String SQL = "UPDATE users SET password = ?, fullname = ? WHERE id = ?";
		conn = ConnectDBUlti.getConnection();
		int result = 0;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1, item.getPassWord());
			pst.setString(2, item.getFullName());
			pst.setInt(3, item.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst);
		}
		return result;
	}

	public int delItem(int id) {
		String SQL = "DELETE FROM users WHERE id = ?";
		conn = ConnectDBUlti.getConnection();
		int result = 0;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst);
		}
		return result;
	}

	public User findByUserAndPassword(String username, String password) {
		String SQL = "SELECT * FROM users WHERE username = ? AND password = ?";
		conn = ConnectDBUlti.getConnection();
		User item = null;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String userName = rs.getString("username");
				String passWord = rs.getString("password");
				String fullName = rs.getString("fullname");
				item = new User(id, userName, passWord, fullName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return item;
	}

	public int numberOfItems() {
		String SQL = "SELECT COUNT(*) AS count FROM users";
		conn = ConnectDBUlti.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, st, rs);
		}
		return 0;
	}

	public List<User> getItemsPagination(int offset) {
		List<User> users = new ArrayList<>();
		String SQL = "SELECT id, username, password, fullname FROM users ORDER BY id DESC LIMIT ?, ?";
		conn = ConnectDBUlti.getConnection();
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, offset);
			pst.setInt(2, DefineConstant.NUMBER_PER_PAGE_USER);
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String userName = rs.getString("username");
				String password = rs.getString("password");
				String fullName = rs.getString("fullname");
				User user = new User(id, userName, password, fullName);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return users;
	}

	
//	public static void main(String[] args) {
//		UserDAO dao = new UserDAO();
//		List<User> users = dao.getItem();
//		System.out.println(users);
//	}
}
