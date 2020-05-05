package com.jingluo.jingluo.service;

import com.jingluo.jingluo.dto.*;
import com.jingluo.jingluo.vo.ResultInfo;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 17:42
 */
public interface UserService {

    ResultInfo login(UserLoginDto userDto, int type);

    ResultInfo bindPhone(UserValidDto userDto, int type);

    ResultInfo updatePassword(UpdatePSWDTO userDto, int type);

    ResultInfo findPassword(FindPSWDTO findPSWDTO, int type);

    ResultInfo logOut(TokenDto tokenDto, int type);

    ResultInfo phoneLogin(UserPhoneLoginDto loginDto, int type);

    ResultInfo selectOne(String token, int type);

    ResultInfo updateMsg(UserUpdateMsgDto dto, int type);
}
