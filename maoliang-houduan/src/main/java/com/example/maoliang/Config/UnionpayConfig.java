package com.example.maoliang.Config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.SDKConfig;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "unionpay")
public class UnionpayConfig {

    @PostConstruct
    public void init() {
        //PropertyConfigurator.configure("log4j.properties");
        SDKConfig.getConfig().loadPropertiesFromSrc(); //从classpath加载acp_sdk.properties文件
        CertUtil.init();

        System.out.println("=======银联SDK初始化成功=======");
    }
}
