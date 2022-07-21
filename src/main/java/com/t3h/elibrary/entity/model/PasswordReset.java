package com.t3h.elibrary.entity.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PasswordReset {
    private int userId;
    private String newPassword;
    private String confirmPassword;
}