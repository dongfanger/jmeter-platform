package org.example.entity.request;

import lombok.Data;

@Data
public class PasswordReq {
    private String oldPassword;
    private String newPassword;
}
