package com.silver.app.kalanama;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.silver.app.kalanama.Enum.EnumGeneral;
import com.silver.app.kalanama.services.JSONParser;
import com.silver.app.kalanama.utility.ActivityBase;
import com.silver.app.kalanama.utility.ServiceTask;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;


public class RegisterActivity extends ActivityBase {

    private LinearLayout LinearLayoutTop, LinearLayoutDown;

    public RegisterActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText profileName = (EditText) findViewById(R.id.RegisterProfileName);
        final EditText phoneNumber = (EditText) findViewById(R.id.RegisterMobileNumber);
        Button register = (Button) findViewById(R.id.RegisterButton);
        final TextView status = (TextView) findViewById(R.id.RegisterStatus);
        final String androidId = getAndroidID();
        LinearLayoutTop = (LinearLayout) findViewById(R.id.RegisterLinearLayoutTop);
        LinearLayoutDown = (LinearLayout) findViewById(R.id.RegisterLinearLayoutDown);

        setSize();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ServiceTask service = new ServiceTask(new ServiceTask.ServiceCallBack() {
                    @Override
                    public void getResult(String result, Object data) {
                        String[] res;
                        String userID = "0", validate = EnumGeneral.EnumRegisterUser.UnKhnown.toString(), ConfirmedCode = "0";
                        try {
                            JSONParser parse = new JSONParser();
                            res = parse.parseRegister(result);
                            userID = res[0];
                            validate = res[1];
                            ConfirmedCode = res[2];
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        switch (validate) {
                            case "DuplicateMac":
                                status.setText("این دستگاه قبلا در سیستم یه ثبت رسیده است");
                                break;
                            case "DuplicatePhoneNumber":
                                status.setText("این شماره موبایل قبلا در سیستم به ثبت رسیده است");
                                break;
                            case "DuplicateProfileName":
                                status.setText("این نام کاربری  قبلا در سیستم یه ثبت رسیده است");
                                break;
                            case "UnKhnown":
                                status.setText("بابا این اینترنتت رو درست کن");
                                break;
                            case "Waiting":
                                Intent intent = new Intent(RegisterActivity.this,RegisterConfirmedActivity.class);
                                intent.putExtra("UserID", userID);
                                intent.putExtra("ConfirmedCode", ConfirmedCode);
                                startActivity(intent);
                                //finish();
                                break;
                        }
                    }
                });
                service.execute(
                        new BasicNameValuePair("address", "User/Register"),
                        new BasicNameValuePair("profileName", profileName.getText().toString()),
                        new BasicNameValuePair("phoneNumber", phoneNumber.getText().toString()),
                        new BasicNameValuePair("DefaultMAC", androidId)
                );
            }
        });
    }

    private String getDeviceID() {
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
    /*    tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();*/

        return tmDevice;
    }

    private String getAndroidID() {
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        return "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    private void setSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int hLayout = dm.heightPixels;
        int wLayout = dm.widthPixels;

        hLayout = hLayout / 2;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                wLayout, hLayout);
        LinearLayoutTop.setLayoutParams(params);
        LinearLayoutDown.setLayoutParams(params);

        LinearLayoutDown.setTextDirection(View.TEXT_DIRECTION_LTR);
    }
}
