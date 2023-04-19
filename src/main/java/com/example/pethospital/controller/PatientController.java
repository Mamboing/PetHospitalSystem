package com.example.pethospital.controller;

import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.Patient;
import com.example.pethospital.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class PatientController {
    @Resource
    private PatientService patientService;

    // 获取全部病例接口
    @GetMapping("/patient/all")
    public MessageBean<?> getAllPatients() {
        List<Patient> data = patientService.selectAllPatient();
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 添加病例接口
    @PostMapping("/patient/add")
    public MessageBean<?> addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
        int data = patient.getPatientId();
        return new MessageBean<>(MessageCodeEnum.OK, data, "添加病例成功");
    }

    // 根据病例ID删除病例接口
    @DeleteMapping("/patient/delete/{patientId}")
    public MessageBean<?> deletePatientById(@PathVariable int patientId) {
        patientService.deletePatientById(patientId);
        return new MessageBean<>(MessageCodeEnum.OK, "删除试题成功");
    }

    // 更新病例接口
    @PutMapping("/patient/update")
    public MessageBean<?> updatePatient(@RequestBody Patient patient) {
        patientService.updatePatient(patient);
        return new MessageBean<>(MessageCodeEnum.OK, "更新试题成功");
    }

    // 根据病例名称模糊查询接口
    @GetMapping("/patient/searchByName")
    public MessageBean<?> getPatientsByName(@RequestParam String name) {
        List<Patient> data = patientService.getPatientsByName(name);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据病例对应病种查询接口
    @GetMapping("/patient/searchByCategory")
    public MessageBean<?> getPatientsByCategory(@RequestParam String category) {
        List<Patient> data = patientService.getPatientsByCategory(category);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据病例ID查询接口
    @GetMapping("/patient/searchById")
    public MessageBean<?> getPatientById(@RequestParam int patientId) {
        Patient data = patientService.selectById(patientId);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }
}
