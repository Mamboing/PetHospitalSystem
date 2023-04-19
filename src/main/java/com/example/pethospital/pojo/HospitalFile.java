package com.example.pethospital.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalFile {
    private int fileId;
    private String path;
    private String originName;
    private Timestamp time;
    private int size;
    private int patientId;
    private String formType;
}
