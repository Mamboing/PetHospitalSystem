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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    public MessageBean<?> saveNewFile(@RequestPart(value = "file", required = false) MultipartFile file, @RequestParam int patientId, @RequestParam String formType){
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

            int ok = fileService.saveFile(filePath, file.getOriginalFilename(), timestamp, (int)file.getSize(), patientId, formType);
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
    public MessageBean<?>[] saveNewFiles(@RequestPart MultipartFile[] files, @RequestParam int patientId, @RequestParam String formType){
        MessageBean<?>[] res = new MessageBean[files.length];
        for(int i=0; i<files.length; i++){
            MultipartFile file = files[i];
            res[i] = saveNewFile(file, patientId, formType);
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

    @PostMapping("/update")
    public MessageBean<?> updateFile(@RequestPart MultipartFile file, @RequestParam String path){
        HospitalFile hf = fileService.selectFileByPath(path);
        if(FileUploadUtil.isImage(file)){
            if(!FileUploadUtil.isImage(hf.getPath())){
                return new MessageBean<>(MessageCodeEnum.NO, "请不要输入图片");
            }
        }
        else if(FileUploadUtil.isVideo(file)){
            if(!FileUploadUtil.isVideo(hf.getPath())){
                return new MessageBean<>(MessageCodeEnum.NO, "请不要输入视频");
            }
        }
        else{
            return new MessageBean<>(MessageCodeEnum.NO, "请不要输入非图片和视频");
        }
        int patientId = hf.getPatientId();
        String formType = hf.getFormType();
        MessageBean<?> messageBean = deleteFile(path);
        if(messageBean.getCode() != MessageCodeEnum.OK.getCode())
            return messageBean;
        return saveNewFile(file, patientId, formType);
    }

    @RequestMapping("/getImages")
    public MessageBean<?> getImages(@RequestParam int patientId, @RequestParam String formType){
        HospitalFile[] files = fileService.selectFileByPatientId(patientId, formType);
        JSONObject json = new JSONObject();
        List<String> paths = new ArrayList<>();
        for(HospitalFile file : files){
            if(FileUploadUtil.isImage(file.getPath())){
                paths.add(file.getPath());
            }
        }
        json.put("path", paths);
        return new MessageBean<>(MessageCodeEnum.OK, json, "返回成功");
    }

    @RequestMapping("/getVideos")
    public MessageBean<?> getVideos(@RequestParam int patientId, @RequestParam String formType){
        HospitalFile[] files = fileService.selectFileByPatientId(patientId, formType);
        JSONObject json = new JSONObject();
        List<String> paths = new ArrayList<>();
        for(HospitalFile file : files){
            if(FileUploadUtil.isVideo(file.getPath())){
                paths.add(file.getPath());
            }
        }
        json.put("path", paths);
        return new MessageBean<>(MessageCodeEnum.OK, json, "返回成功");
    }

    @PostMapping("/transformType")
    public MessageBean<?> changeFileType(@RequestPart MultipartFile file, @RequestParam String targetType, @RequestParam int patientId, @RequestParam String formType){
        if(!FileUploadUtil.isImage(file)) return new MessageBean<>(MessageCodeEnum.NO, "不是图片文件");
        if(!targetType.equals("bmp") && !targetType.equals("jpg") && !targetType.equals("jpeg") && !targetType.equals("png"))
            return new MessageBean<>(MessageCodeEnum.NO, "无法转换为该格式");
        MessageBean<?> messageBean = saveNewFile(file, patientId, formType);
        if(!messageBean.getMsg().equals("上传成功")) return messageBean;
        JSONObject jsonObject = (JSONObject) messageBean.getData();
        String url = (String) jsonObject.get("path");
        try{
            BufferedImage bi = ImageIO.read(new File(url));
            String newPath = url.substring(0, url.lastIndexOf(".")) + "." + targetType;
            ImageIO.write(bi, targetType, new File(newPath));

            fileService.changeFilePath(url, newPath);
            MessageBean<?> mb = deleteFile(url);
            if(mb.getCode() != MessageCodeEnum.OK.getCode()) return mb;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new MessageBean<>(MessageCodeEnum.OK, "转换成功");
    }
}
