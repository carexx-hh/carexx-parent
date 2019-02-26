package com.sh.carexx.uc.service;

import com.sh.carexx.bean.repository.RepositoryBean;
import com.sh.carexx.model.uc.Repository;

import java.util.List;

public interface RepositoryService {

    List<Repository> queryRepository(RepositoryBean repositoryBean);

    String previewRepository(Long id);

}
