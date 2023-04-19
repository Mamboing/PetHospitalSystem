package com.example.pethospital.controller;

import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.Hospitalization;
import com.example.pethospital.service.HospitalizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class HospitalizationController {
    @Resource
    private HospitalizationService hospitalizationService;

    // 获取全部住院信息接口
    @GetMapping("/hospitalization/all")
    public MessageBean<?> getAllHospitalizations() {
        List<Hospitalization> data = hospitalizationService.selectAllHospitalization();
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 添加住院记录接口
    @PostMapping("/hospitalization/add")
    public MessageBean<?> addHospitalization(@RequestBody Hospitalization hospitalization) {
        hospitalizationService.addHospitalization(hospitalization);
        return new MessageBean<>(MessageCodeEnum.OK, "添加住院记录成功");
    }

    // 根据住院ID删除住院记录接口
    @DeleteMapping("/hospitalization/delete/{hospitalizationId}")
    public MessageBean<?> deleteHospitalizationById(@PathVariable int hospitalizationId) {
        hospitalizationService.deleteHospitalizationById(hospitalizationId);
        return new MessageBean<>(MessageCodeEnum.OK, "删除住院记录成功");
    }

    // 更新住院记录接口
    @PutMapping("/hospitalization/update")
    public MessageBean<?> updateHospitalization(@RequestBody Hospitalization hospitalization) {
        hospitalizationService.updateHospitalization(hospitalization);
        return new MessageBean<>(MessageCodeEnum.OK, "更新住院记录成功");
    }

    // 根据住院人姓名查询接口
    @GetMapping("/hospitalization/searchByName")
    public MessageBean<?> getHospitalizationsByName(@RequestParam String patientName) {
        List<Hospitalization> data = hospitalizationService.getHospitalizationsByName(patientName);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据住院ID查询接口
    @GetMapping("hospitalization/searchById")
    public MessageBean<?> getHospitalizationById(@RequestParam int hospitalizationId) {
        Hospitalization data = hospitalizationService.selectById(hospitalizationId);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }
}
