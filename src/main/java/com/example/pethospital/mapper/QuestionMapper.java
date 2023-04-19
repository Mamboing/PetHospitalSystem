package com.example.pethospital.mapper;

import com.example.pethospital.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO tb_question(category, content, option_a, option_b, option_c, option_d, answer, score) " +
            "VALUES (#{category}, #{content}, #{optionA}, #{optionB}, #{optionC}, #{optionD}, #{answer}, #{score})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "questionId", before = false, resultType = int.class)
    void addQuestion(Question question);

    @Select("SELECT * FROM tb_question")
    List<Question> selectAllQuestion();

    @Select("SELECT count(*) FROM tb_question")
    int selectQuestionCount();

    @Delete("DELETE FROM tb_question WHERE question_id = #{questionId}")
    void deleteQuestionById(int id);

    @Select("SELECT * FROM tb_question WHERE category = #{category} ORDER BY RAND() LIMIT #{count}")
    List<Question> selectRandomQuestionByCategory(String category, int count);

    @Select("SELECT * FROM tb_question WHERE content LIKE CONCAT('%',#{content},'%')")
    List<Question> selectByContent(String content);

    @Select("SELECT * FROM tb_question WHERE category = #{category}")
    List<Question> selectByCategory(String category);

    @Update("UPDATE tb_question SET category = #{category}, content = #{content}, option_a = #{optionA}, option_b = #{optionB}, option_c = #{optionC}, option_d = #{optionD}, answer = #{answer}, score = #{score} WHERE question_id = #{questionId}")
    void updateById(Question question);

    @Select("SELECT * FROM tb_question WHERE question_id = #{questionId}")
    Question selectById(int questionId);
}
