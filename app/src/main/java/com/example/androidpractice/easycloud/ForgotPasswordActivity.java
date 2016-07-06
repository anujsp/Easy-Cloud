package com.example.androidpractice.easycloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidpractice.easycloud.R;
import com.example.androidpractice.easycloud.data.CloudDbHelper;
import com.example.androidpractice.easycloud.data.SendEmail;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mResetEmailView;
    Random randomNumberGenerator = new Random();
    CloudDbHelper db;
    private SendEmail sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResetEmailView = (EditText) findViewById(R.id.password_reset);
        Button startBtn = (Button) findViewById(R.id.reset_button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ResetPassword();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void ResetPassword() {
        {


            UserModel user = new UserModel();

            db = new CloudDbHelper(getApplicationContext());
            user = db.getUser(mResetEmailView.getText().toString().trim());
            Log.v("user not exist", user.toString());

            if (!user.email.equals("error")) {

                String email = mResetEmailView.getText().toString().trim();
                String subject = "Easy Cloud Password Reset";

                int randomNumber = 0;
                for (int i = 1; i <= 10; i++) {
                    randomNumber = randomNumberGenerator.nextInt(100000);
                }
                sendEmail = new SendEmail(this, email, subject, String.valueOf(randomNumber));
                SendEmail sm = sendEmail;
                sm.execute();
                db.UpdateUserPassword(email, String.valueOf(randomNumber));
                Toast.makeText(ForgotPasswordActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);


            } else {
                Toast.makeText(ForgotPasswordActivity.this, "This Email id is not registered with our system", Toast.LENGTH_LONG).show();
            }

        }
    }

}
