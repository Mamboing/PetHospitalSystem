package com.example.pethospital.service;

import com.example.pethospital.pojo.HospitalFile;

import java.sql.Timestamp;

public interface FileService {
    int saveFile(String path, String originName, Timestamp time, int size);
    void deleteFile(String path);
    HospitalFile selectFileByPath(String path);
    void changeFilePath(String oldPath, String newPath);
}
