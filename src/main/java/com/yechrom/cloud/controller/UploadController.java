package com.yechrom.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.yechrom.cloud.dto.vo.response.ResponseBaseVo;
import com.yechrom.cloud.dto.vo.response.ResponseErrorVo;
import com.yechrom.cloud.dto.vo.response.ResponseVo;
import com.yechrom.cloud.service.UserService;
import com.yechrom.cloud.util.BusinessConst;
import com.yechrom.cloud.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    UserService userService;


    @RequestMapping(value = "/{token}", method = RequestMethod.POST)
    public ResponseBaseVo upload(MultipartFile file, @PathVariable("token") String token) throws Exception {
        JSONObject    info = userService.getInfo(token);
        StringBuilder sb   = new StringBuilder();
        if (file == null || file.isEmpty()) {
            sb.append("没有选择文件");
        } else {
            long fileSize = file.getSize();
            if (fileSize >= BusinessConst.MAX_FILE_SIZE) {
                sb.append("文件体积超过上限");
            }
            String   fileOriginalName    = file.getOriginalFilename();
            String[] fileOriginalNameArr = fileOriginalName.split("\\.");
            String   filename            = fileOriginalName.substring(0, fileOriginalName.lastIndexOf("."));
            String   extension           = fileOriginalNameArr[fileOriginalNameArr.length - 1];
            String   storageFilename;
            File     storageFile;
            do {
                storageFilename = RandomUtil.getRandomFilename(extension, filename, token);
                storageFile = new File(BusinessConst.STORAGE_DIR + storageFilename);
            } while (storageFile.exists());
            file.transferTo(storageFile);
        }
        if (StringUtils.isNotBlank(sb)){
            ResponseErrorVo response = new ResponseErrorVo();
            response.setErrorcode(0);
            response.setError(sb.toString());
            return response;
        }
        ResponseVo responseVo = new ResponseVo();
        responseVo.setErrorcode(1);
        responseVo.setData(1);
        return responseVo;

    }


}
