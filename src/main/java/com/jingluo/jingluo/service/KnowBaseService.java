package com.jingluo.jingluo.service;

import com.jingluo.jingluo.dto.knowbasedto.DirDocCreateDto;
import com.jingluo.jingluo.dto.knowbasedto.DirectoryShowDto;
import com.jingluo.jingluo.dto.knowbasedto.KnowBaseCreateDto;
import com.jingluo.jingluo.dto.knowbasedto.KnowBaseShowDto;
import com.jingluo.jingluo.vo.ResultInfo;

/**
 * @Description 知识库service
 * @Author 焦斌
 * @Date 2020/5/2 17:21
 */
public interface KnowBaseService {

    ResultInfo createKnowBase(KnowBaseCreateDto dto);

    ResultInfo showAllKnowBase(KnowBaseShowDto dto);

    ResultInfo selectKnowBaseBykeys(String userToken, String keyWord);

    ResultInfo createDirectory(DirDocCreateDto dto);

    ResultInfo showAllDirectory(DirectoryShowDto dto);
}
