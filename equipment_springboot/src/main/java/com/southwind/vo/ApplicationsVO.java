package com.southwind.vo;

import com.southwind.entity.Applications;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationsVO extends Applications {
    private String tname;


}
