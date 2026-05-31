package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Applications;
import com.southwind.service.ApplicationsService;
import com.southwind.service.TeacherService;
import com.southwind.vo.ApplicationsVO;
import com.southwind.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2025-04-12
 */
@RestController
@RequestMapping("/applications")
public class ApplicationsController {

    @Autowired
    private ApplicationsService applicationsService;
    @Autowired
    private TeacherService teacherService;


    @GetMapping("/index")
    public List<Applications> index(){
        return this.applicationsService.list();
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody Applications applications){
        return this.applicationsService.save(applications);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        return this.applicationsService.removeById(id);
    }

    @GetMapping("/list")
    public PageVO<ApplicationsVO> list(
            Integer page,
            Integer size,
            String status
    ){
        Page<Applications> pageModel = new Page<>(page, size);
        QueryWrapper<Applications> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(status), "status", status);
        Page<Applications> resultPage = this.applicationsService.page(pageModel, queryWrapper);
        List<ApplicationsVO> list = new ArrayList<>();
        for (Applications record : resultPage.getRecords()) {
            ApplicationsVO vo = new ApplicationsVO();
            BeanUtils.copyProperties(record, vo);
            vo.setTname(this.teacherService.getById(record.getTid()).getName());
            list.add(vo);
        }
        return new PageVO<>(list,resultPage.getTotal(),resultPage.getSize());
    }

    @PostMapping("/examine/{id}")
    public Boolean examine(@PathVariable("id") Integer id){
        Applications applications = this.applicationsService.getById(id);
        applications.setStatus("已审核");
        return this.applicationsService.updateById(applications);
    }
}

