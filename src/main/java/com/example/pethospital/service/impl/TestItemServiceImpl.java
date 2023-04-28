package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.TestItemMapper;
import com.example.pethospital.pojo.TestItem;
import com.example.pethospital.service.TestItemService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@CacheConfig(cacheNames = "test-item")
public class TestItemServiceImpl implements TestItemService {
    @Resource
    private TestItemMapper testItemMapper;

    @Override
    public void addTestItem(TestItem testItem) {
        testItemMapper.addTestItem(testItem);
    }

    @Override
    public List<TestItem> selectAllTestItem() {
        return testItemMapper.selectAllTestItem();
    }

    @Override
    public int selectTestItemCount() {
        return 0;
    }

    @Override
    public void deleteTestItemById(int testItemId) {
        testItemMapper.deleteTestItemById(testItemId);
    }

    @Override
    public void updateTestItem(TestItem testItem) {
        testItemMapper.updateTestItem(testItem);
    }

    @Override
    public List<TestItem> getTestItemsByName(String testItemName) {
        return testItemMapper.selectByName(testItemName);
    }

    @Override
    public TestItem selectById(int testItemId) {
        return testItemMapper.selectById(testItemId);
    }
}