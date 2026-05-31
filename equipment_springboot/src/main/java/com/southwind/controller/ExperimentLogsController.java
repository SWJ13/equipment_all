package com.southwind.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.ExperimentLogs;
import com.southwind.service.ExperimentLogsService;
import com.southwind.service.TeacherService;
import com.southwind.vo.LogsVO;
import com.southwind.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
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
 *  实验日志前端控制器
 * </p>
 *
 * @author admin
 * @since 2025-04-18
 */
@Slf4j
@RestController
@RequestMapping("/logs")
public class ExperimentLogsController {

    @Autowired
    private ExperimentLogsService logsService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 获取所有实验日志列表
     * @return 实验日志列表
     */
    @GetMapping("/index")
    public List<ExperimentLogs> index(){
        return this.logsService.list();
    }

    /**
     * 添加实验日志
     * @param content 日志内容
     * @return 是否添加成功
     */
    @PostMapping("/add")
    public Boolean add(String content){
        ExperimentLogs log = new ExperimentLogs();
        log.setContent(content);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        log.setTime(simpleDateFormat.format(date));
        return this.logsService.save(log);
    }

    /**
     * 分页获取实验日志列表
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    @GetMapping("/list")
    public PageVO<LogsVO> list(
            Integer page,
            Integer size
    ){
        Page<ExperimentLogs> pageModel = new Page<>(page, size);
        Page<ExperimentLogs> resultPage = this.logsService.page(pageModel);
        List<LogsVO> list = new ArrayList<>();
        for (ExperimentLogs record : resultPage.getRecords()) {
            LogsVO logsVO = new LogsVO();
            BeanUtils.copyProperties(record,logsVO);
            // 检查教师ID是否存在，避免空指针异常
            if (record.getTid() != null) {
                com.southwind.entity.Teacher teacher = this.teacherService.getById(record.getTid());
                if (teacher != null) {
                    logsVO.setTeacherName(teacher.getName());
                } else {
                    logsVO.setTeacherName("未知");
                }
            } else {
                logsVO.setTeacherName("未知");
            }
            list.add(logsVO);
        }
        return new PageVO<>(list,resultPage.getTotal(),resultPage.getSize());
    }
}

