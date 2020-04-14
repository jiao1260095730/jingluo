package com.jingluo.jingluo.service;

import com.jingluo.jingluo.dto.UserDto;
import com.jingluo.jingluo.vo.ReturnInfo;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 17:42
 */
public interface UserService {

    ReturnInfo login(UserDto userDto, int type);

    ReturnInfo bindPhone(UserDto userDto, int type);

    ReturnInfo updatePassword(UserDto userDto, int type);
}
