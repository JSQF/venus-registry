package com.meidusa.venus.registry.domain;

import java.util.List;

/**
 * Created by huawei on 12/21/15.
 */
public class Pagination<T> {

    private List<T> result;
    private int count = 0;

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
