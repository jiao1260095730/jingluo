package com.jingluo.jingluo.service;

<<<<<<< HEAD
import com.jingluo.jingluo.dto.UserLoginDto;
import com.jingluo.jingluo.dto.UserUpdatePSWDto;
import com.jingluo.jingluo.dto.UserValidDto;
=======
import com.jingluo.jingluo.dto.UserDto;
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
import com.jingluo.jingluo.vo.ReturnInfo;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 17:42
 */
public interface UserService {

<<<<<<< HEAD
    ReturnInfo login(UserLoginDto userDto, int type);

    ReturnInfo bindPhone(UserValidDto userDto, int type);

    ReturnInfo updatePassword(UserUpdatePSWDto userDto, int type);
=======
    ReturnInfo login(UserDto userDto, int type);

    ReturnInfo bindPhone(UserDto userDto, int type);

    ReturnInfo updatePassword(UserDto userDto, int type);
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
}
