package com.example.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.community.dto.PaginationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Long totalCount = questionMapper.selectCount(null);
//        System.out.println(totalCount);
        Long totalPage;
        if (totalCount % size == 0) {
            totalPage = (totalCount / size);
        } else {
            totalPage =  (totalCount / size + 1);
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage.intValue();
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.between("id", offset, offset + size - 1);
        List<Question> questions = questionMapper.selectList(wrapper);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(userMapper.selectById(question.getCreator()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;

    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("creator", userId);

        Long totalPage;
        Long totalCount = questionMapper.selectCount(wrapper);
//        System.out.println(totalCount);


        if (totalCount % size == 0) {
            totalPage = (totalCount / size);
        } else {
            totalPage =  (totalCount / size + 1);
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage.intValue();
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);

        wrapper.between("id", offset, offset + size - 1);
        List<Question> questions = questionMapper.selectList(wrapper);

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(userMapper.selectById(question.getCreator()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;

    }
}
