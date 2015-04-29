package com.tomatorun.service.impl;

import com.reeham.component.ddd.model.ModelContainer;
import com.reeham.component.ddd.model.ModelUtils;
import com.tomatorun.dto.Address;
import com.tomatorun.repository.DTUserRepository;
import com.tomatorun.service.DTUserService;
import org.moon.base.service.AbstractService;
import org.moon.pagination.Pager;
import org.moon.rbac.domain.User;
import org.moon.utils.Objects;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DTUserServiceImpl extends AbstractService implements DTUserService {

    @Resource
    private DTUserRepository dTUserRepository;

    @Resource
    private ModelContainer modelContainer;

    @Override
    public Map<String, Object> get(Map<String,Object> params) {
        return dTUserRepository.get(params);
    }

    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return dTUserRepository.list(params);
    }

    @Override
    public void update(Map<String, Object> params) {
        dTUserRepository.update(params);
    }

    @Override
    public void delete(Map<String,Object> params) {
        dTUserRepository.delete(params);
    }

    @Override
    public void add(Map<String, Object> params) {
        dTUserRepository.add(params);
    }

    @Override
    public void register(Map<String, Object> params) {
        dTUserRepository.register(params);
    }

    @Override
    public boolean isUserRegistered(String userName) { return dTUserRepository.isUserRegistered(userName); }

    @Override
    public void activeUser(Long id) {
        modelContainer.removeModel(ModelUtils.asModelKey(User.class,id));
        dTUserRepository.changeUserStatus(id, true);
    }

    @Override
    public void disableUser(Long id) {
        modelContainer.removeModel(ModelUtils.asModelKey(User.class,id));
        dTUserRepository.changeUserStatus(id, false);
    }

    @Override
    public void resetPassword(Long id,String password) {
        modelContainer.removeModel(ModelUtils.asModelKey(User.class,id));
        dTUserRepository.resetPassword(id,password);
    }

    @Override
    public void updateAvatar(Long id, String avatar) {
        dTUserRepository.updateAvatar(id,avatar);
    }

    @Override
    public List<Map<String, Object>> getAddresses(Long userId) {
        return dTUserRepository.getAddresses(userId);
    }

    @Override
    public Address addAddress(Address address) {
        dTUserRepository.addAddress(address);
        return address;
    }

    @Override
    public void deleteAddress(Long addressId) {
        dTUserRepository.deleteAddress(addressId);
    }

    @Override
    public Address getAddress(Long addressId) {
        return dTUserRepository.getAddress(addressId);
    }

    @Override
    public Address updateAddress(Address address) {
        dTUserRepository.updateAddress(address);
        return address;
    }

    @Override
    public void setDefaultAddress(Address address) {
        dTUserRepository.unSetDefaultAddress(address.getUserId());
        dTUserRepository.setDefaultAddress(address);
    }

    @Override
    public Double getBalance(Long userId) {
        Double balance = dTUserRepository.getBalance(userId);
        return Objects.nonNull(balance) ? balance : 0d;
    }

    @Override
    public void consume(Double balance, Long userId) {
        dTUserRepository.consume(balance,userId);
    }

    @Override
    public void updatePrice(Long userId,Double balance) {
        dTUserRepository.updateBalance(userId,balance);
    }

    @Override
    public void earnPrice(Long userId, Double balance) {
        dTUserRepository.earnBalance(userId,balance);
    }
}
