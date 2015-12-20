package com.meidusa.venus.registry.domain.datatables;

import com.meidusa.fastjson.JSON;
import com.meidusa.venus.registry.domain.Jsonable;

import java.util.List;

/**
 * Created by huawei on 12/19/15.
 */
public class DataTableResult<T> implements Jsonable {

    private int draw;
    private int recordsTotal = 0;
    private int recordsFiltered = 0;
    private List<T> data;
    private String error;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toJson(){
        return JSON.toJSONString(this);
    }
}
