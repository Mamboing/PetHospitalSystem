package com.example.pethospital.service.impl;

import com.example.pethospital.mapper.DrugMapper;
import com.example.pethospital.pojo.Drug;
import com.example.pethospital.service.DrugService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "drug")
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugMapper drugMapper;

    @Override
    public void addDrug(Drug drug) {
        drugMapper.addDrug(drug);
    }

    @Override
    public void deleteDrugById(int drugId) {
        drugMapper.deleteDrugById(drugId);
    }

    @Override
    public void updateDrug(Drug drug) {
        drugMapper.updateDrug(drug);
    }

    @Override
    public List<Drug> selectAllDrugs() {
        return drugMapper.selectAllDrugs();
    }

    @Override
    public List<Drug> getDrugsByName(String drugName) {
        return drugMapper.selectDrugsByName(drugName);
    }

    @Override
    public Drug selectDrugById(int drugId) {
        return drugMapper.selectDrugById(drugId);
    }
}
