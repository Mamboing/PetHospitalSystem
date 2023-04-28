package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.QuestionMapper;
import com.example.pethospital.pojo.Answer;
import com.example.pethospital.pojo.Question;
import com.example.pethospital.pojo.Score;
import com.example.pethospital.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "question")
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionMapper  questionMapper;

    private static final int TOTAL_QUESTIONS = 20;

    @Override
    public void addQuestion(Question question) {
        questionMapper.addQuestion(question);
    }

    @Override
    public List<Question> selectAllQuestion() {
        return questionMapper.selectAllQuestion();
    }

    @Override
    public int selectQuestionCount() {
        return questionMapper.selectQuestionCount();
    }

    @Override
    public void deleteQuestionById(int id) {
        questionMapper.deleteQuestionById(id);
    }

    @Override
    public void updateQuestion(Question question) { questionMapper.updateById(question); }

    @Override
    public List<Question> selectByContent(String content) {
        return questionMapper.selectByContent(content);
    }

    @Override
    public List<Question> selectByCategory(String category) {
        return questionMapper.selectByCategory(category);
    }

    @Override
    public Question selectById(int questionId) {
        return questionMapper.selectById(questionId);
    }

    @Override
    public List<Question> generatePaper(List<String> categories) {
        int base = 20 / categories.size();  // 每个病种分配的基本的题目数量
        int remainder = 20 % categories.size();  // 剩余的未分配的题目数量

        // 每个种类题目均匀分配
        List<Question> questions = new ArrayList<>(TOTAL_QUESTIONS);
        List<Integer> questionCount = new ArrayList<>(Collections.nCopies(categories.size(), base));

        // 将剩余未分配的题目按顺序分配给每个病种
        for (int i = 0; i < remainder; i++) {
            questionCount.set(i, base + 1);
        }

        // 随机选择题目
        for(int j = 0; j < categories.size(); j++) {
            List<Question> questionResult = questionMapper.selectRandomQuestionByCategory(categories.get(j), questionCount.get(j));
            questions.addAll(questionResult);
        }

        return questions;
    }

    @Override
    public List<Score> calculateScore(List<Answer> answers) {
        List<Score> scoreList = new ArrayList<>();

        for (Answer answer : answers) {
            boolean result = false;
            int point = 0;

            int questionId = answer.getQuestionId();
            String userAnswer = answer.getUserAnswer();
            Question question = questionMapper.selectById(questionId);

            if (question != null && question.getAnswer().equals(userAnswer)) {
                result = true;
                point = 5;
            }

            Score score = new Score(questionId, result, point);
            scoreList.add(score);
        }
        return scoreList;
    }
}
