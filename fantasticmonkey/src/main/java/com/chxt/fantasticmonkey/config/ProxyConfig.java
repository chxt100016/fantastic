package com.chxt.fantasticmonkey.config;

import com.google.common.collect.ImmutableMap;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.Map;

@Configuration
public class ProxyConfig{
    @Bean
    public Servlet createProxyServlet() {
        // 创建新的ProxyServlet
        return new ProxyServlet();
    }

    @Bean
    public ServletRegistrationBean proxyServletRegistration2() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(createProxyServlet(), "/video/*");
        //设置网址以及参数
        Map<String, String> params = ImmutableMap.of(
                "targetUri", "http://2202.com:9999/video",
                "log", "true");
        registrationBean.setInitParameters(params);
        return registrationBean;
    }
}
