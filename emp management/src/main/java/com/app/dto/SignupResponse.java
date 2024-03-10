package com.app.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

public class SignupResponse {
	@Getter
	@Setter
	public class SigninResponse {

	private LocalDate join_date;;
	private String email;;
	private double salary;
	private String password;
	private String first_name;
	private String last_name;
	private String department;
	private String location;
	}
}
