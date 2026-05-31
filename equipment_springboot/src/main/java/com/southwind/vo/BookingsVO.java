package com.southwind.vo;

import com.southwind.entity.Bookings;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookingsVO extends Bookings {
    private String instrumentsName;
}
