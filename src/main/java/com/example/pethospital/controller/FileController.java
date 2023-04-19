package com.example.pethospital.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.HospitalFile;
import com.example.pethospital.service.FileService;
import com.example.pethospital.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public MessageBean<?> saveNewFile(@RequestPart(value = "file", required = false) MultipartFile file){
        JSONObject data = new JSONObject();
        try{
            FileUploadUtil.assertAllowed(file);
            String filePath = System.getProperty("user.dir") + File.separator + "petHospitalFiles" + File.separator;
            String newName = FileUploadUtil.encodedFileName(file);
            if(FileUploadUtil.isVideo(file)){
                filePath += "video";
            }
            else if(FileUploadUtil.isImage(file)){
                filePath += "image";
            }
            else{
                filePath += "file";
            }
            filePath = filePath + File.separator + newName;

            File newFile = new File(filePath);
            if(!newFile.getParentFile().exists()){
                newFile.getParentFile().mkdirs();
            }
            if(newFile.exists()){
                String msg = "file already exists";
                return new MessageBean<>(MessageCodeEnum.NO, msg);
            }
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            file.transferTo(newFile);

            int ok = fileService.saveFile(filePath, file.getOriginalFilename(), timestamp, (int)file.getSize());
            if(ok == 1){
                String msg = "上传成功";
                data.put("path", filePath);
                return new MessageBean<>(MessageCodeEnum.OK, data, msg);
            }
            else{
                String msg = "上传失败";
                newFile.delete();
                return new MessageBean<>(MessageCodeEnum.NO, msg);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new MessageBean<>(MessageCodeEnum.ERROR, "IOException");
    }

    @PostMapping("/uploadMulti")
    public MessageBean<?>[] saveNewFiles(@RequestPart MultipartFile[] files){
        MessageBean<?>[] res = new MessageBean[files.length];
        for(int i=0; i<files.length; i++){
            MultipartFile file = files[i];
            res[i] = saveNewFile(file);
        }
        return res;
    }

    @PostMapping("/delete")
    public MessageBean<?> deleteFile(@RequestParam String path){
        JSONObject json = new JSONObject();
        HospitalFile hf = fileService.selectFileByPath(path);
        File file = new File(path);
        if(hf == null || !file.exists() || !file.isFile()){
            String msg = "file not exists";
            return new MessageBean<>(MessageCodeEnum.NO, msg);
        }
        boolean ok = file.delete();
        if(!ok){
            String msg = "fail to delete file";
            return new MessageBean<>(MessageCodeEnum.ERROR, msg);
        }
        fileService.deleteFile(path);
        String msg = "delete success";
        return new MessageBean<>(MessageCodeEnum.OK, msg);
    }
}
