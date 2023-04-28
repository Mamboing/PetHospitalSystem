package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.FileMapper;
import com.example.pethospital.pojo.HospitalFile;
import com.example.pethospital.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "file")
public class FileServiceImpl implements FileService {
    @Autowired
    FileMapper fileMapper;

    @Override
    public int saveFile(String path, String originName, Timestamp time, int size, int patientId, String formType) {
        HospitalFile hf = new HospitalFile();
        hf.setPath(path);
        hf.setOriginName(originName);
        hf.setTime(time);
        hf.setSize(size);
        hf.setPatientId(patientId);
        hf.setFormType(formType);
        return fileMapper.uploadFile(hf);
    }

    @Override
    public void deleteFile(String path) {
        fileMapper.deleteFileByPath(path);
    }

    @Override
    public HospitalFile selectFileByPath(String path) {
        return fileMapper.selectFileByPath(path);
    }

    @Override
    public void changeFilePath(String oldPath, String newPath) {
        fileMapper.updatePath(oldPath, newPath);
    }

    @Override
    public HospitalFile[] selectFileByPatientId(int patientId, String formType) {
        return fileMapper.selectFileByPatientId(patientId, formType);
    }

    @Override
    public List<String> selectPathById(int patientId) {
        return fileMapper.selectPathById(patientId);
    }
}
