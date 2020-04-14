package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.service.FileService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/11 14:02
 */
@RestController
@Api(value = "文件上传各种方法", tags = "文件上传")
public class FileController {

    @Autowired
    private FileService service;

    @PostMapping("api/file/uploadImg.do")
    @ApiOperation(value = "上传图片到服务器", notes = "上传图片")
    public ResultInfo uploadImg(@RequestParam MultipartFile file) {
        return service.uploadImg(file);
    }
}
