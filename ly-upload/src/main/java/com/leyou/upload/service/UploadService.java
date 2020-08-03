package com.leyou.upload.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    @Autowired
    private UploadProperties prop;

    public String uploadImage(MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (!prop.getAllowTypes().contains(contentType)) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            BufferedImage imgae = ImageIO.read(file.getInputStream());

            if (imgae == null) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String originname = System.currentTimeMillis() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            File path = new File(prop.getPath() + sdf.format(d));
            if (!path.exists()){
                path.mkdirs();
            }
//        准备目标路径
            File dest = new File(path, originname);
            file.transferTo(dest);
            return prop.getBaseurl() + prop.getUrl() + sdf.format(d) + "/" + originname;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传文件失败" + e);
            throw new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }


    }


}
