package com.southwind.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Notices;
import com.southwind.service.NoticesService;
import com.southwind.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2025-04-18
 */
@RestController
@RequestMapping("/notices")
public class NoticesController {

    @Autowired
    private NoticesService noticesService;

    @GetMapping("/index")
    public List<Notices> index(){
        return this.noticesService.list();
    }

    @GetMapping("/list")
    public PageVO<Notices> list(
            Integer page,
            Integer size
    ){
        Page<Notices> pageModel = new Page<>(page, size);
        Page<Notices> resultPage = this.noticesService.page(pageModel);
        return new PageVO<>(resultPage.getRecords(),resultPage.getTotal(),resultPage.getSize());
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody Notices notices){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        notices.setDate(simpleDateFormat.format(date));
        return this.noticesService.save(notices);
    }

    @PutMapping("/update")
    public Boolean update(@RequestBody Notices notices){
        return this.noticesService.updateById(notices);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        return this.noticesService.removeById(id);
    }
}

