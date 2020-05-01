package com.jingluo.jingluo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.RedisConfig;
import com.jingluo.jingluo.dto.FindPSWDTO;
import com.jingluo.jingluo.dto.UserLoginDto;
import com.jingluo.jingluo.dto.UpdatePSWDTO;
import com.jingluo.jingluo.dto.UserValidDto;
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.mapper.TeacherMapper;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.entity.Teacher;
import com.jingluo.jingluo.service.UserService;
import com.jingluo.jingluo.utils.NumberUtil;
import com.jingluo.jingluo.utils.RedissonUtil;
import com.jingluo.jingluo.utils.StringUtil;
import com.jingluo.jingluo.vo.ResultInfo;
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
    public ResultInfo login(UserLoginDto userDto, int type) {
        //校验参数是否为空
        if (!StringUtil.isEmpty(userDto.getUserCode(), userDto.getPassword())) {
            //区分学生、教师，学生为1  教师为2
            if (type == 1) {
                Student student = studentDao.selectByCode(userDto.getUserCode());
                if (student == null) {
                    LoggerCommon.error("没有从数据库中查询到用户信息，用户不存在");
                    return ResultInfo.fail("学生信息不存在");
                }
                //MD5加密
                String password = NumberUtil.getMd5Str(userDto.getPassword());
                LoggerCommon.info("学生账号：" + userDto.getUserCode() + "的加密后密码为" + password);
                //校验输入密码和教师密码是否一致
                if (StringUtils.equals(password, student.getPassword())) {

                    LoggerCommon.error("登陆成功");
                    return ResultInfo.success("登录成功");
                }
                LoggerCommon.error("密码不正确");
                return ResultInfo.fail("密码不正确");
            }
            if (type == 2) {
                Teacher teacher = teacherDao.selectByCode(userDto.getUserCode());
                if (teacher == null) {
                    LoggerCommon.error("没有从数据库中查询到用户信息，用户不存在");
                    return ResultInfo.fail("教师信息不存在");
                }
                //MD5加密
                String password = NumberUtil.getMd5Str(userDto.getPassword());
                LoggerCommon.info("教师账号：" + userDto.getUserCode() + "的加密后密码为" + password);
                //校验输入密码和教师密码是否一致
                if (StringUtils.equals(password, teacher.getPassword())) {
                    LoggerCommon.error("登陆成功");
                    return ResultInfo.success("登录成功");
                }
                LoggerCommon.error("密码不正确");
                return ResultInfo.fail("密码不正确");
            }
        }
        LoggerCommon.error("用户名密码为空，操作失败");
        return ResultInfo.fail("用户名密码为空，操作失败");
    }

    /**
     * 校验验证码，绑定手机号
     */
    @Override
    public ResultInfo bindPhone(UserValidDto userDto, int type) {
        try {
            //校验redis中是否存在
            if (RedissonUtil.checkKey(RedisConfig.SMS_CODE_BIND + userDto.getPhone())) {
                //校验验证码是否相同
                if (StringUtils.equals(userDto.getCode(), RedissonUtil.getStr(RedisConfig.SMS_CODE_BIND + userDto.getPhone()))) {
                    //区分学生、教师，学生为1  教师为2
                    if (type == 1) {
                        Student student = new Student();
                        student.setPhone(userDto.getPhone());
                        student.setStudentCode(userDto.getUserCode());
                        //绑定手机号
                        LoggerCommon.info("验证通过");
                        if (studentDao.update(student) == 1) {
                            LoggerCommon.info("更新用户手机号成功");
                            return ResultInfo.success("验证码验证通过");
                        }
                        return ResultInfo.fail("更改用户数据异常");
                    }
                    if (type == 2) {
                        Teacher teacher = new Teacher();
                        teacher.setPhone(userDto.getPhone());
                        teacher.setTeacherCode(userDto.getUserCode());
                        //绑定手机号
                        LoggerCommon.info("验证通过");
                        if (teacherDao.update(teacher) == 1) {
                            LoggerCommon.info("更新用户手机号成功");
                            return ResultInfo.success("验证码验证通过");
                        }
                        return ResultInfo.fail("更改用户数据异常");
                    }

                }
                LoggerCommon.error("输入的验证码校验不通过");
                return ResultInfo.fail("输入的验证码校验不通过");

            }
            LoggerCommon.error("校验失败，没有获取到redis中数据");
            return ResultInfo.fail("校验失败，没有获取到redis中数据");
        } catch (Exception e) {
            LoggerCommon.error("绑定手机号实现类中出现异常");
            return ResultInfo.fail("出现异常");
        }
    }

    /**
     * 使用旧密码修改密码
     */
    @Override
    public ResultInfo updatePassword(UpdatePSWDTO userDto, int type) {
        try {
            //获取参数值
            String userCode = userDto.getUserCode();
            String oldPassword = userDto.getOldPassword();
            String newPassword = userDto.getNewPassword();

            //判断学生1、老师2
            if (type == 1) {
                //type 为 1 是学生
                Student student = studentDao.selectByCode(userCode);
                if (StringUtils.equals(student.getPassword(), NumberUtil.getMd5Str(oldPassword))) {
                    Student student1 = new Student();
                    student1.setStudentCode(userCode);
                    //加密
                    student1.setPassword(NumberUtil.getMd5Str(newPassword));
                    //密码校验通过
                    if (studentDao.update(student1) == 1) {
                        //密码重置成功
                        LoggerCommon.info("重置学生密码成功");
                        return ResultInfo.success("重置学生密码成功");
                    }
                    LoggerCommon.error("重置密码时更新数据库失败");
                    return ResultInfo.fail("重置密码时更新数据库失败");
                }
                LoggerCommon.error("密码不正确");
                return ResultInfo.fail("密码不正确");
            } else if (type == 2) {
                //type 为 2 是教师
                Teacher teacher = teacherDao.selectByCode(userCode);
                if (StringUtils.equals(teacher.getPassword(), NumberUtil.getMd5Str(oldPassword))) {
                    Teacher teacher1 = new Teacher();
                    //加密
                    teacher1.setPassword(NumberUtil.getMd5Str(newPassword));
                    teacher1.setTeacherCode(userCode);
                    //密码校验通过
                    if (teacherDao.update(teacher1) == 1) {
                        //密码重置成功
                        LoggerCommon.info("重置教师密码成功");
                        return ResultInfo.success("重置教师密码成功");
                    }
                    LoggerCommon.error("重置密码时更新数据库失败");
                    return ResultInfo.fail("重置密码时更新数据库失败");
                }
                LoggerCommon.error("旧密码不正确");
                return ResultInfo.fail("旧密码不正确");
            }
            return ResultInfo.fail("重置密码失败");
        } catch (Exception e) {
            LoggerCommon.error("修改密码时出异常");
            return ResultInfo.fail("修改密码时出异常");
        }
    }

    /**
     * 忘记密码时找回密码，使用手机验证码找回
     */
    @Override
    public ResultInfo findPassword(FindPSWDTO findPSWDTO, int type) {
        try {
            //获取参数值
            String userCode = findPSWDTO.getUserCode();
            String phone = findPSWDTO.getPhone();
            String validateCode = findPSWDTO.getValidateCode();
            String newPassword = findPSWDTO.getNewPassword();

            String redisKey = RedisConfig.SMS_CODE_FIND + phone;

            //查找Redis中key
            if (!RedissonUtil.checkKey(redisKey)) {
                //Redis中没有对应的验证码、验证码已过期
                LoggerCommon.error("redis中没有对应的验证码");
                return ResultInfo.fail("redis中没有对应的验证码，验证码过期");
            }
            //找回密码时使用验证码校验
            if (StringUtils.equals(validateCode, RedissonUtil.getStr(redisKey))) {
                //校验通过，重置密码
                if (type == 1) {
                    //学生
                    Student student = new Student();
                    student.setStudentCode(userCode);
                    student.setPassword(newPassword);

                    if (studentDao.update(student) == 1) {
                        LoggerCommon.info("重置学生密码成功");
                        return ResultInfo.success("重置学生密码成功");
                    }
                } else if (type == 2) {
                    //老师
                    Teacher teacher = new Teacher();
                    //userCode，筛选条件
                    teacher.setTeacherCode(userCode);
                    teacher.setPassword(newPassword);

                    if (teacherDao.update(teacher) == 1) {
                        LoggerCommon.info("重置教师密码成功");
                        return ResultInfo.success("重置教师密码成功");
                    }
                }
                LoggerCommon.error("重置密码失败");
                return ResultInfo.fail("重置密码失败");
            }
            LoggerCommon.error("验证码不正确");
            return ResultInfo.fail("验证码不正确");
        } catch (Exception e) {
            LoggerCommon.error("找回密码异常");
            return ResultInfo.fail("找回密码异常");
        }
    }
}
