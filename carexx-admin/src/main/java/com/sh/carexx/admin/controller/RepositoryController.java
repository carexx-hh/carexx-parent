package com.sh.carexx.admin.controller;

import com.sh.carexx.bean.repository.RepositoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/repository")
public class RepositoryController extends BaseController {

    @RequestMapping(value = "/queryRepository")
    public String queryRepository(RepositoryBean repositoryBean) {
        return this.ucServiceClient.queryRepository(repositoryBean);
    }

    @RequestMapping(value = "/previewRepository/{id}")
    public String previewRepository(@PathVariable(value = "id") Long id) {
        return this.ucServiceClient.previewRepository(id);
    }
}