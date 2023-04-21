package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.PatientMapper;
import com.example.pethospital.pojo.Patient;
import com.example.pethospital.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {
    @Resource
    private PatientMapper patientMapper;

    @Override
    public void addPatient(Patient patient) {
        patientMapper.addPatient(patient);
    }

    @Override
    public List<Patient> selectAllPatient() {
        return patientMapper.selectAllPatient();
    }

    @Override
    public int selectPatientCount() {
        return patientMapper.selectPatientCount();
    }

    @Override
    public void deletePatientById(int id) {
        patientMapper.deletePatientById(id);

    }

    @Override
    public void updatePatient(Patient patient) { patientMapper.updateById(patient); }

    @Override
    public List<Patient> getPatientsByName(String name) {
        return patientMapper.selectByNameLike(name);
    }

    @Override
    public List<Patient> getPatientsByCategory(String category) {
        return patientMapper.selectByCategory(category);
    }

    @Override
    public Patient selectById(int patientId) {
        return patientMapper.selectById(patientId);
    }

}
