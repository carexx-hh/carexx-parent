package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.repository.RepositoryBean;
import com.sh.carexx.common.util.Word2HtmlUtil;
import com.sh.carexx.model.uc.Repository;
import com.sh.carexx.uc.dao.RepositoryMapper;
import com.sh.carexx.uc.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Override
    public List<Repository> queryRepository(RepositoryBean repositoryBean) {
        return this.repositoryMapper.queryRepository(repositoryBean);
    }

    @Override
    public String previewRepository(Long id) {
        Repository repository = this.repositoryMapper.previewRepository(id);
        return Word2HtmlUtil.getPreviewContent("http://42.96.72.0:9596/demos/"+URLEncoder.encode(repository.getDiseaseName())+".doc");
    }
}
