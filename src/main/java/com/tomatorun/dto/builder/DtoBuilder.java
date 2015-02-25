package com.tomatorun.dto.builder;


import com.tomatorun.dto.BaseDto;

/**
 * Dto创建器
 * @author:Gavin
 * @date 2015/2/5 0005
 */
public interface DtoBuilder<T extends BaseDto> {

    public T build();

}
