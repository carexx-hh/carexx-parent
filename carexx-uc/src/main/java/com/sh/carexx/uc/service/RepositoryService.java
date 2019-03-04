package com.sh.carexx.uc.service;

import com.sh.carexx.bean.repository.RepositoryBean;

import java.util.List;
import java.util.Map;

public interface RepositoryService {

    List<Map<?, ?>> queryRepository(RepositoryBean repositoryBean);

    String previewRepository(Long id);

    Integer queryRepositoryCount(RepositoryBean repositoryBean);

}
