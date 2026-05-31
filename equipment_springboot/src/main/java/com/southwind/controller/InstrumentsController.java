package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Instruments;
import com.southwind.service.InstrumentsService;
import com.southwind.vo.InstrumentsVO;
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
@RequestMapping("/instruments")
public class InstrumentsController {

    @Autowired
    private InstrumentsService instrumentsService;

    @GetMapping("/index")
    public List<Instruments> index(){
        return this.instrumentsService.list();
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody Instruments instruments){
        return this.instrumentsService.save(instruments);
    }

    @PutMapping("/update")
    public Boolean update(@RequestBody Instruments instruments){
        return this.instrumentsService.updateById(instruments);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        return this.instrumentsService.removeById(id);
    }

    @GetMapping("/list")
    public PageVO<InstrumentsVO> list(
            Integer page,
            Integer size,
            String type
    ){
        Page<Instruments> pageModel = new Page<>(page, size);
        QueryWrapper<Instruments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(type), "type", type);
        Page<Instruments> resultPage = this.instrumentsService.page(pageModel, queryWrapper);
        List<InstrumentsVO> list = new ArrayList<>();
        for (Instruments record : resultPage.getRecords()) {
            InstrumentsVO vo = new InstrumentsVO();
            BeanUtils.copyProperties(record, vo);
            if(record.getStatus().equals("available")){
                vo.setStatusVO(true);
                vo.setStatusText("可用");
            } else {
                vo.setStatusVO(false);
                vo.setStatusText("不可用");
            }
            list.add(vo);
        }
        return new PageVO<>(list,resultPage.getTotal(),resultPage.getSize());
    }
}

