package com.johncole.cn.internethw;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText userEt,pwEt;
    RadioGroup rg;
    Button register;
    static String user,password,type,response;
    TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userEt=(EditText)findViewById(R.id.r_user_et);
        pwEt=(EditText)findViewById(R.id.r_pw_et);
        rg=(RadioGroup)findViewById(R.id.r_rg);
        register=(Button)findViewById(R.id.r_register);
        response = new String();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.r_user){
                    type="user";
                }else if  (checkedId == R.id.r_admin){
                    type="admin";
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userEt.getText().toString();

                password = pwEt.getText().toString();
                new Thread() {
                    public void run() {
                        if(user!="" && password!="") {
                            response = GetPostUtil.sendPost(
                                    "http://172.30.66.117:8080/hw/register_user.jsp",
                                    "uid=" + user + "&password=" + password + "&type=" + type);
                                    handler.sendEmptyMessage(0x223);
                        }

                    }
                }.start();

                }
        });
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x223)
            {
                System.out.println(response);
                if(response.trim().equals("success")){
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(i);
                }
            }

        }
    };
}
