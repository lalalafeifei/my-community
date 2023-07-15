package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.provider.GithubProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;


    @Value("${github.clientId}")
    private String client_id;

    @Value("${github.clientSecret}")
    private String client_secret;

    @Value("${github.redirectUri}")
    private String redirect_uri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state, HttpServletRequest request) {
        System.out.println("code: " + code);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("user: " + githubUser.getName());
        if (githubUser != null) {
            User user = new User();
            user.setToken(accessToken);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println("user: " + user);

            userMapper.insert(user);
            // login success, write cookie and session
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            // login failed, re-login
            return "redirect:/";
        }
    }
}
