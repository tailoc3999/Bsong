package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contact {
	private int id;
	
	private String name;
	
	private String email;
	
	private String website;
	
	private String message;

	public Contact(String name, String email, String website, String message) {
		super();
		this.name = name;
		this.email = email;
		this.website = website;
		this.message = message;
	}
	
	
}
