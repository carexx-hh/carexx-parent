package com.sh.carexx.uc.dao;

import com.sh.carexx.model.uc.entity.CareServiceRatio;

public interface CareServiceRatioMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CareServiceRatio record);

    int insertSelective(CareServiceRatio record);

    CareServiceRatio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CareServiceRatio record);

    int updateByPrimaryKey(CareServiceRatio record);
}