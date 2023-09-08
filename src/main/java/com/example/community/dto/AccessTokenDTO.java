package com.example.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state; // state is used to prevent CSRF attacks, and can also be used to identify the user's login status

}
