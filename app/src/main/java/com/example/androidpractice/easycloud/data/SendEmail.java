package com.example.androidpractice.easycloud.data;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//Refered from https://www.simplifiedcoding.net/android-email-app-using-javamail-api-in-android-studio/

/**
 * Created by Vishal on 6/6/2016.
 */
public class SendEmail extends AsyncTask<Void, Void, Void> {


    private Context context;
    private Session session;
    private String email;
    private String subject;
    private String password;

    public SendEmail(Context context, String email, String subject, String password) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.password = password;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Password Reset Email Sent", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("easycloud2016@gmail.com", "Miles2go");
                    }
                });

        try {

            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress("easycloud2016@gmail.com"));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mm.setSubject(subject);
            mm.setText("Your password is updated to "+password);
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
