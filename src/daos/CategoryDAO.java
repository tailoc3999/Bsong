package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import constants.DefineConstant;
import models.Category;
import utils.ConnectDBUlti;

public class CategoryDAO {
	
	private Connection conn;
	
	private Statement st;
	
	private ResultSet rs;
	
	private PreparedStatement pst;
	
	public List<Category> getCategories(){
		String SQL = "SELECT id, name FROM categories ORDER BY id DESC";
		List<Category> categories = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category category = new Category(id, name);
				categories.add(category);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return categories;
	}
	
	public int add(Category cat){
		String SQL = "INSERT INTO categories (name) VALUE (?)";
		int result = 0;
		conn = ConnectDBUlti.getConnection();
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1, cat.getName());
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

	public Category getItemById(int id) {
		String SQL = "SELECT name FROM categories WHERE id = ?";
		conn = ConnectDBUlti.getConnection();
		Category category = null;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				category = new Category(id, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		
		return category;
	}

	public int editItem(Category category) {
		String SQL = "UPDATE categories SET name = ? WHERE id = ?";
		conn = ConnectDBUlti.getConnection();
		int result = 0;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1, category.getName());
			pst.setInt(2, category.getId());
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

	public int delItem(int id) {
		String SQL = "DELETE FROM categories WHERE id = ?";
		conn = ConnectDBUlti.getConnection();
		int result = 0;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, id);
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

	public int checkName(Category category) {
		String SQL = "SELECT * FROM categories WHERE name = ?";
		int result = 0;
		try {
			conn = ConnectDBUlti.getConnection();
			pst = conn.prepareStatement(SQL);
			pst.setString(1, category.getName());
			rs = pst.executeQuery();
			if (rs.next()) {
				result ++;
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		} 
		return result;
	}

	public int numberOfItems() {
		String SQL = "SELECT COUNT(*) AS count FROM categories";
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
		} finally {
			ConnectDBUlti.close(conn, st, rs);
		}
		return 0;
	}

	public List<Category> getCategoriesPagination(int offset) {
		String SQL = "SELECT id, name FROM categories ORDER BY id DESC LIMIT ?, ?";
		List<Category> categories = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, offset);
			pst.setInt(2, DefineConstant.NUMBER_PER_PAGE_CAT);
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category category = new Category(id, name);
				categories.add(category);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return categories;
	}

	
}
