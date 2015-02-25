package com.tomatorun.repository;

import com.tomatorun.dto.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface DTUserRepository {

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
     * 检查用户名是否已经被注册
     * @param userName
     * @return
     */
    public boolean isUserRegistered(@Param("userName") String userName);

    /**
     * 修改用户状态
     * @param id
     */
    public void changeUserStatus(@Param("id") Long id,@Param("status")boolean status);

    /**
     * 重置密码
     * @param id
     */
    public void resetPassword(@Param("id")Long id,@Param("password")String password);

    /**
     * 更新头像
     * @param id
     * @param avatar
     */
    public void updateAvatar(@Param("id")Long id,@Param("avatar")String avatar);

    /**
     * 获取收货地址
     * @param userId
     * @return
     */
    public List<Map<String,Object>> getAddresses(@Param("userId")Long userId);

    /**
     * 添加收货地址
     * @param address
     * @return
     */
    public void addAddress(@Param("address")Address address);
}
