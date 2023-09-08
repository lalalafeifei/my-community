package com.example.community;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.community.dto.PaginationDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class CommunityApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Test
    void contextLoads() {
    }


    @Test
    void testSelect() throws SQLException {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        Integer offset = 1;
        Integer size = 5;
//        wrapper.between("id",offset,offset + size - 1);
//        List<Question> questions = questionMapper.selectList(wrapper);
//        Long totalCount = questionMapper.selectCount(null);
//        Integer num = 1;
//        System.out.println(totalCount-num);
//        System.out.println(questions.size());

        PaginationDTO pagination = questionService.list(offset,size);
        System.out.println(pagination.getQuestions().size());
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
