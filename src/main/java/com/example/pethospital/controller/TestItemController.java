package com.example.pethospital.controller;

import com.example.pethospital.message.MessageBean;
import com.example.pethospital.message.MessageCodeEnum;
import com.example.pethospital.pojo.TestItem;
import com.example.pethospital.service.TestItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class TestItemController {
    @Resource
    private TestItemService testItemService;

    // 获取全部化验项目接口
    @GetMapping("/testItem/all")
    public MessageBean<List<TestItem>> getAllTestItems() {
        List<TestItem> data = testItemService.selectAllTestItem();
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 添加化验项目接口
    @PostMapping("/testItem/add")
    public MessageBean<Object> addTestItem(@RequestBody TestItem testItem) {
        testItemService.addTestItem(testItem);
        return new MessageBean<>(MessageCodeEnum.OK, "添加化验项目成功");
    }

    // 根据化验项目ID删除病例接口
    @DeleteMapping("/testItem/delete/{testItemId}")
    public MessageBean<Object> deleteTestItemById(@PathVariable int testItemId) {
        testItemService.deleteTestItemById(testItemId);
        return new MessageBean<>(MessageCodeEnum.OK, "删除化验项目成功");
    }

    // 更新化验项目接口
    @PutMapping("/testItem/update")
    public MessageBean<Object> updateTestItem(@RequestBody TestItem testItem) {
        testItemService.updateTestItem(testItem);
        return new MessageBean<>(MessageCodeEnum.OK, "更新化验项目成功");
    }

    // 根据化验项目名称查询接口
    @GetMapping("/testItem/searchByName")
    public MessageBean<?> getTestItemsByName(@RequestParam String testItemName) {
        List<TestItem> data = testItemService.getTestItemsByName(testItemName);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }

    // 根据化验项目ID查询接口
    @GetMapping("/testItem/searchById")
    public MessageBean<TestItem> getTestItemById(@RequestParam int testItemId) {
        TestItem data = testItemService.selectById(testItemId);
        return new MessageBean<>(MessageCodeEnum.OK, data);
    }
}
