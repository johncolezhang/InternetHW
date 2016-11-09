package com.johncole.cn.internethw;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText userEt,pwEt;
    RadioGroup rg;
    Button login,register,clear;
    static String user,password,type,response;
    TextView resultTv;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x123)
            {
                resultTv.setText(response);
                if (response.trim().equals("success"))
                {
                response="fail";
                Intent i = new Intent(LoginActivity.this, TurnActivity.class);
                i.putExtra("uid", user);
                i.putExtra("type", type);
                startActivity(i);
                 }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        userEt=(EditText)findViewById(R.id.user_et);
        pwEt=(EditText)findViewById(R.id.pw_et);
        rg=(RadioGroup)findViewById(R.id.rg);
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        clear=(Button)findViewById(R.id.clear);
        resultTv=(TextView)findViewById(R.id.result_tv);
        response="wait";

        type = "visitor";


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.visitor){
                    type="visitor";
                }else if(checkedId==R.id.admin){
                    type="admin";
                }else if(checkedId==R.id.user){
                    type="user";
                }
                System.out.println(type);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userEt.getText().toString();
                password = pwEt.getText().toString();
                new Thread() {
                    public void run() {
                        Log.d("a", type);
                        response = GetPostUtil.sendPost(
                                GetPostUtil.ipadd+"login_user.jsp",
                                "uid=" + user + "&password=" + password + "&type=" + type);
                        handler.sendEmptyMessage(0x123);
                    }
                }.start();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEt.setText("");
                pwEt.setText("");
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });


    }


}
