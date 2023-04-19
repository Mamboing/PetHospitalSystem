package com.example.pethospital.controller;

import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.Vaccine;
import com.example.pethospital.service.VaccineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class VaccineController {
    @Resource
    private VaccineService vaccineService;

    // 获取全部疫苗接口
    @GetMapping("/vaccine/all")
    public MessageBean<?> getAllVaccines() {
        List<Vaccine> data = vaccineService.selectAllVaccine();
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 添加疫苗接口
    @PostMapping("/vaccine/add")
    public MessageBean<?> addVaccines(@RequestBody Vaccine vaccine) {
        vaccineService.addVaccine(vaccine);
        return new MessageBean<>(MessageCodeEnum.OK, "添加疫苗成功");
    }

    // 根据疫苗ID删除疫苗接口
    @DeleteMapping("/vaccine/delete/{vaccineId}")
    public MessageBean<?> deleteVaccineById(@PathVariable int vaccineId) {
        vaccineService.deleteVaccineById(vaccineId);
        return new MessageBean<>(MessageCodeEnum.OK, "删除疫苗成功");
    }

    // 更新疫苗接口
    @PutMapping("/vaccine/update")
    public MessageBean<?> updateVaccine(@RequestBody Vaccine vaccine) {
        vaccineService.updateVaccine(vaccine);
        return new MessageBean<>(MessageCodeEnum.OK, "更新疫苗成功");
    }

    // 根据疫苗名称模糊查询接口
    @GetMapping("/vaccine/searchByName")
    public MessageBean<?> getVaccinesByName(@RequestParam String vaccineName) {
        List<Vaccine> data = vaccineService.getVaccinesByName(vaccineName);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据疫苗ID查询接口
    @GetMapping("/vaccine/searchById")
    public MessageBean<?> getVaccineById(@RequestParam int vaccineId) {
        Vaccine data = vaccineService.selectById(vaccineId);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }
}
