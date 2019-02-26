package com.sh.carexx.uc.controller;

import com.sh.carexx.bean.repository.RepositoryBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.DataRetVal;
import com.sh.carexx.model.uc.Repository;
import com.sh.carexx.uc.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/repository")
public class RepositoryController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value = "/queryRepository", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String queryRepository(@RequestBody RepositoryBean repositoryBean) {
        List<Repository> repositoryList = this.repositoryService.queryRepository(repositoryBean);
        return new DataRetVal(CarexxConstant.RetCode.SUCCESS, repositoryList).toJSON();
    }

    @RequestMapping(value = "/previewRepository/{id}", method = RequestMethod.GET, consumes = MediaType.TEXT_HTML_VALUE)
    public String previewRepository(@PathVariable(value = "id") Long id) {
        String content = this.repositoryService.previewRepository(id);
        return content;
    }

}