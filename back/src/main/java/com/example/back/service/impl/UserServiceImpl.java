package com.example.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.back.controller.dto.UserDTO;
import com.example.back.entity.User;
import com.example.back.mapper.UserMapper;
import com.example.back.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ID 服务实现类
 * </p>
 *
 * @author 小心卓越
 * @since 2024-04-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Boolean login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("passward",userDTO.getPassward());
        try{
            User one = getOne(queryWrapper);
            return one != null;
        } catch (Exception e){
            return false;
        }
    }
}
