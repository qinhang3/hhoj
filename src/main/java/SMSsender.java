import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.http.HttpStatus;


public class SMSsender {
	/**
     * 新增一个通道 by:世伦
     * 短信模板"subject":主题;"content":内容
     *
     * @param empNos
     * @param content
     * @throws Exception
     */
	/*
    public void sendSmsMessageNew(final String content, List<String> empNos) throws Exception {
        sendMessageNew(content, empNos, Channel.SMS);
    }

    public void sendMessageNew(final String content, List<String> empNos, final String channel) throws Exception {
        for (String empNo : empNos) {
            sendMessageNew(content, empNo, channel);
        }
    }
    public Integer sendMessageNew(final String content, String empNo, final String channel) throws Exception {
        return sendMessageNew(content, empNo, channel, "jinxi.wx", "j3U2.JLk5d");
    }
    public Integer sendMessageNew(final String content, String empNo, final String channel, String username, String password) throws IOException, NoSuchAlgorithmException {

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://mon.alibaba-inc.com/noticenter/send-by-criteria.do");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
        postMethod.setParameter("username", username);

        postMethod.setParameter("password", md5Encode(password+empNo));
        postMethod.setParameter("method", channel);
        postMethod.setParameter("sender", "tnt");
        postMethod.setParameter("empId", empNo);
        postMethod.setParameter("message", content);

        int statusCode = httpClient.executeMethod(postMethod);
        if (statusCode == HttpStatus.SC_OK) {
        } else {
            //log.error("发送短信异常, empNo=" + empNo);
        }
        postMethod.releaseConnection();
        return statusCode;
    }
    */
    private String md5Encode(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String charset = "GBK";
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(password.getBytes(charset));
        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString().toLowerCase();
    }
    
    public static class Channel {
        public static final String SMS = "sms";//短信
        public static final String EMAIL = "mail";//邮件
        public static final String WANGWANG_ALERT = "wang-alert";//旺旺弹窗
        public static final String WANGWANG_DELOG = "wang-dialog";//往往对话框
        public static final String ALI_IVR = "ali-ivr";//语音电话

    }
}
