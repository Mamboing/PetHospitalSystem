package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.FileMapper;
import com.example.pethospital.pojo.HospitalFile;
import com.example.pethospital.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileMapper fileMapper;

    @Override
    public int saveFile(String path, String originName, Timestamp time, int size) {
        HospitalFile hf = new HospitalFile();
        hf.setPath(path);
        hf.setOriginName(originName);
        hf.setTime(time);
        hf.setSize(size);
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
}
