package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	
	private int id;
	
	private String userName;
	
	private String passWord;
	
	private String fullName;

	public User(String userName, String passWord, String fullName) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.fullName = fullName;
	}

	public User(int id, String passWord, String fullName) {
		super();
		this.id = id;
		this.passWord = passWord;
		this.fullName = fullName;
	}

	
	
}
