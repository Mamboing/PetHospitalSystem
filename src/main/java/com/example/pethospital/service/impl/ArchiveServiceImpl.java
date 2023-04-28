package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.ArchiveMapper;
import com.example.pethospital.pojo.Archive;
import com.example.pethospital.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "archive")
public class ArchiveServiceImpl implements ArchiveService {

    @Resource
    private ArchiveMapper archiveMapper;

    @Override
    public void addArchive(Archive archive) {
        archiveMapper.addArchive(archive);
    }

    @Override
    public List<Archive> selectAllArchive() {
        return archiveMapper.selectAllArchive();
    }

    @Override
    public int selectArchiveCount() {
        return archiveMapper.selectArchiveCount();
    }

    @Override
    public void deleteArchiveById(int archiveId) {
        archiveMapper.deleteArchiveById(archiveId);
    }

    @Override
    public void updateArchive(Archive archive) {
        archiveMapper.updateById(archive);
    }

    @Override
    public List<Archive> getArchivesByName(String archiveName) {
        return archiveMapper.selectByNameLike(archiveName);
    }

    @Override
    public Archive getArchiveById(int archiveId) {
        return archiveMapper.selectById(archiveId);
    }
}
