package com.sh.carexx.uc.service.impl;

import com.sh.carexx.bean.repository.RepositoryBean;
import com.sh.carexx.common.util.Word2HtmlUtil;
import com.sh.carexx.model.uc.Repository;
import com.sh.carexx.uc.dao.RepositoryMapper;
import com.sh.carexx.uc.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Override
    public List<Map<?, ?>> queryRepository(RepositoryBean repositoryBean) {
        return this.repositoryMapper.queryRepository(repositoryBean);
    }

    @Override
    public Integer queryRepositoryCount(RepositoryBean repositoryBean) {
        return this.repositoryMapper.queryRepositoryCount(repositoryBean);
    }

    @Override
    public String previewRepository(Long id) {
        Repository repository = this.repositoryMapper.previewRepository(id);
        String content = Word2HtmlUtil.getPreviewContent("http://pntv8tnvj.bkt.clouddn.com/" + URLEncoder.encode(repository.getDiseaseName()) + ".doc");
        logger.info(content);
        return content;
    }
}
