package com.brainor.textforward.textautoforward;

/**
 * Created by 欧伟科 on 2017/7/19.
 */
import android.os.AsyncTask;
import java.util.Properties;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
class SendMail extends AsyncTask<String, Void, String> {
    private void sendMail(String... 短信内容) {
        Properties props = new Properties();
        String mailServer = "smtp.qq.com";
        String userMail = "brainor@qq.com";
        String password = "neqkaihvgiwkcadh";
        props.put("mail.smtp.host", mailServer);
        props.put("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(userMail);
            msg.setRecipients(Message.RecipientType.TO,
                    userMail);
            msg.setSubject("手机短信");
            msg.setSentDate(new Date());

            msg.setText(短信内容[0]);
            Transport.send(msg, userMail, password);
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }
    }
    @Override
    protected String doInBackground(String... 短信内容) {
        sendMail(短信内容);
        return "success";
    }
}