package com.xinbo.mp.hosp.controller;

import com.xinbo.mp.hosp.service.HospitalSetService;
import com.xinbo.mp.model.hosp.HospitalSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xinbo
 */
@Api(tags = "医院设置管理", value = "HospitalSetController")
@RestController
@RequestMapping("/admin/hosp")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取医院设置列表")
    @GetMapping("/set")
    public List<HospitalSet> findAll() {
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

}
