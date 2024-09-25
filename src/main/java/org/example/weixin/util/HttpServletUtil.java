package org.example.weixin.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * @Author: heyh
 * @Description: httpServlet工具类
 */
public class HttpServletUtil {

    public static void redirectToUrl(HttpServletResponse response,String url){
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", url);
    }
}
