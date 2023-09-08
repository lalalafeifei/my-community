package com.example.community.controller;

import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            HttpServletRequest request,
                            Model model) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title.equals("")) {
            model.addAttribute("error","标题不能为空");
            System.out.println("标题不能为空");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error","问题补充不能为空");
            System.out.println("问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error","标签不能为空");
            System.out.println("标签不能为空");
            return "publish";
        }


//        User user = null;

//        Cookie[] cookies = request.getCookies();
//        if(cookies == null) {
//            model.addAttribute("error","用户未登录");
//            System.out.println("用户未登录");
//            return "publish";
//        }
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("token")) {
//                String token = cookie.getValue();
//                Map<String,Object> condition = new HashMap<>();
//                condition.put("token",token);
//                user = userMapper.selectByMap(condition).get(0);
////                System.out.println("user = " + user);
//                if(user != null) {
//                    request.getSession().setAttribute("user",user);
//                }
//                break;
//            }
//        }

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            System.out.println("用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
//        assert user != null;
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
//        System.out.println(user.getAvatarUrl());
        question.setAvatarUrl(user.getAvatarUrl());
        questionMapper.insert(question);
        System.out.println("questionMapper.insert(question)执行完毕");
        return "redirect:/" ;
    }
}
