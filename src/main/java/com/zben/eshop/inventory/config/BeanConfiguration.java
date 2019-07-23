package com.zben.eshop.inventory.config;

import com.zben.eshop.inventory.listener.InitListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @DESC:
 * @author: jhon.zhou
 * @date: 2019/7/22 0022 17:46
 */
@Configuration
public class BeanConfiguration {

    /**
     * 注册监听器
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
         return new ServletListenerRegistrationBean(new InitListener());
    }
}
