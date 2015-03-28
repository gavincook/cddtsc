package com.tomatorun.repository;

import com.tomatorun.dto.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author:Gavin
 * @date 2015/3/28 0028
 */
@Repository
public interface ShopRepository {

    public void add(@Param("shop")Shop shop);

    public void update(@Param("shop")Shop shop);

    public void delete(@Param("shop")Shop shop);

    /**
     * 根据userId 检查是否存在对应用户的店铺
     * @param userId
     * @return
     */
    public Shop checkIfExists(@Param("userId")Long userId);

    public List<Map<String,Object>> list(@Param("shop")Shop shop);

}
