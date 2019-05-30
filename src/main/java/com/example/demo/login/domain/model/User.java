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
@NamedQueries({
	@NamedQuery(
			name = "getAllUsers",
			query = "SELECT u FROM User AS u"
			),
	@NamedQuery(
			name = "getAllUsersCount",
			query = "SELECT COUNT(u) FROM User AS u"
			)
})
@Entity
@Data
public class User {
	
	@Id
	@Column
	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String userId;                       //ユーザーID（メールアドレス形式）
	
	@Column
	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 4, max = 100, groups = ValidGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups = ValidGroup3.class)
	private String password;                     //パスワード
	
	@Column
	@NotBlank(groups = ValidGroup1.class)
	private String nickname;                     //公開されるユーザー名
	
	@Column
	@NotBlank(groups = ValidGroup1.class)
	private String userName;                     //氏名
	
	@Column
	@NotNull(groups = ValidGroup1.class)
	private boolean gender;                      //性別
	
	@Column 
	@NotNull(groups = ValidGroup1.class)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthday;                  //生年月日
	
	@Column
	@NotBlank(groups = ValidGroup1.class)
	private String postalCode;                  //郵便番号
	
	@Column
	@NotBlank(groups = ValidGroup1.class)
	private String address;                      //住所
	
	@Column
	private String role;                         //管理者権限（ROLE_GENERAL　or ROLE_ADMIN)
}
