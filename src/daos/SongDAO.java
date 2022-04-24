package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import constants.DefineConstant;
import models.Category;
import models.Song;
import utils.ConnectDBUlti;

public class SongDAO {
	
	private Connection conn;
	
	private Statement st;
	
	private PreparedStatement pst;
	
	private ResultSet rs;
	
	public ArrayList<Song> getSong() {
		ArrayList<Song> list = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT songs.*, categories.name AS catName FROM Songs "
				+ "INNER JOIN Categories ON Songs.cat_id = categories.id "
				+ "ORDER BY songs.id DESC";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(SQL);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText = rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category cat =  new Category(rs.getInt("cat_id"), rs.getString("catName"));
				Song song = new Song(id, name, previewText, detailText, dateCreate, picture, counter, cat);
				list.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, st, rs);
		}
		
		return list;
	}
	
	public ArrayList<Song> getSong(Category category) {
		ArrayList<Song> list = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT songs.*, categories.name AS catName FROM Songs "
				+ "INNER JOIN Categories ON Songs.cat_id = categories.id "
				+ "WHERE songs.cat_id = ? "
				+ "ORDER BY songs.id DESC";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, category.getId());
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText = rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category cat =  new Category(rs.getInt("cat_id"), rs.getString("catName"));
				Song song = new Song(id, name, previewText, detailText, dateCreate, picture, counter, cat);
				list.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		
		return list;
	}
	
	public ArrayList<Song> getSong(int number) {
		ArrayList<Song> list = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT songs.*, categories.name AS catName FROM Songs "
				+ "INNER JOIN Categories ON Songs.cat_id = categories.id "
				+ "ORDER BY songs.id DESC "
				+ "LIMIT ?";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, number);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText = rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category cat =  new Category(rs.getInt("cat_id"), rs.getString("catName"));
				Song song = new Song(id, name, previewText, detailText, dateCreate, picture, counter, cat);
				list.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		
		return list;
	}
	
	public int add(Song song) {
		conn = ConnectDBUlti.getConnection();
		String SQL = "INSERT INTO Songs (name, preview_text, detail_text, picture, cat_id) VALUE (?, ?, ?, ?, ?)";
		int result = 0;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1,  song.getName());
			pst.setString(2,  song.getPreview_text());
			pst.setString(3,  song.getDetail_text());
			pst.setString(4,  song.getPicture());
			pst.setInt(5,  song.getCategory().getId());
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

	public Song getById(int id) {
		Song song = null;
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT s.*, c.name AS catName "
				+ "FROM Songs AS s INNER JOIN Categories AS c ON s.cat_id = c.id WHERE s.id = ?";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText = rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				int cat_id = rs.getInt("cat_id");
				Category cat = new Category(cat_id, rs.getString("catName"));
				song = new Song(id, name, previewText, detailText, dateCreate, picture, counter, cat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return song;
	}

	public int editSong(Song song) {
		conn = ConnectDBUlti.getConnection();
		String SQL = "UPDATE songs SET name = ?, preview_text = ?, detail_text = ?, picture = ?"
				+ ", cat_id = ? WHERE id = ?";
		int result = 0;
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1,  song.getName());
			pst.setString(2,  song.getPreview_text());
			pst.setString(3,  song.getDetail_text());
			pst.setString(4,  song.getPicture());
			pst.setInt(5,  song.getCategory().getId());
			pst.setInt(6, song.getId());
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
		String SQL = "DELETE FROM songs WHERE id = ?";
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

	public ArrayList<Song> relatedSong(Song song) {
		ArrayList<Song> items = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT * FROM songs WHERE id != ? AND cat_id = ? ORDER BY counter DESC LIMIT 3";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, song.getId());
			pst.setInt(2, song.getCategory().getId());
			rs = pst.executeQuery();
			while (rs.next()) {
				Song item = new Song(rs.getInt("id"), rs.getString("name"), rs.getString("preview_text"), rs.getString("detail_text"), rs.getTimestamp("date_create"), rs.getString("picture"), rs.getInt("counter"), new Category(rs.getInt("cat_id")));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return items;
	}

	public void increaseView(int id) {
		conn = ConnectDBUlti.getConnection();
		String SQL = "UPDATE songs SET counter = counter + 1 WHERE id = ?";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst);
		}
	}

	public int numberOfItems() {
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT COUNT(*) AS count FROM songs";
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
	
	public int numberOfItems(int catID) {
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT COUNT(*) AS count FROM songs WHERE cat_id = ?";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, catID);
			rs = pst.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return 0;
	}

	public List<Song> getSongsPagination(int offset) {
		ArrayList<Song> list = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT songs.*, categories.name AS catName FROM Songs "
				+ "INNER JOIN Categories ON Songs.cat_id = categories.id "
				+ "ORDER BY songs.id DESC "
				+ "LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, offset);
			pst.setInt(2, DefineConstant.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText = rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category cat =  new Category(rs.getInt("cat_id"), rs.getString("catName"));
				Song song = new Song(id, name, previewText, detailText, dateCreate, picture, counter, cat);
				list.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		
		return list;
	}

	public List<Song> getSongPagination(Category category, int offset) {
		ArrayList<Song> list = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT songs.*, categories.name AS catName FROM Songs "
				+ "INNER JOIN Categories ON Songs.cat_id = categories.id "
				+ "WHERE songs.cat_id = ? "
				+ "ORDER BY songs.id DESC "
				+ "LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setInt(1, category.getId());
			pst.setInt(2, offset);
			pst.setInt(3, DefineConstant.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText = rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category cat =  new Category(rs.getInt("cat_id"), rs.getString("catName"));
				Song song = new Song(id, name, previewText, detailText, dateCreate, picture, counter, cat);
				list.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		return list;
	}

	public List<Song> findAllByName(int offset, String sname) {
		ArrayList<Song> list = new ArrayList<>();
		conn = ConnectDBUlti.getConnection();
		String SQL = "SELECT songs.*, categories.name AS catName FROM Songs "
				+ "INNER JOIN Categories ON Songs.cat_id = categories.id "
				+ "WHERE songs.name LIKE ?"
				+ "ORDER BY songs.id DESC "
				+ "LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(SQL);
			pst.setString(1,"%" + sname + "%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineConstant.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText = rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category cat =  new Category(rs.getInt("cat_id"), rs.getString("catName"));
				Song song = new Song(id, name, previewText, detailText, dateCreate, picture, counter, cat);
				list.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectDBUlti.close(conn, pst, rs);
		}
		
		return list;
	}

}
