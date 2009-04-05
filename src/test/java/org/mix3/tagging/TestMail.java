package org.mix3.tagging;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestMail {
	public static void main(String[] args) {
		try {
			Properties props = System.getProperties();
			// SMTPサーバーのアドレスを指定
			props.put("mail.smtp.host","smtp.nifty.com");
			Session session=Session.getDefaultInstance(props,null);
			MimeMessage mimeMessage=new MimeMessage(session);
			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(new InternetAddress("test@test.co.jp","田中　宏和","iso-2022-jp"));
			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO,"UIN50349@nifty.com");
			// メールのタイトルを指定
			mimeMessage.setSubject("Hello World JavaMail","iso-2022-jp");
			// メールの内容を指定
			mimeMessage.setText("<h1>Hello World JavaMail</h1>","iso-2022-jp");
			// メールの形式を指定
			mimeMessage.setHeader("Content-Type","text/html");
			// 送信日付を指定
			mimeMessage.setSentDate(new Date());
			// 送信します
			Transport.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
