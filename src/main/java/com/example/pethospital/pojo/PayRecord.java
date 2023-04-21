package com.example.pethospital.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayRecord {
    @NotNull
    private Integer recordId;
    @NotNull
    private Timestamp time;
    @NotNull
    private String personName;
    @NotNull
    private String information;
    @NotNull
    private Double totalCost;
}
