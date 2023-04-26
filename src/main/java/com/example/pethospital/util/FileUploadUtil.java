package com.example.pethospital.util;

import com.example.pethospital.message.exception.ErrorCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 实现文件上传
 * description: FileUploadUtil
 * date: 2022-05-29 16:24
 * author: Jun.T
 */
@Slf4j
public class FileUploadUtil {

    /**
     * @throws throw IllegalStateException when util class is instantiated
     * @Title: FileUploadUtil
     * @Description: Add a private constructor to hide the implicit public one
     * @Author: Jun.T
     * @DateTime: 2022/7/13 21:59
     */
    private FileUploadUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 默认大小 20M
     * 视频大小上限 100M
     */
    public static final long DEFAULT_MAX_SIZE = 20L * 1024 * 1024;
    public static final long VIDEO_MAX_SIZE = 100L * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int FILE_NAME_MAX = 100;

    /**
     * 按照默认的配置上传文件
     *
     * @param file 文件
     * @return 文件名
     * @throws IOException
     */
//    public static String upload(MultipartFile file) throws IOException {
//
//        try {
//            //合法性校验
//            assertAllowed(file);
//            //保存时的文件名
//            String dateName = encodingFileName(file);
//            //保存文件的绝对路径
//            String filePath = System.getProperty("user.dir") + File.separator + "OTAFiles" + File.separator + dateName;
//            log.info("绝对路径:" + filePath);
//            File desc = new File(filePath);
//            if (!desc.getParentFile().exists()) {
//                desc.getParentFile().mkdirs();
//            }
//            if (!desc.exists() || !desc.createNewFile()) {
//                log.error("createNewFile fails");
//            }
//            //上传文件
//            file.transferTo(desc);
//            return desc.getAbsolutePath();
//        } catch (Exception e) {
//            throw new IOException(e.getMessage(), e);
//        }
//    }

    /**
     * 对文件名特殊处理一下
     *
     * @param file 文件
     * @return
     */
    public static String encodedFileName(MultipartFile file) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePath = simpleDateFormat.format(new Date());
        String extension = getExtension(file);
        return datePath + "-" + UUID.randomUUID() + "." + extension;
    }


    /**
     * 文件合法性校验
     *
     * @param file 上传的文件
     */
    public static void assertAllowed(MultipartFile file) throws ErrorCodeException{
        if (file.getOriginalFilename() != null) {
            int fileNameLength = file.getOriginalFilename().length();
            if (fileNameLength > FILE_NAME_MAX) {
                throw new ErrorCodeException("文件名过长");
            }
        }

        long size = file.getSize();
        if (isVideo(file)) {
            if(size > VIDEO_MAX_SIZE)
                throw new ErrorCodeException("视频过大");
        }
        else if (size > DEFAULT_MAX_SIZE) {
            throw new ErrorCodeException("文件过大");
        }

    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return getExtension(fileName);
    }

    public static String getExtension(String fileName) {
        String extension = null;
        if (fileName == null) {
            return null;
        } else {
            int index = indexOfExtension(fileName);
            extension = index == -1 ? "" : fileName.substring(index + 1);
        }
        return extension;
    }

    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int lastUnixPos = filename.lastIndexOf(47);
            int lastWindowsPos = filename.lastIndexOf(92);
            return Math.max(lastUnixPos, lastWindowsPos);
        }
    }

    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int extensionPos = filename.lastIndexOf(46);
            int lastSeparator = indexOfLastSeparator(filename);
            return lastSeparator > extensionPos ? -1 : extensionPos;
        }
    }

    public static boolean isImage(MultipartFile file){
        String extension = getExtension(file);
        if(extension == null) return false;
        switch (extension){
            case "bmp": case "jpg": case "jpeg": case "png": case "gif":
                return true;
        }
        return false;
    }

    public static boolean isVideo(MultipartFile file){
        String extension = getExtension(file);
        if(extension == null) return false;
        switch (extension){
            case "asx": case "asf": case "mpg": case "wmv": case "3gp":
            case "mp4": case "mov": case "avi": case "flv": case "rmvb":
                return true;
        }
        return false;
    }
    public static boolean isImage(String path){
        String extension = getExtension(path);
        if(extension == null) return false;
        switch (extension){
            case "bmp": case "jpg": case "jpeg": case "png": case "gif":
                return true;
        }
        return false;
    }

    public static boolean isVideo(String path){
        String extension = getExtension(path);
        if(extension == null) return false;
        switch (extension){
            case "asx": case "asf": case "mpg": case "wmv": case "3gp":
            case "mp4": case "mov": case "avi": case "flv": case "rmvb":
                return true;
        }
        return false;
    }
}
