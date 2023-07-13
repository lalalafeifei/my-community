package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("e7d6121877ebe4ffc032");
        accessTokenDTO.setClient_secret("2e55b77ceb9845cd974e9470e4250edb8b4b2024");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8888/callback");

        githubProvider.getAccessToken(accessTokenDTO);
        return "index";
    }
}
