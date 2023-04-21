package com.example.pethospital.controller;

import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.Patient;
import com.example.pethospital.service.FileService;
import com.example.pethospital.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class PatientController {
    @Resource
    private PatientService patientService;

    @Resource
    private FileService fileService;

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
        // 获取病例有关文件路径列表
        List<String> filePathList = fileService.selectPathById(patientId);

        // 根据文件路径列表在数据库中循环删除该病例有关文件信息
        for(String filePath: filePathList) {
            try {
                File file = new File(filePath);

                // 文件不存在
                if (!file.exists() || !file.isFile()) {
                    return new MessageBean<>(MessageCodeEnum.ERROR, "删除病例对应文件不存在");
                }

                // 文件无法删除
                if (!file.delete()) {
                    return new MessageBean<>(MessageCodeEnum.ERROR, "删除文件失败");
                }

                fileService.deleteFile(filePath);
            } catch (Exception e) {
                log.error("删除病例文件出现异常");
                return new MessageBean<>(MessageCodeEnum.ERROR,"删除病例文件出现异常");
            }
        }

        // 数据库删除病例表中该病例的信息
        patientService.deletePatientById(patientId);

        return new MessageBean<>(MessageCodeEnum.OK, "删除病例成功");
    }

    // 更新病例接口
    @PutMapping("/patient/update")
    public MessageBean<?> updatePatient(@RequestBody Patient patient) {
        patientService.updatePatient(patient);
        return new MessageBean<>(MessageCodeEnum.OK, "更新病例成功");
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
