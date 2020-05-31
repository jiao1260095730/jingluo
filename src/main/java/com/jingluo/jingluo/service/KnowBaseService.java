package com.jingluo.jingluo.service;

import com.jingluo.jingluo.dto.knowbasedto.*;
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

    ResultInfo createDirAndDoc(DirDocCreateDto dto);

    ResultInfo showAllDirAndDoc(DirectoryShowDto dto);

    ResultInfo delOneDoc(DocDeleteDto dto);

    ResultInfo delOneKnowBase(KnowBaseDelDto dto);

    ResultInfo showAllKBInDel(KnowBaseShowDto dto);

    ResultInfo delOneKnowBaseReally(KnowBaseDelDto dto);

    ResultInfo selectOneBaseMsg(String userToken, Integer baseId);
}
