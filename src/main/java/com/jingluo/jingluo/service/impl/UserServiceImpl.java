package com.jingluo.jingluo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.config.RedisConfig;
import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.dto.userdto.*;
import com.jingluo.jingluo.mapper.StudentMapper;
import com.jingluo.jingluo.mapper.TeacherMapper;
import com.jingluo.jingluo.entity.Student;
import com.jingluo.jingluo.entity.Teacher;
import com.jingluo.jingluo.service.UserService;
import com.jingluo.jingluo.utils.*;
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
     * @param code     用于存储 userToken 的 key 和值的后缀 统一为userCode
     * @param tokenStr 存储token 的 key 的前缀，
     *                 老师：userToken:teacher:
     *                 学生：userToken:student:
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

    /**
     * 根据token中的userCode查询用户信息
     */
    @Override
    public ResultInfo selectOne(String userToken, int type) {
        if (StringUtil.isEmpty(userToken) || userToken.length() <= 18) {
            LoggerCommon.error("token字符串异常，userToken：" + userToken);
            return ResultInfo.fail("token字符串异常，userToken：" + userToken);
        }
        String userCode = TokenUtil.getUserCodeFormToken(userToken);
        if (type == 1) {
            Student student = studentDao.selectByCode(userCode);
            LoggerCommon.info("获取的学生信息：" + student);
            return ResultInfo.success("获取成功", student);
        }
        if (type == 2) {
            Teacher teacher = teacherDao.selectByCode(userCode);
            LoggerCommon.info("获取的教师信息：" + teacher);
            return ResultInfo.success("获取成功", teacher);
        }
        return ResultInfo.fail("获取失败");
    }

    @Override
    public ResultInfo updateMsg(UserUpdateMsgDto dto, int type) {

        String name = dto.getName();
        String nickName = dto.getNickName();
        String headImg = dto.getHeadImg();
        String token = dto.getUserToken();
        String userCode = TokenUtil.getUserCodeFormToken(token);

        //校验token是否有效
        if (TokenUtil.tokenValidate(token, userCode)) {
            LoggerCommon.error("登录已失效，请重新登陆");
            return ResultInfo.fail("登录已失效，请重新登陆");
        }
        if (type == 1) {
            Student student = new Student();
            student.setStudentCode(userCode);
            student.setHeadImg(headImg);
            student.setName(name);
            student.setNickName(nickName);

            studentDao.update(student);
            LoggerCommon.info("修改学生信息成功");
            return ResultInfo.success("修改学生信息成功");
        }
        if (type == 2) {
            Teacher teacher = new Teacher();
            teacher.setTeacherCode(userCode);
            teacher.setName(name);
            teacher.setNickName(nickName);
            teacher.setHeadImg(headImg);

            teacherDao.update(teacher);
            LoggerCommon.info("修改教师信息成功");
            return ResultInfo.success("修改教师信息成功");
        }
        LoggerCommon.error("修改资料失败");
        return ResultInfo.fail("修改资料失败");
    }

    /**
     * 校验验证码，绑定手机号
     */
    @Override
    public ResultInfo bindPhone(UserValidDto userDto, int type) {
        try {
            String phone = userDto.getPhone();
            String validCode = userDto.getValidCode();
            String userToken = userDto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            if (TokenUtil.tokenValidate(userToken, userCode)) {
                return ResultInfo.fail("登录已失效，请重新登陆");
            }
            //校验是否该手机号是否已绑定
            Student stu = studentDao.selectBindPhone(phone);
            Teacher tea = teacherDao.selectBindPhone(phone);
            if (stu != null || tea != null) {
                //该手机号已经绑定
                LoggerCommon.error("该手机号已经绑定");
                return ResultInfo.fail("该手机号已经绑定");
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
            String oldPassword = userDto.getOldPassword();
            String newPassword = userDto.getNewPassword();
            //获取token值和存储的key
            String userToken = userDto.getUserToken();
            String userCode = TokenUtil.getUserCodeFormToken(userToken);
            if (TokenUtil.tokenValidate(userToken, userCode)) {
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
            String validateCode = findPSWDTO.getValidCode();
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
            Student student1 = studentDao.selectBindPhone(phone);
            Teacher teacher1 = teacherDao.selectBindPhone(phone);
            if (student1 == null && teacher1 == null) {
                LoggerCommon.error("手机号" + phone + "未绑定");
                return ResultInfo.fail("手机号：" + phone + "未绑定");
            }
            String studentCode;
            String teacherCode;
            try {
                studentCode = student1.getStudentCode();
                teacherCode = teacher1.getTeacherCode();
            } catch (Exception e) {
                LoggerCommon.error("根据手机号查询的userCode无值");
                return ResultInfo.fail("手机号和userCode不匹配");
            }
            if (!StringUtils.equals(userCode, studentCode)
                    && !StringUtils.equals(userCode, teacherCode)) {
                LoggerCommon.error("根据手机号查出的userCode和传入的userCode不匹配");
                return ResultInfo.fail("根据手机号查出的userCode和传入的userCode不匹配");
            }
            //校验通过，重置密码
            if (type == 1) {
                //学生
                Student student = new Student();
                student.setStudentCode(userCode);
                student.setPassword(NumberUtil.getMd5Str(newPassword));

                if (studentDao.update(student) == 1) {
                    LoggerCommon.info("重置学生密码成功");
                    return ResultInfo.success("重置学生密码成功");
                }
            } else if (type == 2) {
                //老师
                Teacher teacher = new Teacher();
                //userCode，筛选条件
                teacher.setTeacherCode(userCode);
                teacher.setPassword(NumberUtil.getMd5Str(newPassword));

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
    public ResultInfo logOut(String userToken, int type) {
        String userCode = TokenUtil.getUserCodeFormToken(userToken);
        String stuTokenKey = stuTokenPre + userCode;
        String teaTokenKey = teaTokenPre + userCode;
        if (TokenUtil.tokenValidate(userToken, userCode)) {
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
}
