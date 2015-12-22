package com.meidusa.venus.registry.utils;

import com.meidusa.venus.registry.domain.datatables.DataTableResult;

import java.util.List;

/**
 * Created by huawei on 12/20/15.
 */
public class DataTableUtils {

    public static <T> DataTableResult<T> getDataTableResult(int draw, int recordsTotal, int recordsFiltered, List<T> data, String error) {
        DataTableResult<T> result = new DataTableResult<>();
        result.setDraw(draw);
        result.setRecordsTotal(recordsTotal);
        result.setRecordsFiltered(recordsFiltered);
        if (data != null) {
            result.setData(data);
        }
        return result;

    }
}
