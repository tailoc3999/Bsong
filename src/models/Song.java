package models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Song {
	private int id;
	
	private String name;
	
	private String preview_text;
	
	private String detail_text;
	
	private Timestamp date_create;
	
	private String picture;
	
	private int counter;
	
	private Category category;

	public Song(String name, String preview_text, String detail_text, String picture, Category category) {
		super();
		this.name = name;
		this.preview_text = preview_text;
		this.detail_text = detail_text;
		this.picture = picture;
		this.category = category;
	}

	public Song(int id, String name, String preview_text, String detail_text, String picture, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.preview_text = preview_text;
		this.detail_text = detail_text;
		this.picture = picture;
		this.category = category;
	}
	
	
	
}
