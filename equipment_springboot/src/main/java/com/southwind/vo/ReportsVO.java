package com.southwind.vo;

import com.southwind.entity.ExperimentReports;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReportsVO extends ExperimentReports {
    private String teacherName;
}
