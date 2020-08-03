package com.leyou.upload.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {
    private String diskpath;
    private String mappingpath;
    private String path;
    private  String url;
    private String baseurl;
    private List<String> allowTypes;
}
