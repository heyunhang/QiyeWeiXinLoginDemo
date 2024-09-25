package org.example.weixin.dto;

import lombok.Data;
import org.springframework.stereotype.Component;


/**
 * @Author: heyh
 * @Description:    获取网页授权时，需要的code和state
 */
@Component
@Data
public class WeiXinLoginParam {

    private String code;

    private String state;
}
