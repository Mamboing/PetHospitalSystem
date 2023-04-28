package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.PayRecordMapper;
import com.example.pethospital.pojo.PayRecord;
import com.example.pethospital.service.PayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "pay-record")
public class PayRecordServiceImpl implements PayRecordService {
    @Autowired
    PayRecordMapper payRecordMapper;

    @Override
    public int addRecord(PayRecord payRecord) {
        return payRecordMapper.addRecord(payRecord);
    }

    @Override
    public List<PayRecord> selectByPage(int page, int size) {
        page = (page - 1) * size;
        return payRecordMapper.selectAllRecordByPage(page, size);
    }

    @Override
    public List<PayRecord> selectAll() {
        return payRecordMapper.selectAllRecord();
    }

    @Override
    public List<PayRecord> selectByName(String personName) {
        return payRecordMapper.selectRecordByName(personName);
    }

    @Override
    public PayRecord selectById(int recordId) {
        return payRecordMapper.selectRecordById(recordId);
    }
}
