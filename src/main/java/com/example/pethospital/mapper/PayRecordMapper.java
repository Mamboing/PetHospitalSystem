package com.example.pethospital.mapper;

import com.example.pethospital.pojo.PayRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PayRecordMapper {
    @Insert("insert into tb_pay_record(time, person_name, information, total_cost) values (#{time}, #{personName}, #{information}, #{totalCost})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "recordId", keyColumn = "record_id", before = false, resultType = int.class)
    int addRecord(PayRecord payRecord);

    @Select("select * from tb_pay_record limit #{page},#{size}")
    List<PayRecord> selectAllRecordByPage(int page, int size);

    @Select("select * from tb_pay_record")
    List<PayRecord> selectAllRecord();

    @Select("select * from tb_pay_record where person_name = #{personName}")
    List<PayRecord> selectRecordByName(String personName);

    @Select("select * from tb_pay_record where record_id = #{recordId}")
    PayRecord selectRecordById(int recordId);
}
