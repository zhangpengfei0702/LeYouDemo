package com.leyou.gatewy;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class LyGatewy {
    public static void main(String[] args) {
        SpringApplication.run(LyGatewy.class);
    }
}
