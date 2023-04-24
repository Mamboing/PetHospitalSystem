package com.example.pethospital.mapper;

import com.example.pethospital.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into tb_user(user_name, password, authority, gender, age) VALUES (#{userName}, #{password}, #{authority}, #{gender}, #{age})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "userId", keyColumn = "user_id", before = false, resultType = int.class)
    int addUser(User user);

    @Select("select * from tb_user")
    List<User> selectAllUser();

    @Select("select * from tb_user where user_id = #{userId}")
    User selectUserById(int id);

    @Select("select * from tb_user where user_name = #{userName}")
    User selectUserByName(String userName);

    @Delete("delete from tb_user where user_id = #{userId}")
    void deleteUserById(int id);

    @Select("select * from tb_user where user_name = #{userName} and password = #{password}")
    User selectUserByPassword(String userName, String password);

    @Select("select count(*) from tb_user where user_name = #{userName}")
    int selectGivenUserCount(String userName);

    @Update("update tb_user set password = #{password} where user_name = #{userName}")
    void updateUserPassword(String userName, String password);

    @Select("select * from tb_user limit #{page},#{size}")
    List<User> selectUserByPage(int page, int size);

    @Update("update tb_user set user_name = #{userName}, authority = #{authority}, gender = #{gender}, age = #{age} where user_id = #{id}")
    void updateUserInformation(int id, String userName, int authority, String gender, int age);
}
