package com.jingluo.jingluo.service;

import com.jingluo.jingluo.dto.KnowBaseCreateDto;
import com.jingluo.jingluo.dto.KnowBaseShowDto;
import com.jingluo.jingluo.vo.ResultInfo;

/**
 * @Description 知识库service
 * @Author 焦斌
 * @Date 2020/5/2 17:21
 */
public interface KnowBaseService {

    ResultInfo createKnowBase(KnowBaseCreateDto dto);

    ResultInfo showAllKnowBase(KnowBaseShowDto dto);
}
