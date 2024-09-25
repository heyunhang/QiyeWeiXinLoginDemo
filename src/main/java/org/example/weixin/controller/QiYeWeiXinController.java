package org.example.weixin.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.weixin.dto.WeiXinLoginParam;
import org.example.weixin.service.QiYeWeiXinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: heyh
 * @Description:
 */
@Slf4j
@Controller
public class QiYeWeiXinController {

    @Resource
    private QiYeWeiXinService qiYeWeiXinService;


    /**
     * @Author: heyh
     * @Description: 获取网页授权
     * @param:
     */
    @GetMapping(value = "/getWeiXinWebAuthorize")
    @ResponseBody
    public void getWeiXinWebAuthorize(WeiXinLoginParam weiXinLoginParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
        qiYeWeiXinService.weiXinWebAuthorize(weiXinLoginParam, request, response);
    }


}