package com.xinbo.mp.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xinbo.mp.common.util.Md5Util;
import com.xinbo.mp.hosp.service.HospitalSetService;
import com.xinbo.mp.model.hosp.HospitalSet;
import com.xinbo.mp.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import result.Result;
import result.ResultCodeEnum;

import java.util.List;
import java.util.Random;

/**
 * @author xinbo
 */
@Api(tags = "医院设置管理", value = "HospitalSetController")
@RestController
@RequestMapping("/admin/hosp/set")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取医院设置列表")
    @GetMapping("/")
    public Result<List<HospitalSet>> findAll() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    @ApiOperation(value = "分页获取医院设置列表")
    @GetMapping("/{offset}/{limit}")
    public Result find(@RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo,
                       @PathVariable Long offset,
                       @PathVariable Long limit) {

        Page<HospitalSet> page = new Page<>(offset, limit);

        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        if (hospitalSetQueryVo != null) {
            if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())) {
                wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
            }
            if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())) {
                wrapper.like("hosname", hospitalSetQueryVo.getHosname());
            }
            return Result.ok(hospitalSetService.page(page, wrapper));
        }
        return Result.ok(hospitalSetService.page(page));

    }

    @ApiOperation(value = "获取指定医院设置")
    @GetMapping("/{id}")
    public Result find(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        if (hospitalSet == null) {
            return Result.build(ResultCodeEnum.FAIL.getCode(), "没有获取到指定医院设置");
        }
        return Result.ok(hospitalSet);
    }

    @ApiOperation(value = "新增医院设置")
    @PostMapping("/")
    public Result add(@RequestBody HospitalSet hospitalSet) {
        hospitalSet.setStatus(1);

        Random random = new Random();
        Md5Util.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000));

        // 验证hoscode是否存在
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        hospitalSetService.getById(hospitalSet.getHoscode());
        if (!StringUtils.isEmpty(hospitalSet.getHoscode())) {
            wrapper.eq("hoscode", hospitalSet.getHoscode());
        }
        if (hospitalSetService.count(wrapper) > 0) {
            return Result.build(ResultCodeEnum.FAIL.getCode(), "医院编码已存在");
        }

        boolean result = hospitalSetService.save(hospitalSet);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "更新医院设置")
    @PutMapping("/")
    public Result update(@RequestBody HospitalSet hospitalSet) {

        boolean result = hospitalSetService.updateById(hospitalSet);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "删除医院设置")
    @DeleteMapping("/")
    public Result delete(@RequestBody List<Long> ids) {
        boolean result = hospitalSetService.removeByIds(ids);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "删除指定医院设置")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        boolean result = hospitalSetService.removeById(id);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "锁定指定医院设置")
    @PutMapping("/{id}/{status}")
    public Result lock(@PathVariable Long id,
                       @PathVariable Integer status) {
        HospitalSet set = hospitalSetService.getById(id);
        set.setStatus(status);

        boolean result = hospitalSetService.updateById(set);
        if (result) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "发送签名密钥")
    @GetMapping("/{id}/sign-key")
    public Result lock(@PathVariable Long id) {
        HospitalSet set = hospitalSetService.getById(id);

        //TODO 后期可以通过发送短信提供签名密钥
        if (set == null) {
            return Result.build(ResultCodeEnum.FAIL.getCode(), "没有获取到指定医院的签名密钥");
        }
        return Result.ok(set);
    }
}
