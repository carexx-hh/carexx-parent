package com.sh.carexx.uc.dao;


import com.sh.carexx.bean.repository.RepositoryBean;
import com.sh.carexx.model.uc.Repository;

import java.util.List;

public interface RepositoryMapper {

	List<Repository> queryRepository(RepositoryBean repositoryBean);

	Repository previewRepository(Long id);

}