package com.silver.app.kalanama;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.silver.app.kalanama.utility.ServiceTask;

import org.apache.http.message.BasicNameValuePair;

public class RegisterConfirmedActivity extends AppCompatActivity {

    EditText phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirmed);
        final String userID,confirmedCode;
        phoneNumber = (EditText) findViewById(R.id.RegisterConfirmedCode);


        Bundle extra = getIntent().getExtras();
        if (extra != null) {
             userID = extra.getString("UserID");
             confirmedCode = extra.getString("ConfirmedCode");
        } else {
            finish();
            return;
        }

        phoneNumber.setText(confirmedCode);

        //wait for sms
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                //below lines is temporary. in fact we wait for sms
                ServiceTask service = new ServiceTask(new ServiceTask.ServiceCallBack() {
                    @Override
                    public void getResult(String result, Object data) {
                        String s = result;

                        Intent intent = new Intent(RegisterConfirmedActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                service.execute(
                        new BasicNameValuePair("address", "User/ConfirmedRegister"),
                        new BasicNameValuePair("userID", userID),
                        new BasicNameValuePair("confirmedCode", confirmedCode));

            }
        }, 5000);
    }
}
