package com.johncole.cn.internethw;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends AppCompatActivity {

    User user;
    static String uid,type,isActive,topic;
    static String[] topics,to;
    static List<String> lt = new ArrayList<String>();
    ListView topicLv;
    EditText topicEt;
    Button topicBtn;
    ArrayAdapter<String>adapter;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x325)
            {
                System.out.println(topic);
                topics = topic.split(" ");
                for(String t:topics){
                    lt.add(t);
                }
                System.out.println(topics.length);
                adapter.notifyDataSetChanged();
                topicLv.setAdapter(adapter);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        topicLv=(ListView)findViewById(R.id.topic_lv);
        topicBtn=(Button)findViewById(R.id.topic_btn);
        topicEt=(EditText)findViewById(R.id.topic_et);


        user = (User) getIntent().getSerializableExtra("User");
        uid = user.getUid();
        type = user.getType();
        isActive = user.getIsActive();
        topics=new String[]{};
        adapter= new ArrayAdapter<String>(this,
                R.layout.array_item,lt);
        System.out.println(uid + " " + type + " " + isActive);
        new Thread() {
            public void run() {
                lt.clear();
                topic = GetPostUtil.sendPost(GetPostUtil.ipadd +
                        "select_topic.jsp",null);
                handler.sendEmptyMessage(0x325);
            }

        }.start();

        topicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new Thread(){
                     public void run(){
                        if(!type.equals("visitor")) {
                            lt.clear();
                            topic = GetPostUtil.sendPost(GetPostUtil.ipadd +
                                "new_topic.jsp", "uid=" + uid +
                                "&text=" + topicEt.getText().toString());
                            topic = GetPostUtil.sendPost(GetPostUtil.ipadd +
                                    "select_topic.jsp",null);
                            handler.sendEmptyMessage(0x325);
                    }
                }
            }.start();

            }

        });





    }
}
