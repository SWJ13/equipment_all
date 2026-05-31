package com.southwind.vo;

import com.southwind.entity.ExperimentLogs;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LogsVO extends ExperimentLogs {
    private String teacherName;
}
