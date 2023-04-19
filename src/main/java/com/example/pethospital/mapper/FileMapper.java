package com.example.pethospital.mapper;

import com.example.pethospital.pojo.HospitalFile;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMapper {
    @Insert("insert into tb_file(path, origin_name, time, size, patient_id, form_type) values (#{path}, #{originName}, #{time}, #{size}, #{patientId}, #{formType})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "fileId", keyColumn = "file_id", before = false, resultType = int.class)
    int uploadFile(HospitalFile file);

    @Select("select * from tb_file where path = #{path}")
    HospitalFile selectFileByPath(String path);

    @Delete("delete from tb_file where path = #{path}")
    void deleteFileByPath(String path);

    @Update("update tb_file set size = #{size} where path = #{path}")
    void updateFile(String path, int size);

    @Update("update tb_file set path = #{newPath} where path = #{oldPath}")
    void updatePath(String oldPath, String newPath);

    @Select("select * from tb_file where patient_id = #{patientId} and form_type = #{formType}")
    HospitalFile[] selectFileByPatientId(int patientId, String formType);
}
