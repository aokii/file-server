package com.dooling.server.controller;

import com.dooling.server.service.IMinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description: FileUploadController
 *
 * @date: 2020/11/11 6:03 下午
 * @author: dooling
 */
@RequestMapping("/file")
@Controller
public class FileUploadController {
    @Autowired
    private IMinioService minioService;

    @Value("${file.bucket}")
    private String bucket;

    @RequestMapping("/index")
    @ResponseBody()
    public Object test() {
        return new Date();
    }

    @RequestMapping("/upload")
    @ResponseBody()
    public Object upload(@RequestParam("file") MultipartFile[] files) {
        List<String> paths = Arrays.stream(Optional.ofNullable(files).get()).filter(file -> !StringUtils.isEmpty(file.getOriginalFilename())).map(file -> {
            try {
                return minioService.uploadInputStream(bucket, file.getOriginalFilename(), file.getContentType(), file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return paths;
    }
}
