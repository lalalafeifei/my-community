package com.example.community.provider;

import com.example.community.dto.AccessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string(); // access_token=gho_16aKZqF8g0gJGxYdlZEBBxxxxxxxxxxxxxxxxxxxx&scope=user%3Aemail&token_type=bearer
            System.out.println(string);
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

}
