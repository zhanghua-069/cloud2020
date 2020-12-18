package com.fleexy.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    /**
     * 配置了-个id为news_baidu_route1的路由规则,
     * 当访问地址http://localhost:9527/guoji时会自动转发到地址：https://news.baidu.com/guoji
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator baiduRoutes1(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("news_baidu_route1", route -> route
                        .path("/guoji").uri("https://news.baidu.com/guoji"))
                .build();
    }

    @Bean
    public RouteLocator baiduRoutes2(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("news_baidu_route2", route -> route
                        .path("/sports").uri("https://news.baidu.com/sports"))
                .build();
    }
}
