package com.sh.carexx.mapp.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customerordertime")
public class CustomerOrderTimeController extends BaseController {

    @RequestMapping(value = "/get_by_instId/{instId}")
    public String getByInstId(@PathVariable("instId") Integer instId) {
        return this.ucServiceClient.getCustomerordertimeByInstId(instId);
    }
}
