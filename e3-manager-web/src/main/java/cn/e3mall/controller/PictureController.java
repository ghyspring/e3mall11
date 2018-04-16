package cn.e3mall.controller;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


/**
 * 图片上传
 * @Author:Pineapple
 * @Date: 2018/4/10 21:15
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @ResponseBody
    @RequestMapping("/pic/upload")
    public String pictureUpload(MultipartFile uploadFile){
        try {
            //获取上传文件原始信息
            String originalFilename = uploadFile.getOriginalFilename();
            //获取文件扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //创建一个FashDFS客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            //执行上传
            String result = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            System.out.println(result);
            //返回结果
            Map map = new HashMap();
            map.put("error",0);
            System.out.println(IMAGE_SERVER_URL);
            map.put("url",IMAGE_SERVER_URL+result);
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            Map map = new HashMap();
            map.put("error",1);
            map.put("message","上传图片失败！");
            return JsonUtils.objectToJson(map);
        }

    }
}
