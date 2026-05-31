package com.southwind.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.ExperimentReports;
import com.southwind.service.ExperimentReportsService;
import com.southwind.service.TeacherService;
import com.southwind.vo.PageVO;
import com.southwind.vo.ReportsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  实验周报前端控制器
 * </p>
 *
 * @author admin
 * @since 2025-04-18
 */
@RestController
@RequestMapping("/reports")
public class ExperimentReportsController {

    @Autowired
    private ExperimentReportsService reportsService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 获取所有实验周报列表
     * @return 实验周报列表
     */
    @GetMapping("/index")
    public List<ExperimentReports> index(){
        return this.reportsService.list();
    }

    /**
     * 添加实验周报
     * @param content 周报内容
     * @return 是否添加成功
     */
    @PostMapping("/add")
    public Boolean add(String content){
        ExperimentReports reports = new ExperimentReports();
        reports.setContent(content);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        reports.setTime(simpleDateFormat.format(date));
        return this.reportsService.save(reports);
    }

    /**
     * 分页获取实验周报列表
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    @GetMapping("/list")
    public PageVO<ReportsVO> list(
            Integer page,
            Integer size
    ){
        Page<ExperimentReports> pageModel = new Page<>(page, size);
        Page<ExperimentReports> resultPage = this.reportsService.page(pageModel);
        List<ReportsVO> list = new ArrayList<>();
        for (ExperimentReports record : resultPage.getRecords()) {
            ReportsVO reportsVO = new ReportsVO();
            BeanUtils.copyProperties(record,reportsVO);
            // 检查教师ID是否存在，避免空指针异常
            if (record.getTid() != null) {
                com.southwind.entity.Teacher teacher = this.teacherService.getById(record.getTid());
                if (teacher != null) {
                    reportsVO.setTeacherName(teacher.getName());
                } else {
                    reportsVO.setTeacherName("未知");
                }
            } else {
                reportsVO.setTeacherName("未知");
            }
            list.add(reportsVO);
        }
        return new PageVO<>(list,resultPage.getTotal(),resultPage.getSize());
    }
}

