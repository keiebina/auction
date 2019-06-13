package com.example.demo.login.domain.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Table(name = "users")
@Entity
@Data
public class User {
	
	@Id
	@Column
	@NotBlank
	@Email
	private String userId;                       //ユーザーID（メールアドレス形式）
	
	@Column
	@NotBlank
	@Length(min = 4, max = 100)
	@Pattern( regexp = "^[a-zA-Z0-9]+$")
	private String password;                     //パスワード
	
	@Column
	@NotBlank
	private String nickname;                     //公開されるユーザー名
	
	@Column
	@NotBlank
	private String userName;                     //氏名
	
	@Column
	@NotNull
	private boolean gender;                      //性別
	
	@Column 
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthday;                  //生年月日
	
	@Column
	@NotBlank
	private String postalCode;                  //郵便番号
	
	@Column
	@NotBlank
	private String address;                      //住所
	
	@Column
	private String role;                         //管理者権限（ROLE_GENERAL　or ROLE_ADMIN)
}
