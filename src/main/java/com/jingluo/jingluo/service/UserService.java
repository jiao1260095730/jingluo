package com.jingluo.jingluo.service;

import com.jingluo.jingluo.dto.UserLoginDto;
import com.jingluo.jingluo.dto.UserUpdatePSWDto;
import com.jingluo.jingluo.dto.UserValidDto;
import com.jingluo.jingluo.vo.ReturnInfo;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 17:42
 */
public interface UserService {

    ReturnInfo login(UserLoginDto userDto, int type);

    ReturnInfo bindPhone(UserValidDto userDto, int type);

    ReturnInfo updatePassword(UserUpdatePSWDto userDto, int type);
}
