package org.example.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserLoginResp {
    private String token;
    private UserResp user;
}
