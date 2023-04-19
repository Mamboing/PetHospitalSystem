package com.example.pethospital.controller;

import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.Archive;
import com.example.pethospital.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class ArchiveController {

    @Resource
    private ArchiveService archiveService;

    // 获取全部档案接口
    @GetMapping("/archive/all")
    public MessageBean<?> getAllArchives() {
        List<Archive> data = archiveService.selectAllArchive();
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 添加档案接口
    @PostMapping("/archive/add")
    public MessageBean<?> addArchive(@RequestBody Archive archive) {
        archiveService.addArchive(archive);
        return new MessageBean<>(MessageCodeEnum.OK, "添加档案成功");
    }

    // 根据档案ID删除档案接口
    @DeleteMapping("/archive/delete/{archiveId}")
    public MessageBean<?> deleteArchiveById(@PathVariable int archiveId) {
        archiveService.deleteArchiveById(archiveId);
        return new MessageBean<>(MessageCodeEnum.OK, "删除档案成功");
    }

    // 更新档案接口
    @PutMapping("/archive/update")
    public MessageBean<?> updateArchive(@RequestBody Archive archive) {
        archiveService.updateArchive(archive);
        return new MessageBean<>(MessageCodeEnum.OK, "更新档案成功");
    }

    // 根据档案名称模糊查询接口
    @GetMapping("/archive/searchByName")
    public MessageBean<?> getArchivesByName(@RequestParam String archiveName) {
        List<Archive> data = archiveService.getArchivesByName(archiveName);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据档案ID查询接口
    @GetMapping("/archive/searchById")
    public MessageBean<?> getArchiveById(@RequestParam int archiveId) {
        Archive data = archiveService.getArchiveById(archiveId);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }
}
