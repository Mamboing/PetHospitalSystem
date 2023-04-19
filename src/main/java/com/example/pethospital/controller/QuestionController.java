package com.example.pethospital.controller;


import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.Answer;
import com.example.pethospital.pojo.Question;
import com.example.pethospital.pojo.Score;
import com.example.pethospital.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class QuestionController {
    @Resource
    private QuestionService questionService;

    // 获取全部试题接口
    @GetMapping("/question/all")
    public MessageBean<?> getAllQuestion() {
        List<Question> data = questionService.selectAllQuestion();
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 添加试题接口
    @PostMapping("/question/add")
    public MessageBean<?> addNewQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        int data = question.getQuestionId();
        return new MessageBean<>(MessageCodeEnum.OK, data, "添加试题成功");
    }

    // 根据试题id删除试题接口
    @DeleteMapping("/question/delete/{questionId}")
    public MessageBean<?> deleteQuestion(@PathVariable int questionId) {
        questionService.deleteQuestionById(questionId);
        return new MessageBean<>(MessageCodeEnum.OK, "删除试题成功");
    }

    // 更新试题接口
    @PutMapping("/question/update")
    public MessageBean<?> updateQuestion(@RequestBody Question question) {
        questionService.updateQuestion(question);
        return new MessageBean<>(MessageCodeEnum.OK, "更新试题成功");
    }

    // 根据试题内容模糊查询接口
    @GetMapping("/question/searchByContent")
    public MessageBean<?> searchByContent(@RequestParam String content) {
        List<Question> data = questionService.selectByContent(content);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据试题对应病种查询接口
    @GetMapping("/question/searchByCategory")
    public MessageBean<?> searchByCategory(@RequestParam String category) {
        List<Question> data = questionService.selectByCategory(category);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据试题id查询接口
    @GetMapping("/question/searchById")
    public MessageBean<?> searchById(@RequestParam int questionId) {
        Question data = questionService.selectById(questionId);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 自动生成试卷接口
    @PostMapping("/question/generate")
    public MessageBean<?> generatePaper(@RequestBody List<String> categories) {
        if (categories == null || categories.isEmpty() || categories.size() > 5) {
            return new MessageBean<>(MessageCodeEnum.NO, "问题种类数量不符合需求");
        }

        List<Question> data = questionService.generatePaper(categories);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 自动计分接口（传入包含问题id和用户答案的List）
    @PostMapping("/question/score")
    public MessageBean<?> calculateScore(@RequestBody List<Answer> answers) {
        List<Score> data = questionService.calculateScore(answers);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }
}
