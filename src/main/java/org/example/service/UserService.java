package org.example.service;


import org.example.entity.request.PasswordReq;
import org.example.entity.response.UserLoginResp;
import org.example.entity.response.UserResp;
import org.example.mbg.model.User;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;

/**
 * UserService
 * Created by macro on 2019/4/19.
 */
public interface UserService {
    List<User> listAllUser();

    User updateUser(Long id, User user);

    int deleteUser(Long id);

    PageInfo<UserResp> listUser(String keyword, int pageNum, int pageSize);

    UserResp getUser(Long id);

    User getUserByUsername(String username);

    UserResp getLoginUser(String username);

    UserResp register(User user);

    String login(String username, String password);

    Boolean updatePassword(HttpHeaders headers, PasswordReq passwordReq);

    String resetPassword(Long id);
}
