package org.example.weixin.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.example.weixin.dto.WeiXinLoginParam;
import org.example.weixin.exceptions.BusinessException;
import org.example.weixin.util.HttpClientResult;
import org.example.weixin.util.HttpClientUtil;
import org.example.weixin.util.HttpServletUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: heyh
 * @Description:
 */
@Slf4j
@Service
public class QiYeWeiXinService {

    private static final String GET_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo";
    private static final String GET_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    /**
     * 应用id
     */
    @Value("${qiye.weixin.corp.id:123123}")
    private String corpId;

    /**
     * 企业微信管理后台corpSecret
     */
    @Value("${qiye.weixin.corp.secret:xxx}")
    private String corpSecret;

    /**
     * 登陆成功跳转首页
     */
    @Value("${qiye.weixin.index.url:http://baidu.com}")
    private String indexUrl;

    /**
     * weiXinWebAuthorize
     *
     * @param weiXinLoginParam
     * @param request
     * @param response
     * @throws Exception
     */
    public void weiXinWebAuthorize(WeiXinLoginParam weiXinLoginParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject qiyeWeiXinAccessToken = getQiyeWeiXinAccessToken();
        Integer errcode = qiyeWeiXinAccessToken.getInteger("errcode");
        if (errcode != 0) {
            throw new BusinessException("获取accessToken失败");
        }

        String accessToken = qiyeWeiXinAccessToken.getString("access_token");
        JSONObject qiyeWeiXinUserInfo = getQiyeWeiXinUserInfo(accessToken, weiXinLoginParam.getCode());
        Integer userInfoErrcode = qiyeWeiXinUserInfo.getInteger("errcode");
        if (userInfoErrcode != 0) {
            throw new BusinessException("获取用户信息失败");
        }
        String userid = qiyeWeiXinUserInfo.getString("userid");
        log.info("企业微信返回userid:{}", userid);
        HttpServletUtil.redirectToUrl(response, String.format(indexUrl));
    }

    /**
     * 企业微信网页授权时获取用户id
     *
     * @param accessToken
     * @param code
     * @return
     * @throws Exception
     */
    private JSONObject getQiyeWeiXinUserInfo(String accessToken, String code) throws Exception {
        /* 封装请求的参数*/
        Map<String, String> map = new HashMap<>(2);
        map.put("access_token", accessToken);
        map.put("code", code);
        HttpClientResult httpClientResult = HttpClientUtil.doGet(GET_USER_INFO, map);
        log.info("企业微信网页授权时获取用户信息返回，getQiyeWeiXinUserInfo:{}", httpClientResult.getContent());
        return JSONObject.parseObject(httpClientResult.getContent(), JSONObject.class);
    }

    /**
     * 企业微信获取应用AccessToken
     *
     * @return
     * @throws Exception
     */
    private JSONObject getQiyeWeiXinAccessToken() throws Exception {
        /* 封装请求的参数*/
        Map<String, String> map = new HashMap<>(2);
        map.put("corpid", corpId);
        map.put("corpsecret", corpSecret);
        HttpClientResult httpClientResult = HttpClientUtil.doGet(GET_TOKEN, map);
        log.info("企业微信获取应用AccessToken返回，getQiyeWeiXinAccessToken:{}", httpClientResult.getContent());
        return JSONObject.parseObject(httpClientResult.getContent(), JSONObject.class);
    }


}
