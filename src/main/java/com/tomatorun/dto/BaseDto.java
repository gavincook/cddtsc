package com.tomatorun.dto;

import java.io.Serializable;

/**
 * 数据传输对象
 * @author:Gavin
 * @date 2015/2/5 0005
 */
public class BaseDto implements Serializable {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
