package com.example.demo.login.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "users")
@Entity
public class User {
	
	@Id
	@Column(name = "user_id")
	@NotBlank
	@Email
	private String userId;                      								 //ユーザーID（メールアドレス形式）
	
	@Column(name = "password")
	@NotBlank
	@Length(min = 4, max = 100)
	private String password;                     							//パスワード
	
	@Column(name = "nickname", nullable = false)
	@NotBlank
	private String nickname;                     							//公開されるユーザー名
	
	@Column(name = "user_name", nullable = false)
	@NotBlank
	private String userName;                    							 //氏名
	
	@Column(name = "gender", nullable = false)
	@NotNull
	private boolean gender;                      							//性別
	
	@Column(name = "birthday", nullable = false)
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthday;                  							//生年月日
	
	@Column(name = "postal_code", nullable = false)
	@NotBlank	
	private String postalCode;                  							//郵便番号
	
	@Column(name = "address", nullable = false)
	@NotBlank
	private String address;                    								//住所
	
	@Column(name = "role")
	private String role;                         								//管理者権限（ROLE_GENERAL　or ROLE_ADMIN)

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}	
