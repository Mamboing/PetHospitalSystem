package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.VaccineMapper;
import com.example.pethospital.pojo.Vaccine;
import com.example.pethospital.service.VaccineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
@CacheConfig(cacheNames = "vaccine")
public class VaccineServiceImpl implements VaccineService {

    @Resource
    private VaccineMapper vaccineMapper;

    @Override
    public void addVaccine(Vaccine vaccine) {
        vaccineMapper.addVaccine(vaccine);
    }

    @Override
    public List<Vaccine> selectAllVaccine() {
        return vaccineMapper.selectAllVaccine();
    }

    @Override
    public Vaccine selectById(int vaccineId) {
        return vaccineMapper.selectById(vaccineId);
    }

    @Override
    public List<Vaccine> getVaccinesByName(String vaccineName) {
        return vaccineMapper.selectByNameLike(vaccineName);
    }

    @Override
    public void deleteVaccineById(int vaccineId) {
        vaccineMapper.deleteVaccineById(vaccineId);
    }

    @Override
    public void updateVaccine(Vaccine vaccine) {
        vaccineMapper.updateById(vaccine);
    }
}
