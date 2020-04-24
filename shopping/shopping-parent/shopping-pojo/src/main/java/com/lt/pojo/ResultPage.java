package com.lt.pojo;

import java.io.Serializable;
import java.util.List;

public class ResultPage implements Serializable {
    private List rows;//记录分页记录
    private Long total;//记录分页总数

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }



}
