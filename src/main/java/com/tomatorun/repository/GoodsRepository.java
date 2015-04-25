package com.tomatorun.repository;

import com.tomatorun.dto.UserGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface GoodsRepository {

    public Map<String,Object> get(Map<String, Object> params);

    public List<Map<String,Object>> list(Map<String, Object> params);

    public void update(Map<String, Object> params);

    public void delete(Map<String, Object> params);

    public void add(Map<String, Object> params);

    /**
     * 供商选择商品
     * @param params
     */
    public void select(Map<String, Object> params);

    /**
     * 删除供商选择的商品
     * @param selectGoodsId
     */
    public void deleteSelectGoods(@Param("selectGoodsId")Long selectGoodsId);

    /**
     * 获取指定的用户商品列表
     * @param userGoodsIds
     * @return
     */
    public List<UserGoods> getUserGoodsForSpecified(@Param("userGoodsIds")Long[] userGoodsIds);

    /**
     * 获取在主页展示的商品列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> listGoods(Map<String, Object> params);

    /**
     * 获取该商品的详细信息
     * @param params
     * @return
     */
    public Map<String,Object> getGoodsDetail(Map<String, Object> params);

    /**
     * 根据userGoodsId获取商品信息
     * @param userGoodsId
     * @return
     */
    public Map<String,Object> getGoodsForShop(@Param("userGoodsId")Long userGoodsId,
                                              @Param("levelDicId")Object levelDicId,@Param("unitDicId")Object unitDicId);

    /**
     * 获取商品产品的规格
     * @param goodsId
     * @return {userGoodsId,goodsId,specification}
     */
    public List<Map<String,Object>> listSpecForShopGoods(@Param("goodsId")Long goodsId,@Param("userId")Long userId);
    /**
     * 更新价格
     * @param price
     * @param id
     */
    public void updatePrice(@Param("price")Double price,@Param("id")Long id);

    /**
     * 更改goodsId
     * @param goodsId
     * @param id
     */
    public void updateGoodsId(@Param("goodsId")Long goodsId,@Param("id")Long id);

    /**
     * 根据id获取商品
     * @param id
     * @return
     */
    public Map<String,Object> getGoodsById(@Param("id")Long id);

    /**
     * 删除类别下的商品
     * @param categoryIds
     */
    public void deleteByCategory(@Param("ids")String categoryIds);
}
