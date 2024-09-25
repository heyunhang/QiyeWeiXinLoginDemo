package org.example.weixin.util;

import lombok.Data;

/**
 * @Author: heyh
 * @Description:
 */

@Data
public class HttpClientResult {
    private static final long serialVersionUID = 2168152194164783950L;
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;


    public HttpClientResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public HttpClientResult(int code) {
        this.code = code;
    }
}
