package com.example.pethospital.controller;

import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.Drug;
import com.example.pethospital.service.DrugService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class DrugController {

    @Autowired
    private DrugService drugService;

    // 获取全部药品接口
    @GetMapping("/drug/all")
    public MessageBean<?> getAllDrugs() {
        List<Drug> data = drugService.selectAllDrugs();
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 添加药品接口
    @PostMapping("/drug/add")
    public MessageBean<?> addDrug(@RequestBody Drug drug) {
        drugService.addDrug(drug);
        return new MessageBean<>(MessageCodeEnum.OK, "添加药品成功");
    }

    // 根据药品ID删除药品接口
    @DeleteMapping("/drug/delete/{drugId}")
    public MessageBean<?> deleteDrugById(@PathVariable int drugId) {
        drugService.deleteDrugById(drugId);
        return new MessageBean<>(MessageCodeEnum.OK, "删除药品成功");
    }

    // 更新药品接口
    @PutMapping("/drug/update")
    public MessageBean<?> updateDrug(@RequestBody Drug drug) {
        drugService.updateDrug(drug);
        return new MessageBean<>(MessageCodeEnum.OK, "更新药品成功");
    }

    // 根据药品名称模糊查询接口
    @GetMapping("/drug/searchByName")
    public MessageBean<?> getDrugsByName(@RequestParam String drugName) {
        List<Drug> data = drugService.getDrugsByName(drugName);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据药品ID查询接口
    @GetMapping("drug/searchById")
    public MessageBean<?> getDrugById(@RequestParam int drugId) {
        Drug data = drugService.selectDrugById(drugId);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }
}
