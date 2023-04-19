package com.example.pethospital.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 住院管理实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospitalization {
    private int hospitalizationId;      // 住院ID
    private String patientName;         // 住院人姓名
    private int roomNumber;             // 住院房间号
    private double price;               // 住院价格
}
