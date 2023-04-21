package com.example.pethospital.service;

import com.example.pethospital.pojo.Hospitalization;

import java.util.List;

public interface HospitalizationService {
    void addHospitalization(Hospitalization hospitalization);

    List<Hospitalization> selectAllHospitalization();

    int selectHospitalizationCount();

    void deleteHospitalizationById(int id);

    void updateHospitalization(Hospitalization hospitalization);

    List<Hospitalization> getHospitalizationsByName(String patientName);

    Hospitalization selectById(int hospitalizationId);
}
