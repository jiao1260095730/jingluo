package com.jingluo.jingluo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.RedisConfig;
import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.dto.*;
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.mapper.TeacherMapper;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.entity.Teacher;
import com.jingluo.jingluo.service.UserService;
import com.jingluo.jingluo.utils.IdGenerator;
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

    @Autowired
    private IdGenerator idGenerator;

    private String stuTokenPre = RedisConfig.TOKEN_STUDENT_PRE;
    private String teaTokenPre = RedisConfig.TOKEN_TEACHER_PRE;

    /**
     * 登录
     */
    @Override
    public ResultInfo login(UserLoginDto userDto, int type) {
        //校验参数是否为空
        if (StringUtil.isEmpty(userDto.getUserCode(), userDto.getPassword())) {
            LoggerCommon.error("用户名密码为空，操作失败");
            return ResultInfo.fail("用户名密码为空，操作失败");
        }
        String userCode = userDto.getUserCode();
        //区分学生、教师，学生为1  教师为2
        if (type == 1) {
            Student student = studentDao.selectByCode(userCode);

            if (student == null) {
                LoggerCommon.error("没有从数据库中查询到用户信息，用户不存在");
                return ResultInfo.fail("学生信息不存在");
            }
            //MD5加密
            String md5Password = NumberUtil.getMd5Str(userDto.getPassword());
            LoggerCommon.info("学生账号：" + userCode + "的加密后密码为" + md5Password);
            //校验输入密码和教师密码是否一致
            if (StringUtils.equals(md5Password, student.getPassword())) {
                //登录成功， 提取共用方法
                return isLogin(userCode, stuTokenPre);
            }
        }
        if (type == 2) {
            Teacher teacher = teacherDao.selectByCode(userCode);
            if (teacher == null) {
                LoggerCommon.error("没有从数据库中查询到用户信息，用户不存在");
                return ResultInfo.fail("教师信息不存在");
            }
            //MD5加密
            String md5Password = NumberUtil.getMd5Str(userDto.getPassword());
            LoggerCommon.info("教师账号：" + userCode + "的加密后密码为" + md5Password);
            //校验输入密码和教师密码是否一致
            if (StringUtils.equals(md5Password, teacher.getPassword())) {
                //登录成功， 提取共用方法
                return isLogin(userCode, teaTokenPre);
            }
        }
        LoggerCommon.error("密码不正确");
        return ResultInfo.fail("密码不正确");
    }

    /**
     * 如果登录成功教师、学生共用方法
     *
     * @param code     用于存储 token 的 key 和值的后缀 统一为userCode
     * @param tokenStr 存储token 的 key 的前缀，
     *                 老师：token:teacher:
     *                 学生：token:student:
     */
    private ResultInfo isLogin(String code, String tokenStr) {
        //登录成功，设置token
        String token = idGenerator.nextId() + code;
        //将学生token存入redis中，30分钟
        RedissonUtil.setStr(tokenStr + code, token, SystemConfig.TOKEN_REDIS_TIME);
        LoggerCommon.info("登陆成功,用户userCode为 ：" + code + " 的token为: " + token);
        //将token返回到前端
        return ResultInfo.success("登录成功", token);
    }

    /**
     * 校验验证码，绑定手机号
     */
    @Override
    public ResultInfo bindPhone(UserValidDto userDto, int type) {
        try {
            String userCode = userDto.getUserCode();
            String phone = userDto.getPhone();
            String validCode = userDto.getCode();
            String token = userDto.getToken();

            //校验是否该手机号是否已绑定
            Student stu = studentDao.selectBindPhone(phone);
            Teacher tea = teacherDao.selectBindPhone(phone);
            if (stu != null || tea != null) {
                //该手机号已经绑定
                LoggerCommon.error("该手机号已经绑定，请直接登陆");
                return ResultInfo.fail("该手机号已经绑定，请直接登陆");
            }

            //校验redis中是否存在
            if (!RedissonUtil.checkKey(RedisConfig.SMS_CODE_BIND + phone)) {
                LoggerCommon.error("redis中没有验证码信息");
                return ResultInfo.fail("没有获取到验证码信息，请发送验证码");
            }

            //校验验证码是否相同
            if (!StringUtils.equals(validCode, RedissonUtil.getStr(RedisConfig.SMS_CODE_BIND + phone))) {

                LoggerCommon.error("输入的验证码校验不通过");
                return ResultInfo.fail("输入的验证码校验不通过");
            }
            //区分学生、教师，学生为1  教师为2
            if (type == 1) {
                //校验token，是否已经登录
                if ("".equals(token) || !StringUtils.equals(token, RedissonUtil.getStr(RedisConfig.TOKEN_STUDENT_PRE + userCode))) {
                    //没有token值或者登录已过期，需重新登录
                    LoggerCommon.error("登录已过期，请重新登陆");
                    return ResultInfo.fail("登录已过期，请重新登陆");
                }

                Student student = new Student();
                student.setPhone(phone);
                student.setStudentCode(userCode);
                //绑定手机号
                if (studentDao.update(student) == 1) {
                    LoggerCommon.info("更新用户手机号成功");
                    return ResultInfo.success("验证码验证通过");
                }
            }
            //区分学生、教师，学生为1  教师为2
            if (type == 2) {
                //校验token，是否已经登录
                if ("".equals(token) || !StringUtils.equals(token, RedissonUtil.getStr(RedisConfig.TOKEN_TEACHER_PRE + userCode))) {
                    //没有token值或者登录已过期，需重新登录
                    LoggerCommon.error("登录已过期，请重新登陆");
                    return ResultInfo.fail("登录已过期，请重新登陆");
                }

                Teacher teacher = new Teacher();
                teacher.setPhone(phone);
                teacher.setTeacherCode(userCode);
                //绑定手机号
                if (teacherDao.update(teacher) == 1) {
                    LoggerCommon.info("更新用户手机号成功");
                    return ResultInfo.success("验证码验证通过");
                }
            }
            return ResultInfo.fail("更改用户数据异常");
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
            //获取token值和存储的key
            String token = userDto.getToken();
            String teaTokenKey = RedisConfig.TOKEN_TEACHER_PRE + userCode;
            String stuTokenKey = RedisConfig.TOKEN_STUDENT_PRE + userCode;
            //拿出Redis中token进行比较，如果不一致或者没有，返回登录
            if (!StringUtils.equals(token, RedissonUtil.getStr(stuTokenKey))
                    && !StringUtils.equals(token, RedissonUtil.getStr(teaTokenKey))) {
                return ResultInfo.fail("登录已失效，请重新登陆");
            }

            //判断学生1、老师2
            if (type == 1) {
                //type 为 1 是学生
                Student student = studentDao.selectByCode(userCode);
                if (!StringUtils.equals(student.getPassword(), NumberUtil.getMd5Str(oldPassword))) {
                    LoggerCommon.error("密码不正确，重置密码失败");
                    return ResultInfo.fail("密码不正确，重置密码失败");
                }
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
            }
            if (type == 2) {
                //type 为 2 是教师
                Teacher teacher = teacherDao.selectByCode(userCode);
                if (!StringUtils.equals(teacher.getPassword(), NumberUtil.getMd5Str(oldPassword))) {
                    LoggerCommon.error("密码不正确，重置密码失败");
                    return ResultInfo.fail("密码不正确，重置密码失败");
                }
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
            }
            LoggerCommon.error("修改密码失败");
            return ResultInfo.fail("修改密码失败");

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
            if (!StringUtils.equals(validateCode, RedissonUtil.getStr(redisKey))) {
                LoggerCommon.error("验证码不正确，请重新输入");
                return ResultInfo.fail("验证码不正确，请重新输入");
            }
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
        } catch (Exception e) {
            LoggerCommon.error("找回密码异常");
            return ResultInfo.fail("找回密码异常");
        }
    }

    /**
     * 退出登录
     */
    @Override
    public ResultInfo logOut(TokenDto tokenDto, int type) {
        String userCode = tokenDto.getUserCode();
        String token = tokenDto.getToken();
        String stuTokenKey = RedisConfig.TOKEN_STUDENT_PRE + userCode;
        String teaTokenKey = RedisConfig.TOKEN_TEACHER_PRE + userCode;
        //拿出Redis中token进行比较，如果不一致或者没有，返回登录
        if (!StringUtils.equals(token, RedissonUtil.getStr(stuTokenKey)) && !StringUtils.equals(token, RedissonUtil.getStr(teaTokenKey))) {
            LoggerCommon.error("登录已失效，请重新登陆");
            return ResultInfo.fail("登录已失效，请重新登陆");
        }
        //区分学生、教师，学生为1  教师为2
        if (type == 1) {
            RedissonUtil.delKey(stuTokenKey);
        }
        if (type == 2) {
            RedissonUtil.delKey(teaTokenKey);
        }
        LoggerCommon.info("退出登录成功");
        return ResultInfo.success("退出登录成功");
    }

    /**
     * 使用手机号登陆
     */
    @Override
    public ResultInfo phoneLogin(UserPhoneLoginDto loginDto, int type) {
        String phone = loginDto.getPhone();
        String validCode = loginDto.getValidCode();
        String redisKey = RedisConfig.SMS_CODE_LOGIN + phone;
        String userCode;

        //校验是否该手机号是否已绑定
        Student stu = studentDao.selectBindPhone(phone);
        Teacher tea = teacherDao.selectBindPhone(phone);
        if (stu == null && tea == null) {
            //此手机号没有绑定，需要绑定手机号后进行登陆
            LoggerCommon.error("此手机号没有绑定，需要绑定手机号后进行登陆");
            return ResultInfo.fail("此手机号没有绑定，需要绑定手机号后进行登陆");
        }
        //存入redis的token，学生或者老师的userCode
        userCode = stu == null ? tea.getTeacherCode() : stu.getStudentCode();

        if (!RedissonUtil.checkKey(redisKey)) {
            LoggerCommon.error("验证码已过期");
            return ResultInfo.fail("验证码已过期");
        }

        if (!StringUtils.equals(RedissonUtil.getStr(redisKey), validCode)) {
            //验证码不正确
            LoggerCommon.error("验证码不正确");
            return ResultInfo.fail("验证码不正确");
        }
        if (type == 1) {
            //学生
            return isLogin(userCode, stuTokenPre);
        }
        if (type == 2) {
            //老师
            return isLogin(userCode, teaTokenPre);
        }
        return ResultInfo.fail("失败");
    }
}
