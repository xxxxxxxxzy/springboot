package com.example.back.service;

import com.example.back.controller.dto.UserDTO;
import com.example.back.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * ID 服务类
 * </p>
 *
 * @author 小心卓越
 * @since 2024-04-02
 */
public interface IUserService extends IService<User> {

    Boolean login(UserDTO userDTO);
}
