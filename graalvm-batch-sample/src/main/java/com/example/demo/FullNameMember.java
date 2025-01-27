package com.example.demo;

public class FullNameMember {

	private final String id;

	private final String firstName;

	private final String lastName;

	private final String fullName;

	public FullNameMember(String id, String firstName, String lastName, String fullName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return fullName;
	}

}
