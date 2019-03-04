package com.sh.carexx.uc.dao;


import com.sh.carexx.bean.repository.RepositoryBean;
import com.sh.carexx.model.uc.Repository;

import java.util.List;
import java.util.Map;

public interface RepositoryMapper {

	List<Map<?, ?>> queryRepository(RepositoryBean repositoryBean);

	Repository previewRepository(Long id);

	Integer queryRepositoryCount(RepositoryBean repositoryBean);

}