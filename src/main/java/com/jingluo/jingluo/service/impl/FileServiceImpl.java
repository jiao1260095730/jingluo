package com.jingluo.jingluo.service.impl;

import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.dto.commondto.OssBeanDto;
import com.jingluo.jingluo.exception.MyException;
import com.jingluo.jingluo.service.FileService;
import com.jingluo.jingluo.utils.IdGenerator;
import com.jingluo.jingluo.utils.OSSClientUtil;
import com.jingluo.jingluo.vo.ResultInfo;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/11 15:32
 */
@Component
public class FileServiceImpl implements FileService {

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public ResultInfo uploadImg(MultipartFile file, String typeName) {
        //1.验证是否为空
        if (!file.isEmpty()) {
            //2.获取上传文件名
            String fileName = file.getOriginalFilename();
            //3.重命名
            if (fileName.length() > 20) {
                fileName = fileName.substring(fileName.length() - 20);
            }
            //2.重命名  微服务唯一  雪花算法
            fileName = idGenerator.nextId() + "" + fileName;
            //4.上传到阿里云的oss
            String url = null;
            try {
                //调用上传方法，上传资源到服务器
                url = OSSClientUtil.upload(fileName, file.getBytes(), typeName);
            } catch (IOException e) {
                LoggerCommon.error("文件操作异常");
                return ResultInfo.fail(" IOException ");
            } catch (MyException e) {
                return ResultInfo.fail(e.getMessage(), e.getCode());
            }
            if (!StringUtil.isEmpty(url)) {
                OssBeanDto beanDto = new OssBeanDto();
                beanDto.setObjName(fileName);
                beanDto.setUrl(url);
                return ResultInfo.success(beanDto);
            }
        }
        return ResultInfo.fail("参数不合法");
    }
}
