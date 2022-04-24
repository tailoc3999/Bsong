package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Category {
	
	private int id;
	
	private String name;

	public Category(String name) {
		super();
		this.name = name;
	}

	public Category(int id) {
		super();
		this.id = id;
	}
	
	
}
