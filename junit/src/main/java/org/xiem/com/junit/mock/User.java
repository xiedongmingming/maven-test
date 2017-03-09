package org.xiem.com.junit.mock;

public class User {

	public User() {

	}
	public User(String id) {
		Id = id;
	}

	private String Id;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
}
