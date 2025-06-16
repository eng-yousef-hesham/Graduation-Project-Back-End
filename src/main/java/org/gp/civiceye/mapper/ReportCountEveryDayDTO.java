package org.gp.civiceye.mapper;

import lombok.Data;


import java.sql.Timestamp;

@Data
public class ReportCountEveryDayDTO {

    private Timestamp date;
    private Long reportCount;

    public ReportCountEveryDayDTO(Timestamp date, Long reportCount) {
        this.date = date;
        this.reportCount = reportCount;
    }


}
