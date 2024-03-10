package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class SignupRequest {
	@Getter
	@Setter
	@ToString
	public class SigninRequest {
		//validation Annotation
		@NotBlank(message = "Email can't be blank")
		@Email(message = "Invalid email format")
		private String email;
		@Length(min = 5,max=20,message = "Invalid password length")
		private String password;
		@Length(min = 4,message="first name is mandatory")
		private String first_name;
		@Length(min = 5,message="last name is mandatory")
		private String last_name;
		@NotBlank(message = "department can't be blank")
		private String department;
		private String location;
		@Range(min=1000,max=100000)
		private double salary;
		@Future (message="enter future date")
		@DateTimeFormat(pattern="dd-MMM-yyyy") 
		private LocalDate join_date;
	}

}
