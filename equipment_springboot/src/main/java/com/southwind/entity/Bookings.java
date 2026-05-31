package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2025-04-12
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Bookings implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer instrumentId;

    private String userName;

    private String userEmail;

    private String userInstitution;

    private LocalDate bookingDate;

    private LocalTime bookingTime;


}
