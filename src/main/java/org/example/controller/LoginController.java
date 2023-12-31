package org.example.controller;

import org.example.common.api.CommonResult;
import org.example.entity.request.UserLoginReq;
import org.example.entity.response.UserLoginResp;
import org.example.entity.response.UserResp;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserLoginReq userLoginReq) {
        String username = userLoginReq.getUsername();
        String token = userService.login(username, userLoginReq.getPassword());
        if (token == null) {
            return new ResponseEntity<>(CommonResult.validateFailed("用户名或密码错误"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserResp user = userService.getLoginUser(username);
        UserLoginResp userLoginResp = new UserLoginResp();
        userLoginResp.setUser(user);
        userLoginResp.setToken(token);

        return new ResponseEntity<>(CommonResult.success(userLoginResp), HttpStatus.OK);
    }
}