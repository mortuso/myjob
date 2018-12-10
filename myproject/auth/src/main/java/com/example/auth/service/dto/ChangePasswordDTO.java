package com.example.auth.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ChangePasswordDTO {

	private String oldPassword;
	private String newPassword;
	private String newPasswordConfirm;
}
