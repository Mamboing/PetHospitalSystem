package com.example.pethospital.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 病例
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @NotNull
    private Integer patientId;  // 病例ID
    @NotNull
    private String name;    // 病例名称
    private String owner;   // 主人
    private String address;    // 地址
    private String phone;   // 电话
    private String petName; // 宠物名
    private String type;    // 种类
    private String variety; // 品种*/
    private String category;    // 病种类别
    private int age;    // 年龄
    private char sex;   // 性别
    private String immunity;    // 免疫
    private String weight;  // 体重
    private String state;   // 接诊状态
    private String diagnoseProcess; // 诊疗过程和方法
    private String result;  // 诊断结果
    private String treatment;   // 治疗方案
}
