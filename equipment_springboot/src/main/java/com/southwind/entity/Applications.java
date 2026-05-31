package com.southwind.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
    public class Applications implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String name;

    private String type;

    private Integer quantity;

    private BigDecimal budget;

    private Integer urgency;

    private String reason;

    private Date date;

    private Integer tid;

    private String status;



}
