package com.jingluo.jingluo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.RedisConfig;
<<<<<<< HEAD
import com.jingluo.jingluo.dto.UserLoginDto;
import com.jingluo.jingluo.dto.UserUpdatePSWDto;
import com.jingluo.jingluo.dto.UserValidDto;
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.mapper.TeacherMapper;
=======
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.mapper.TeacherMapper;
import com.jingluo.jingluo.dto.UserDto;
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.entity.Teacher;
import com.jingluo.jingluo.service.UserService;
import com.jingluo.jingluo.utils.NumberUtil;
import com.jingluo.jingluo.utils.RedissonUtil;
import com.jingluo.jingluo.utils.StringUtil;
import com.jingluo.jingluo.vo.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/12 17:42
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private StudentMapper studentDao;

    @Autowired
    private TeacherMapper teacherDao;

    /**
     * 登录
     */
    @Override
<<<<<<< HEAD
    public ReturnInfo login(UserLoginDto userDto, int type) {
=======
    public ReturnInfo login(UserDto userDto, int type) {
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
        //校验参数是否为空
        if (!StringUtil.isEmpty(userDto.getUserCode(), userDto.getPassword())) {
            //区分学生、教师，学生为1  教师为2
            if (type == 1) {
                Student student = studentDao.selectByCode(userDto.getUserCode());
                if (student == null) {
                    LoggerCommon.commonerror("没有从数据库中查询到用户信息，用户不存在");
                    return ReturnInfo.fail("学生信息不存在");
                }
                //MD5加密
                String password = NumberUtil.getMd5Str(userDto.getPassword());
                LoggerCommon.commoninfo("学生账号：" + userDto.getUserCode() + "的加密后密码为" + password);
                //校验输入密码和教师密码是否一致
                if (StringUtils.equals(password, student.getPassword())) {
                    LoggerCommon.commonerror("登陆成功");
                    return ReturnInfo.success("登录成功");
                }
                LoggerCommon.commonerror("密码不正确");
                return ReturnInfo.fail("密码不正确");
            }
            if (type == 2) {
                Teacher teacher = teacherDao.selectByCode(userDto.getUserCode());
                if (teacher == null) {
                    LoggerCommon.commonerror("没有从数据库中查询到用户信息，用户不存在");
                    return ReturnInfo.fail("教师信息不存在");
                }
                //MD5加密
                String password = NumberUtil.getMd5Str(userDto.getPassword());
                LoggerCommon.commoninfo("教师账号：" + userDto.getUserCode() + "的加密后密码为" + password);
                //校验输入密码和教师密码是否一致
                if (StringUtils.equals(password, teacher.getPassword())) {
                    LoggerCommon.commonerror("登陆成功");
                    return ReturnInfo.success("登录成功");
                }
                LoggerCommon.commonerror("密码不正确");
                return ReturnInfo.fail("密码不正确");
            }
        }
        LoggerCommon.commonerror("用户名密码为空，操作失败");
        return ReturnInfo.fail("用户名密码为空，操作失败");
    }

    /**
     * 校验验证码，绑定手机号
     */
    @Override
<<<<<<< HEAD
    public ReturnInfo bindPhone(UserValidDto userDto, int type) {
=======
    public ReturnInfo bindPhone(UserDto userDto, int type) {
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
        try {
            //校验redis中是否存在
            if (RedissonUtil.checkKey(RedisConfig.SMS_CODE_BIND + userDto.getPhone())) {
                //校验验证码是否相同
<<<<<<< HEAD
                if (StringUtils.equals(userDto.getCode(), RedissonUtil.getStr(RedisConfig.SMS_CODE_BIND + userDto.getPhone()))) {
=======
                if (StringUtils.equals(userDto.getValidateCode(), RedissonUtil.getStr(RedisConfig.SMS_CODE_BIND + userDto.getPhone()))) {
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
                    //区分学生、教师，学生为1  教师为2
                    if (type == 1) {
                        Student student = new Student();
                        student.setPhone(userDto.getPhone());
<<<<<<< HEAD
                        student.setStudentCode(userDto.getUserCode());
=======
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
                        //绑定手机号
                        LoggerCommon.commoninfo("验证通过");
                        if (studentDao.update(student) == 1) {
                            LoggerCommon.commoninfo("更新用户手机号成功");
                            return ReturnInfo.success("验证码验证通过");
                        }
                        return ReturnInfo.fail("更改用户数据异常");
                    }
                    if (type == 2) {
                        Teacher teacher = new Teacher();
                        teacher.setPhone(userDto.getPhone());
<<<<<<< HEAD
                        teacher.setTeacherCode(userDto.getUserCode());
=======
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
                        //绑定手机号
                        LoggerCommon.commoninfo("验证通过");
                        if (teacherDao.update(teacher) == 1) {
                            LoggerCommon.commoninfo("更新用户手机号成功");
                            return ReturnInfo.success("验证码验证通过");
                        }
                        return ReturnInfo.fail("更改用户数据异常");
                    }

                }
                LoggerCommon.commonerror("输入的验证码校验不通过");
                return ReturnInfo.fail("输入的验证码校验不通过");

            }
            LoggerCommon.commonerror("校验失败，没有获取到redis中数据");
            return ReturnInfo.fail("校验失败，没有获取到redis中数据");
        } catch (Exception e) {
            LoggerCommon.commonerror("绑定手机号实现类中出现异常");
            return ReturnInfo.fail("出现异常");
        }
    }

    @Override
<<<<<<< HEAD
    public ReturnInfo updatePassword(UserUpdatePSWDto userDto, int type) {
=======
    public ReturnInfo updatePassword(UserDto userDto, int type) {
>>>>>>> bb48385dad6f33bdf1c2257aad7490069620ca51
        try {
            String userCode = userDto.getUserCode();
            String oldPassword = userDto.getOldPassword();
            String password = userDto.getPassword();
            String validateCode = userDto.getValidateCode();
            String phone = userDto.getPhone();
            if (userDto.getOldPassword() != null) {
                //oldPassword字段不为空则为使用就密码更改密码
                if (type == 1) {
                    //type 为 1 是学生
                    Student student = studentDao.selectByCode(userCode);
                    if (StringUtils.equals(student.getPassword(), oldPassword)) {
                        Student student1 = new Student();
                        student1.setStudentCode(userCode);
                        student1.setPassword(password);
                        //密码校验通过
                        if (studentDao.update(student1) == 1) {
                            //密码重置成功
                            LoggerCommon.commoninfo("重置学生密码成功");
                            return ReturnInfo.success("重置学生密码成功");
                        }
                        LoggerCommon.commonerror("重置密码时更新数据库失败");
                        return ReturnInfo.fail("重置密码时更新数据库失败");
                    }
                    LoggerCommon.commonerror("旧密码不正确");
                    return ReturnInfo.fail("旧密码不正确");
                } else if (type == 2) {
                    //type 为 2 是教师
                    Teacher teacher = teacherDao.selectByCode(userCode);
                    if (StringUtils.equals(teacher.getPassword(), oldPassword)) {
                        Teacher teacher1 = new Teacher();
                        teacher1.setPassword(password);
                        teacher1.setTeacherCode(userCode);
                        //密码校验通过
                        if (teacherDao.update(teacher) == 1) {
                            //密码重置成功
                            LoggerCommon.commoninfo("重置教师密码成功");
                            return ReturnInfo.success("重置教师密码成功");
                        }
                        LoggerCommon.commonerror("重置密码时更新数据库失败");
                        return ReturnInfo.fail("重置密码时更新数据库失败");
                    }
                    LoggerCommon.commonerror("旧密码不正确");
                    return ReturnInfo.fail("旧密码不正确");
                }
            }
            if (userDto.getValidateCode() != null) {
                if (!RedissonUtil.checkKey(RedisConfig.SMS_CODE_FIND + phone)) {
                    //Redis中没有对应的验证码、验证码已过期
                    LoggerCommon.commonerror("redis中没有对应的验证码");
                    return ReturnInfo.fail("redis中没有对应的验证码，验证码过期");
                }
                //找回密码时使用验证码校验
                if (StringUtils.equals(validateCode, RedisConfig.SMS_CODE_FIND + phone)) {
                    //校验通过，重置密码
                    if (type == 1) {
                        //学生
                        Student student = new Student();
                        student.setStudentCode(userCode);
                        student.setPassword(password);

                        if (studentDao.update(student) == 1) {
                            LoggerCommon.commoninfo("重置学生密码成功");
                            return ReturnInfo.success("重置学生密码成功");
                        }
                    } else if (type == 2) {
                        //老师
                        Teacher teacher = new Teacher();
                        teacher.setTeacherCode(userCode);
                        teacher.setPassword(password);
                        if (teacherDao.update(teacher) == 1) {
                            LoggerCommon.commoninfo("重置教师密码成功");
                            return ReturnInfo.success("重置教师密码成功");
                        }
                    }
                    LoggerCommon.commonerror("重置密码失败");
                    return ReturnInfo.fail("重置密码失败");
                }
                LoggerCommon.commonerror("验证码不正确");
                return ReturnInfo.fail("验证码不正确");
            }
            return ReturnInfo.fail("重置密码失败");
        } catch (Exception e) {
            LoggerCommon.commonerror("修改密码时出异常");
            return ReturnInfo.fail("修改密码时出异常");
        }
    }
}
