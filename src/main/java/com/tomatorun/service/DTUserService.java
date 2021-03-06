package com.tomatorun.service;

import com.tomatorun.dto.Address;
import org.moon.base.service.BaseService;
import org.moon.pagination.Pager;

import java.util.List;
import java.util.Map;

public interface DTUserService extends BaseService {

    /**
     * 获取详情
     * @param params
     * @return
     */
    public Map<String,Object> get(Map<String, Object> params);

    /**
     * 获取用户列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> list(Map<String, Object> params);

    /**
     * 更新用户
     * @param params
     */
    public void update(Map<String, Object> params);

    /**
     * 删除用户
     * @param params
     */
    public void delete(Map<String, Object> params);

    /**
     * 添加用户
     * @param params
     */
    public void add(Map<String, Object> params);

    /**
     * 注册
     * @param params
     */
    public void register(Map<String,Object> params);

    /**
     * 检查用户名是否注册
     * @param userName
     * @return
     */
    public boolean isUserRegistered(String userName);

    /**
     * 激活用户
     * @param id
     */
    public void activeUser(Long id);

    /**
     * 禁用用户
     * @param id
     */
    public void disableUser(Long id);

    /**
     * 重置密码
     * @param id
     */
    public void resetPassword(Long id,String password);

    /**
     * 更新用户头像
     * @param id
     * @param avatar
     */
    public void updateAvatar(Long id,String avatar);


    /**
     * 获取收货地址
     * @param userId
     * @return
     */
    public List<Map<String,Object>> getAddresses(Long userId);

    /**
     * 添加收货地址
     * @param address
     */
    public Address addAddress(Address address);

    /**
     * 删除收货地址
     * @param addressId
     */
    public void deleteAddress(Long addressId);

    /**
     * 获取地址
     * @param addressId
     * @return
     */
    public Address getAddress(Long addressId);

    /**
     * 更新收货地址
     * @param address
     * @return
     */
    public Address updateAddress(Address address);

    /**
     * 设置默认地址
     * @param address
     */
    public void setDefaultAddress(Address address);

    /**
     * 获取用户余额
     * @param userId
     * @return
     */
    public Double getBalance(Long userId);

    /**
     * 消费
     * @param balance
     * @param userId
     */
    public void consume(Double balance,Long userId);

    /**
     * 更新余额
     * @param balance
     * @param userId
     */
    public void updatePrice(Long userId,Double balance);


    public void earnPrice(Long userId,Double balance);
}
