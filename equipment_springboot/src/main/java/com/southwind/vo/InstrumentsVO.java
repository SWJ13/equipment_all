package com.southwind.vo;

import com.southwind.entity.Instruments;
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
@EqualsAndHashCode(callSuper = true)
public class InstrumentsVO extends Instruments {
  private Boolean statusVO;
  private String statusText;
}
