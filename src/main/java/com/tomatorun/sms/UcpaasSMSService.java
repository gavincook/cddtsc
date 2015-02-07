package com.tomatorun.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.moon.exception.ApplicationRunTimeException;
import org.moon.utils.FileUtils;
import org.moon.utils.MD5;
import org.moon.utils.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 云之讯短信服务类
 * @author:Gavin
 * @date 2015/2/6
 */
@Component
public class UcpaasSMSService implements SMSService,DisposableBean{

    private final String softVersion = "2014-06-30";

    private final String accountId = "09b369142522054b8bebe858877810b6";

    private final String authToken = "cf14088dce4e0dd51b056b9ca58716dc";

    private final String appId = "c5e09cefe2e94314b293b611389cdacc";//"04c9c4e23e3046d08de2abd228ec5497"-->正式id

    private Base64.Encoder encoder = Base64.getEncoder();

    /**
     * 验证码模板
     */
    private String verifyCodeTemplate = "2560";

    private CloseableHttpClient httpClient;

    private String lock = "lock";

    private char[] keyStorePhrase = "123456".toCharArray();

    //发送短信的Url
    private String sendUrl = String.format("https://api.ucpaas.com/%s/Accounts/%s/Messages/templateSMS",softVersion,accountId);

    private final String cacheName = "verifyCodeCache";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void sendSms(String phoneNumber, String content) {
        log.debug("trying send  {} to {}",content,phoneNumber);
        String currentTime = getCurrentTime();
        HttpPost post = new HttpPost(sendUrl+"?sig="+getSig(currentTime));
        authRequest(post,currentTime);
        post.setEntity(formatParams("templateSMS",
                "appId", appId, "param", content, "templateId", verifyCodeTemplate, "to", phoneNumber));
        HttpResponse response = execute(post);
        checkIfSuccess(response);
        log.debug("success sent verify code {} to {}",content,phoneNumber);
    }

    /**
     * 检查请求是否成功
     * @param response
     */
    private void checkIfSuccess(HttpResponse response){
        Map<String,Object> result = getContent(response);
        if(!"000000".equals(result.get("respCode"))){
            throw new ApplicationRunTimeException("发送短信错误，错误码为"+result.get("respCode"));
        }
    }
    /**
     * 格式化参数
     * @param first 最外层的参数
     * @param args 详细参数
     * @return
     */
    private ByteArrayEntity formatParams(String first,String...args){
        StringBuilder sb = new StringBuilder("{\"").append(first).append("\":{");
        int length ;
        if(Objects.isNull(args) || (length = args.length) == 0){
            throw new ApplicationRunTimeException("传入参数非法");
        }
        for(int i = 0,l = args.length ; i < l ; i+=2) {
            if(i != 0){
                sb.append(",");
            }
            sb.append("\"").append(args[i]).append("\":\"").append(args[i+1]).append("\"");
        }
        sb.append("}}");
        try {
            return new ByteArrayEntity(sb.toString().getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e) {
            throw new ApplicationRunTimeException(e);
        }
    }

    /**
     * 授权请求
     * @param request
     */
    private void authRequest(HttpRequestBase request,String time){
        request.setHeader("Accept","application/json");
        request.setHeader("Content-Type","application/json;charset=utf-8");
        request.setHeader("Authorization",getAuthorization(time));
    }

    /**
     * 获取当前的时间，格式为yyyyMMddHHmmss
     */
    private String getCurrentTime(){
        LocalDateTime dt = LocalDateTime.now();
        return dt.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    /**
     * 获取rest-api 验证参数
     * @param time
     * @return
     */
    private String getSig(String time){
        return MD5.getMD5(accountId + authToken + time).toUpperCase();
    }

    /**
     * 获取包头验证信息
     * @param time
     * @return
     */
    private String getAuthorization(String time){
        return new String(encoder.encode((accountId+":"+time).getBytes()));
    }

    /**
     * 将结果json字符串转换为Map
     * @param response
     * @return
     */
    private Map<String,Object> getContent(HttpResponse response){
        InputStream in = null;
        Map<String, Object> result = null;
        try {
            HttpEntity httpEntity = response.getEntity();
            in = httpEntity.getContent();
            ObjectMapper mapper = new ObjectMapper();
            result = (Map<String, Object>) mapper.readValue(in, HashMap.class).get("resp");
        }catch (IOException e) {
            throw new ApplicationRunTimeException(e);
        }finally{
            try {
                in.close();
            }catch (IOException e){
                throw new ApplicationRunTimeException(e);
            }
        }

        return result;
    }

    /**
     * 获取加载了证书密钥库的HttpClient
     * @return
     */
    private HttpClient getUcpaasAuthedClient(){
        if(Objects.isNull(httpClient)){
            synchronized (lock){//单例模式
                if(Objects.isNull(httpClient)){
                    InputStream in = null;
                    try {
                        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                        in = FileUtils.loadFile("/keystore/ucpaas.keystore");//读取密钥库
                        keyStore.load(in, keyStorePhrase);//根据口令加载密钥库

                        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore).build();
                        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                        httpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();

                    }catch (Exception e){
                        throw new ApplicationRunTimeException(e);
                    }finally{
                        if(Objects.nonNull(in)){
                            try {
                                in.close();
                            } catch (IOException e) {
                                throw new ApplicationRunTimeException(e);
                            }
                        }
                    }
                }
            }
        }

        return httpClient;
    }

    /**
     * 执行请求，将IOException转化为ApplicationRunTimeException
     * @param request
     * @return
     */
    private HttpResponse execute(HttpRequestBase request){
        HttpClient client = getUcpaasAuthedClient();
        HttpResponse httpResponse = null;
        try{
            httpResponse = client.execute(request);
        }catch (IOException e){
            throw new ApplicationRunTimeException(e);
        }
        return httpResponse;
    }

    @Override
    public void destroy() throws Exception {
        if(Objects.nonNull(httpClient)){
            httpClient.close();
        }
    }

}
