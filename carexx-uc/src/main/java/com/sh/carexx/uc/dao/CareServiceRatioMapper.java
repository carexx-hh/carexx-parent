package com.sh.carexx.uc.dao;

import com.sh.carexx.model.uc.CareServiceRatio;

public interface CareServiceRatioMapper {

    CareServiceRatio selectById(Integer id);
    
    int insert(CareServiceRatio careServiceRatio);

}