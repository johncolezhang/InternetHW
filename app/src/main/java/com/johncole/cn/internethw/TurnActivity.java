package com.johncole.cn.internethw;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TurnActivity extends AppCompatActivity {

    Button turnBoard,turnTopic;
    static String type;
    static String uid;
    static User user;
    static String userInfo;


    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x124)
            {
                String[]  stringU = userInfo.trim().split(" ");
                user = new User(stringU[2] ,stringU[0],stringU[1]);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn);
        turnBoard=(Button)findViewById(R.id.turn_board);
        turnTopic=(Button)findViewById(R.id.turn_topic);
        Intent i = getIntent();
        uid = i.getStringExtra("uid");
        type = i.getStringExtra("type");
        System.out.println(type);

        if (!type.equals("visitor")) {
            new Thread() {
                public void run() {
                    userInfo = GetPostUtil.sendPost(GetPostUtil.ipadd +
                            "select_user.jsp", "uid=" + uid);
                    handler.sendEmptyMessage(0x124);
                }
            }.start();
        }
        System.out.println(uid + " " + type);

        turnTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TurnActivity.this,TopicActivity.class);
                if(!type.equals("visitor")){
                    Bundle data = new Bundle();
                    data.putSerializable("User",user );
                    intent.putExtras(data);
                }else{
                    user = new User("visitor","visitor","1");
                    Bundle data = new Bundle();
                    data.putSerializable("User",user );
                    intent.putExtras(data);
                }
                startActivity(intent);
            }
        });

        turnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TurnActivity.this,BoardActivity.class);
                if(!type.equals("visitor")){
                    Bundle data = new Bundle();
                    data.putSerializable("User",user );
                    intent.putExtras(data);
                }else{
                    user = new User("visitor","visitor","1");
                    Bundle data = new Bundle();
                    data.putSerializable("User",user );
                    intent.putExtras(data);
                }
                startActivity(intent);
            }
        });
    }

}
