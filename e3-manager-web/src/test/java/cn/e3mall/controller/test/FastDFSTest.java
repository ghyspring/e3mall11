package cn.e3mall.controller.test;

import cn.e3mall.common.utils.FastDFSClient;
import org.junit.Test;

/**
 * 测试图片上传功能
 * 直接用工具类
 */
public class FastDFSTest {

    @Test
    public void test() throws Exception {
        FastDFSClient dfsClient = new FastDFSClient("C:/TOOLS/IDEAWorkSpace/e3mall/e3-manager-web/src/main/resources/conf/client.conf");
        String jpg = dfsClient.uploadFile("D:\\桌面\\t015d6559ef9dca3686.jpg", "jpg");
        System.out.println(jpg);
    }

}