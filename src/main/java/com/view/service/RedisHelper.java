package com.view.service;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

/**
 * redisson配置
 */
@Configuration
@Service
public class RedisHelper {
//    @Value("${spring.redis.clusters}")
    private  String cluster = "172.30.20.111:17000;172.30.20.110:19000;172.30.20.109:15000;172.30.20.109:18000;172.30.20.110:16000;172.30.20.111:20000";
//    @Value("${spring.redis.password}")
    private String password = "enorth2017";
//    @Value("${spring.log.path}")
    private  String path = "/opt/push/test.log";
//    @Value("${spring.redis.key}")
    private String redisKey  = "{shardingdefault}:setljg";
    RedissonClient redisson = null;

    @Bean
    public void getRedisson() throws  Exception{
        System.out.println("cluster:"+cluster);
        String[] nodes = cluster.split(";");
        for(int i=0;i<nodes.length;i++){
            nodes[i] = "redis://"+nodes[i];
        }
        Config config = new Config();
        config.useClusterServers() //这是用的集群server
                .setScanInterval(2000) //设置集群状态扫描时间
                .addNodeAddress(nodes).setReadMode(ReadMode.MASTER)
                .setPassword(password);
        redisson = Redisson.create(config);
//        makeDate();
        checkRedis();
    }

    private void checkRedis(){
        boolean flg = true;
        while(flg) {
            try {
                RSet<Object> set = redisson.getSet(redisKey);
                for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                    Object obj = iter.next();
//                    try {
//                        Integer intt = (Integer) obj;
//                    }catch (Exception ex){
////                        writeExcptionLog(obj+":"+ex.getMessage());
//                    }
                    System.out.println("------------------------------------------取出来的值：" + obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("异常发生，写入开始---------------------------");
                writeExcptionLog(e.getMessage());
            }
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        for (Object obj : set){
//            try {
//                System.out.println("取出来的值：" + obj);
//            }catch(Exception e){
//                e.printStackTrace();
//                System.out.println("异常发生，写入开始---------------------------");
//                writeExcptionLog(e.getMessage());
//            }
//        }
    }

    private void makeDate(){
        RSet<Object> set = redisson.getSet(redisKey);
        for(int i=0;i<180000;i++){
            set.add(i);
        }
    }
    private void writeExcptionLog(String content){
        File writefile;
        Writer out =null;
        FileOutputStream fw = null;
        try {
            // 通过这个对象来判断是否向文本文件中追加内容
            // boolean addStr = append;
            writefile = new File(this.path);
            // 如果文本文件不存在则创建它
            if (!writefile.exists()) {
                writefile.createNewFile();
                writefile = new File(path); // 重新实例化
            }
            fw = new FileOutputStream(writefile,true);
            out = new OutputStreamWriter(fw, "utf-8");
            StringBuffer sb = new StringBuffer();
            sb.append(getCurrentYYYYMMDDHHMMSS()).append(" : ");
            sb.append(content);
            out.write(sb.toString());
            String newline = System.getProperty("line.separator");
            //写入换行
            out.write(newline);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try{
                if(out !=null){
                    out.close();
                }
                if(fw != null){
                    fw.flush();
                    fw.close();
                }
            }catch (Exception e){

            }
        }
    }

    // 获取当前时间
    private  String getCurrentYYYYMMDDHHMMSS() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date currTime = new Date();
        String thisTime = new String(formatter.format(currTime));
        return thisTime;
    }




}
