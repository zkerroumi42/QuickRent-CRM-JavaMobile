package com.example.rentalapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edt_email;
    Button btn_send_email;
    TextView tv_status;

    private static final String SENDER_EMAIL = "za.kerroumi42@gmail.com";
    private static final String SENDER_PASSWORD = "ldgz phov tmiv akqy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edt_email = findViewById(R.id.edt_email);
        btn_send_email = findViewById(R.id.btn_send_email);
        tv_status = findViewById(R.id.tv_status);
        tv_status.setVisibility(View.GONE);

        btn_send_email.setOnClickListener(view -> {
            String recipientEmail = edt_email.getText().toString().trim();
            if (recipientEmail.isEmpty()) {
                tv_status.setText("Veuillez entrer votre adresse email.");
                tv_status.setVisibility(View.VISIBLE);
            } else {
                sendPasswordResetEmail(recipientEmail);
            }
        });
    }

    private void sendPasswordResetEmail(String recipientEmail) {
        String subject = "Password Reset Request";
        String message = "Hello,\n\nYou have requested to reset your password. Please click the link below to reset it:\n\n"
                + "http://example.com/reset-password?email=" + recipientEmail + "\n\nThank you.";

        AsyncTask.execute(() -> {
            try {
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                    }
                });

                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(SENDER_EMAIL));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(message);

                Transport.send(mimeMessage);

                runOnUiThread(() -> {
                    tv_status.setText("Un email de réinitialisation a été envoyé !");
                    tv_status.setVisibility(View.VISIBLE);
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    tv_status.setText("Une erreur s'est produite lors de l'envoi de l'email.");
                    tv_status.setVisibility(View.VISIBLE);
                });
                e.printStackTrace();
            }
        });
    }
}
