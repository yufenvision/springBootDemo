import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lawrence
 * @date 2021-11-03 11:09
 */
public class MongoClientTest {

    private static Session session;
    private static MongoTemplate mongoTemplate;

    @Test
    public void testAddTcReportData2UatMongo(){
        //test
//        List<ArsTcReportData> arsTcReportDataAll = mongoTemplate.find(new Query(), ArsTcReportData.class);
//        System.out.println(JacksonUtil.serialize(arsTcReportDataAll));
    }

    /**
     * 通过ssh通道获取 mongoClient
     * @throws JSchException 异常
     */
    @BeforeClass
    public static void setUp(){
        String sshLocalhost = "10.122.11.60";
        int sshPort = 22;
        String sshUserName = "username";
        String sshPassword = "abcd-1234";
        int localPort = 8988;
        String localhost = "127.0.0.1";
        String remoteHost = "10.62.108.1";
        int remotePort = 27017;
        String userName = "esupport";
        String password = "esupport1234";
        MongoClient mongoClient = null;
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(sshUserName, sshLocalhost, sshPort);
            session.setPassword(sshPassword);
            session.setConfig(config);
            session.connect();
            session.setPortForwardingL(localPort, remoteHost, remotePort);
            mongoClient = getMongoClient(localhost + ":" + localPort, "", userName, password);
            assert mongoClient != null;
            mongoClient.setReadPreference(ReadPreference.nearest());
            // 简单测试
            for (String s : mongoClient.listDatabaseNames()) {
                System.out.println(s);
            }

            mongoTemplate = new MongoTemplate(mongoClient, "Commercial");

        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void closeResource(){
        // 关闭 SSH 连接
        if (session != null) {
            session.disconnect();
        }
    }

    /**
     * 获取mongoClient
     * @param masterUrl 主url
     * @param slaveUrl 从url
     * @param userName 用户名
     * @param password 密码
     * @return mongoClient
     */
    private static MongoClient getMongoClient(String masterUrl, String slaveUrl, String userName, String password) {
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        // 与目标数据库能够建立的最大connection数量为50
        build.connectionsPerHost(50);
        // 自动重连数据库启动
        build.socketKeepAlive(true);
        // 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
        build.threadsAllowedToBlockForConnectionMultiplier(50);
        /*
         * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
         */
        build.maxWaitTime(1000 * 60 * 2);
        // 与数据库建立连接的timeout设置为1分钟
        build.connectTimeout(1000 * 60);
        MongoClientOptions myOptions = build.build();
        List<MongoCredential> lstCredentials = new ArrayList<MongoCredential>();
        if (StringUtils.isNoneBlank(userName, password)) {
            final MongoCredential credential = MongoCredential.createCredential(userName, "Commercial", password.toCharArray());
            lstCredentials = Collections.singletonList(credential);
        }
        try {
            String url = slaveUrl;
            if (StringUtils.isBlank(url)) {
                url = masterUrl;
            }
            if (StringUtils.isBlank(url)) {
                throw new RuntimeException("请配置数据库连接");
            }
            // 数据库连接实例
            return new MongoClient(new ServerAddress(url), lstCredentials, myOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

