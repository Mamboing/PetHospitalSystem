package com.example.pethospital.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 住院管理实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospitalization {
    @NotNull(message = "住院ID不能为空")
    private Integer hospitalizationId;      // 住院ID
    @NotNull(message = "住院人姓名不能为空")
    private String patientName;         // 住院人姓名
    @NotNull(message = "住院房间号不能为空")
    private Integer roomNumber;             // 住院房间号
    @NotNull(message = "住院价格不能为空")
    private Double price;               // 住院价格
}
