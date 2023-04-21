package com.example.pethospital.service;

import com.example.pethospital.pojo.HospitalFile;

import java.sql.Timestamp;
import java.util.List;

public interface FileService {
    int saveFile(String path, String originName, Timestamp time, int size, int patientId, String formType);
    void deleteFile(String path);
    HospitalFile selectFileByPath(String path);
    void changeFilePath(String oldPath, String newPath);
    HospitalFile[] selectFileByPatientId(int patientId, String formType);

    List<String> selectPathById(int patientId);
}
