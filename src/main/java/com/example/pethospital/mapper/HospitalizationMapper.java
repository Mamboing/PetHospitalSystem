package com.example.pethospital.mapper;

import com.example.pethospital.pojo.Hospitalization;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HospitalizationMapper {
    @Insert("INSERT INTO tb_hospitalization(patient_name, room_number, date_description, hospitalization_price) VALUES(#{patientName}, #{roomNumber}, #{dateDescription}, #{hospitalizationPrice})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "hospitalizationId", before = false, resultType = int.class)
    void addHospitalization(Hospitalization hospitalization);

    @Select("SELECT * FROM tb_hospitalization")
    List<Hospitalization> selectAllHospitalization();

    @Select("SELECT COUNT(*) FROM tb_hospitalization")
    int selectHospitalizationCount();

    @Delete("DELETE FROM tb_hospitalization WHERE hospitalization_id = #{hospitalizationId}")
    void deleteHospitalizationById(int hospitalizationId);

    @Update("UPDATE tb_hospitalization SET patient_name = #{patientName}, room_number = #{roomNumber}, date_description = #{dateDescription}, hospitalization_price = #{hospitalizationPrice} WHERE hospitalization_id = #{hospitalizationId}")
    void updateById(Hospitalization hospitalization);

    @Select("SELECT * FROM tb_hospitalization WHERE patient_name = #{patientName}")
    List<Hospitalization> selectByPatientName(String patientName);

    @Select("SELECT * FROM tb_hospitalization WHERE hospitalization_id = #{hospitalizationId}")
    Hospitalization selectById(int hospitalizationId);
}
