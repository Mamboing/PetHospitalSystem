package com.example.pethospital.service;

import com.example.pethospital.pojo.PayRecord;

import java.util.List;

public interface PayRecordService {
    int addRecord(PayRecord payRecord);
    List<PayRecord> selectByPage(int page, int size);
    List<PayRecord> selectAll();
    List<PayRecord> selectByName(String personName);
    PayRecord selectById(int recordId);
}
