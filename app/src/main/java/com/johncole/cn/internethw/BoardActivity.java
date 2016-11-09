package com.johncole.cn.internethw;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BoardActivity extends AppCompatActivity {

    User user;
    static String uid,type,isActive,board;
    TextView boardTv;
    EditText boardEt;
    Button boardBtn;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x125)
            {
                boardTv.setText(board);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        boardTv=(TextView)findViewById(R.id.board_tv);
        boardEt=(EditText)findViewById(R.id.board_et);
        boardBtn=(Button)findViewById(R.id.board_btn);
        user = (User) getIntent().getSerializableExtra("User");
        uid = user.getUid();
        type = user.getType();
        isActive = user.getIsActive();
        System.out.println( uid +" "+type+" "+isActive);
        new Thread() {
            public void run() {
                board = GetPostUtil.sendPost(GetPostUtil.ipadd +
                        "select_board.jsp",null);
                handler.sendEmptyMessage(0x125);
            }

        }.start();

        boardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        if(type.equals("admin")) {
                            board = GetPostUtil.sendPost(GetPostUtil.ipadd +
                                    "new_board.jsp", "text=" + boardEt.getText().toString() +
                                    "&uid=" + uid);
                        }
                        board = GetPostUtil.sendPost(GetPostUtil.ipadd +
                                "select_board.jsp",null);
                        handler.sendEmptyMessage(0x125);
                    }
                }.start();
            }
        });

    }
}
