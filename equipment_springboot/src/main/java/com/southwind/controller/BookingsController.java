package com.southwind.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.Bookings;
import com.southwind.entity.Instruments;
import com.southwind.service.BookingsService;
import com.southwind.service.InstrumentsService;
import com.southwind.vo.BookingsVO;
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
@RequestMapping("/bookings")
public class BookingsController {

    @Autowired
    private BookingsService bookingsService;
    @Autowired
    private InstrumentsService instrumentsService;

    @PostMapping("/add")
    public Boolean add(@RequestBody Bookings bookings){
        //添加预约信息
        boolean save = this.bookingsService.save(bookings);
        //修改仪器状态
        Instruments instruments = this.instrumentsService.getById(bookings.getInstrumentId());
        instruments.setStatus("unavailable");
        boolean updatedById = this.instrumentsService.updateById(instruments);
        return save&&updatedById;
    }

    @GetMapping("/list")
    public PageVO<BookingsVO> list(
            Integer page,
            Integer size
    ){
        Page<Bookings> pageModel = new Page<>(page, size);
        Page<Bookings> resultPage = this.bookingsService.page(pageModel);
        List<BookingsVO> list = new ArrayList<>();
        for (Bookings record : resultPage.getRecords()) {
            BookingsVO vo = new BookingsVO();
            BeanUtils.copyProperties(record, vo);
            vo.setInstrumentsName(this.instrumentsService.getById(record.getInstrumentId()).getName());
            list.add(vo);
        }
        return new PageVO<>(list,resultPage.getTotal(),resultPage.getSize());
    }
}

