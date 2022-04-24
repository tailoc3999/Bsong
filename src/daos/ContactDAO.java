package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import constants.DefineConstant;
import models.Contact;
import utils.ConnectDBUlti;

public class ContactDAO {
	private Connection conn;
	
	private PreparedStatement pst;
	
	private Statement st;
	
	private ResultSet rs;

	public int sendContact(Contact contact) {
		String SQL = "INSERT INTO contacts(name, email, website, message) VALUES (?, ?, ?, ?)";
		conn = ConnectDBUlti.getConnection();
		int result = 0;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1, contact.getName());
			pst.setString(2, contact.getEmail());
			pst.setString(3, contact.getWebsite());
			pst.setString(4, contact.getMessage());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst);
		}
		
		return result;
	}

	public List<Contact> getAllItem() {
		List<Contact> contacts = new ArrayList<>();
		String SQL = "SELECT id, name, email, website, message FROM contacts ORDER BY id DESC";
		conn = ConnectDBUlti.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String website = rs.getString("website");
				String message = rs.getString("message");
				Contact contact = new Contact(id, name, email, website, message);
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, st, rs);
		}
		
		return contacts;
	}

	public int delItem(int id) {
		String SQL = "DELETE FROM contacts WHERE id = ?";
		conn = ConnectDBUlti.getConnection();
		int ressult = 0;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, id);
			ressult = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst);
		}
		
		return ressult;
	}

	public int numberOfItems() {
		String SQL = "SELECT COUNT(*) AS count FROM contacts";
		conn = ConnectDBUlti.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, st, rs);
		}
		
		return 0;
	}

	public List<Contact> getAllItemPagination(int offset) {
		List<Contact> contacts = new ArrayList<>();
		String SQL = "SELECT id, name, email, website, message FROM contacts ORDER BY id DESC LIMIT ?, ?";
		conn = ConnectDBUlti.getConnection();
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, offset);
			pst.setInt(2, DefineConstant.NUMBER_PER_PAGE_CONTACT);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String website = rs.getString("website");
				String message = rs.getString("message");
				Contact contact = new Contact(id, name, email, website, message);
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return contacts;
	}

	public List<Contact> findAllByName(int offset, String cname) {
		List<Contact> contacts = new ArrayList<>();
		String SQL = "SELECT id, name, email, website, message FROM contacts WHERE name LIKE ? ORDER BY id DESC LIMIT ?, ?";
		conn = ConnectDBUlti.getConnection();
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1,"%" + cname + "%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineConstant.NUMBER_PER_PAGE_CONTACT);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String website = rs.getString("website");
				String message = rs.getString("message");
				Contact contact = new Contact(id, name, email, website, message);
				contacts.add(contact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return contacts;
	}
}
