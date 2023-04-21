package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.HospitalizationMapper;
import com.example.pethospital.pojo.Hospitalization;
import com.example.pethospital.service.HospitalizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class HospitalizationServiceImpl implements HospitalizationService {
    @Resource
    private HospitalizationMapper hospitalizationMapper;

    @Override
    public void addHospitalization(Hospitalization hospitalization) {
        hospitalizationMapper.addHospitalization(hospitalization);
    }

    @Override
    public List<Hospitalization> selectAllHospitalization() {
        return hospitalizationMapper.selectAllHospitalization();
    }

    @Override
    public int selectHospitalizationCount() {
        return hospitalizationMapper.selectHospitalizationCount();
    }

    @Override
    public void deleteHospitalizationById(int id) {
        hospitalizationMapper.deleteHospitalizationById(id);
    }

    @Override
    public void updateHospitalization(Hospitalization hospitalization) {
        hospitalizationMapper.updateById(hospitalization);
    }

    @Override
    public List<Hospitalization> getHospitalizationsByName(String patientName) {
        return hospitalizationMapper.selectByPatientName(patientName);
    }

    @Override
    public Hospitalization selectById(int hospitalizationId) {
        return hospitalizationMapper.selectById(hospitalizationId);
    }
}
