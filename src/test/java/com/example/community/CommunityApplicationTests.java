package com.example.community;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class CommunityApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }


    @Test
    void testSelect() throws SQLException {
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList.size());
        for (User user : userList
             ) {
            System.out.println(user);
            String name = user.getName();
            System.out.println(name);

        }
    }

    @Test
    void testInsert() throws SQLException {
        User user = new User();
        user.setToken("123");
        user.setName("123");
        user.setAccountId("123");
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        userMapper.insert(user);
    }

}
