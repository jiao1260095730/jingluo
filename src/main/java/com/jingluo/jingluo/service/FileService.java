package com.jingluo.jingluo.service;

import com.jingluo.jingluo.vo.ResultInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/11 15:31
 */
public interface FileService {
    ResultInfo uploadImg(MultipartFile file);
}
