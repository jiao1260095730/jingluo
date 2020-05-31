package com.jingluo.jingluo.controller;

import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.service.FileService;
import com.jingluo.jingluo.vo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class FileController {

    @Autowired
    private FileService service;

    @PostMapping("api/file/uploadImg.do")
    @ApiOperation(value = "上传图片到服务器", notes = "上传图片")
    public ResultInfo uploadImg(@RequestParam MultipartFile file) {
        //SystemConfig.OSS_IMG_OBJECT_NAME   图片存储文件夹 img-1
        return service.uploadImg(file, SystemConfig.OSS_IMG_OBJECT_NAME);
    }

    @PostMapping("api/file/uploadFile.do")
    @ApiOperation(value = "上传文件到服务器", notes = "上传文件")
    public ResultInfo uploadFile(@RequestParam MultipartFile file) {
        //SystemConfig.OSS_FILE_OBJECT_NAME   图片存储文件夹 file-1
        return service.uploadImg(file ,SystemConfig.OSS_FILE_OBJECT_NAME);
    }

}
