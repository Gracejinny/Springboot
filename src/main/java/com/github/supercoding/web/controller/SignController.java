package com.github.supercoding.web.controller;

import com.github.supercoding.service.AuthService;
import com.github.supercoding.web.dto.auth.Login;
import com.github.supercoding.web.dto.auth.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/sign")
public class SignController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public String register(@RequestBody SignUp signUpRequest){
        boolean isSuccess = authService.signUp(signUpRequest);
        return isSuccess ? "회원가입 성공했습니다." : "회원가입 실패했습니다.";
    }

    @PostMapping(value = "/login")
    public String login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse){
        String token = authService.login(loginRequest);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);
        return "로그인이 성공하였습니다.";
    }

}
