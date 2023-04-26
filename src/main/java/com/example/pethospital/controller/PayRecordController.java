package com.example.pethospital.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.PayInfo;
import com.example.pethospital.pojo.PayRecord;
import com.example.pethospital.pojo.RecordDTO;
import com.example.pethospital.service.PayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/payRecord")
@Slf4j
public class PayRecordController {
    @Autowired
    PayRecordService payRecordService;

    @PostMapping("/addRecord")
    public MessageBean<?> addNewRecord(@Valid @RequestBody RecordDTO recordDTO){
        PayRecord payRecord = new PayRecord();
        payRecord.setPersonName(recordDTO.getPersonName());
        payRecord.setTotalCost(recordDTO.getTotalCost());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        payRecord.setTime(timestamp);

        JSONObject[] jsonObject = new JSONObject[recordDTO.getInformation().length];
        int i = 0;
        for(PayInfo info : recordDTO.getInformation()){
            JSONObject json = new JSONObject();
            json.put("item", info.getItem());
            json.put("number", info.getNumber());
            json.put("price", info.getPrice());
            jsonObject[i] = json;
            i++;
        }
        payRecord.setInformation(Arrays.toString(jsonObject));
        int ok = payRecordService.addRecord(payRecord);
        if(ok == 1){
            return new MessageBean<>(MessageCodeEnum.OK, "添加成功");
        }
        else{
            return new MessageBean<>(MessageCodeEnum.NO, "添加失败");
        }
    }

    @RequestMapping("/get/{recordId}")
    public MessageBean<?> getRecordById(@PathVariable int recordId){
        if(recordId <= 0){
            return new MessageBean<>(MessageCodeEnum.INVALID_PARAMS);
        }
        PayRecord payRecord = payRecordService.selectById(recordId);
        return new MessageBean<>(MessageCodeEnum.OK, payRecord, "成功");
    }

    @RequestMapping("/getByPage")
    public MessageBean<?> getAllRecord(@RequestParam int page, @RequestParam int size){
        if(page < 0 || size <= 0){
            return new MessageBean<>(MessageCodeEnum.INVALID_PARAMS);
        }
        List<PayRecord> list = payRecordService.selectByPage(page, size);
        return new MessageBean<>(MessageCodeEnum.OK, list, "成功");
    }

    @RequestMapping("/getAll")
    public MessageBean<?> getAll(){
        List<PayRecord> list = payRecordService.selectAll();
        return new MessageBean<>(MessageCodeEnum.OK, list, "成功");
    }
}
