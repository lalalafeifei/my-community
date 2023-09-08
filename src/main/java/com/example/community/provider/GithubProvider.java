package com.example.community.provider;

import com.alibaba.fastjson2.JSON;
import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        System.out.println("body: " + body);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string(); // access_token=gho_16aKZqF8g0gJGxYdlZEBBxxxxxxxxxxxxxxxxxxxx&scope=user%3Aemail&token_type=bearer
            String[] split = string.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];

            System.out.println("token: " + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .header("Authorization", "token " + accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            GithubUser githubUser = JSON.parseObject(response.body().string(), GithubUser.class);
            return githubUser;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
