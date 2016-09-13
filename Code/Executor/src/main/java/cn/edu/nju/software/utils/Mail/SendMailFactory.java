package cn.edu.nju.software.utils.Mail;

import cn.edu.nju.software.config.MsgInfo;

import java.util.Date;

/**
 * Created by Jiayiwu on 16/7/29.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class SendMailFactory {

    private SendMailFactory(){

    }

    public static MsgInfo sendMail(String receiver,String userName,String UrlNum){
        try{
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost("smtp.163.com");
            mailInfo.setMailServerPort("25");
            mailInfo.setValidate(true);
            // 邮箱用户名
            mailInfo.setUserName("m13276631257@163.com");
            // 邮箱密码
            mailInfo.setPassword("qexecutor123");
            // 发件人邮箱
            mailInfo.setFromAddress("m13276631257@163.com");
            // 收件人邮箱
            mailInfo.setToAddress(receiver);
            // 邮件标题
            mailInfo.setSubject("Q_executor用户找回密码");

            StringBuffer buffer = new StringBuffer();
            buffer.append("亲爱的用户：\n");
            buffer.append("您好！感谢您使用Q_executor服务，您正在进行邮箱密码找回验证，请点击下方链接进行密码修改\n");
            String tem = "http://www.fivedreamer.com/page/findpw.html?userName="+userName+"&num="+UrlNum;
            buffer.append(tem+"\n");
            buffer.append("若无法点击链接,请复制链接地址到浏览器中进行打开"+"\n");
            buffer.append("Q_executor帐号团队"+"\n");
            buffer.append(new Date().toLocaleString());
            mailInfo.setContent(buffer.toString());
            // 发送邮件
            SimpleMailSender sms = new SimpleMailSender();
            // 发送文体格式
            sms.sendTextMail(mailInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new MsgInfo(false,"邮件发送失败");
        }

        return new MsgInfo(true,"邮件发送成功");
    }

}