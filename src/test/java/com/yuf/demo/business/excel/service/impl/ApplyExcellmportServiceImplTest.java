package com.yuf.demo.business.excel.service.impl;

import com.yuf.demo.DemoApplication;
import com.yuf.demo.business.excel.mapper.ApplyExcelImportDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.Selector;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class ApplyExcellmportServiceImplTest {

    static BASE64Encoder base64Encoder = new BASE64Encoder();
    @Autowired
    ApplyExcelImportDao applyExcelImportDao;

    @Test
    public void queryList(){
        System.out.println(applyExcelImportDao.getPushList("100000").size());

    }

    @Test
    public void socket() throws IOException {
        Socket socket = new Socket();
        // 初始化远程连接地址
        SocketAddress remote = new InetSocketAddress("ybxq.horsentwise.com/static/faceImage/2020/11/30/eb4a5a68-156d-4f45-b838-ed3267d63a62.jpeg", 80);
        // 建立连接
        socket.connect(remote);
        InputStream in = socket.getInputStream();
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        // 将内容读取内存中
        int len;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            data.write(buffer, 0, len);
        }
        String base64Photo = base64Encoder.encode(data.toByteArray());
        log.info(base64Photo);
    }

}