package com.example.easynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPage extends AppCompatActivity {
ImageButton back;
Button add;
EditText timer,head,body;
//timer YYYY-MM-DD HH:MM:SS
Spinner style;
UserOpenHelper helper;
String[] style_option={"普通","紧急","待办"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        back=findViewById(R.id.back);
        add=findViewById(R.id.add);
        timer=findViewById(R.id.timer);
        head=findViewById(R.id.head);
        body=findViewById(R.id.body);
        style=findViewById(R.id.style);
        helper=new UserOpenHelper(this);
        final ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, style_option);
        style.setAdapter(styleAdapter);
        final int style_value=0;
        style.setSelection(style_value,true);
        Date now=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timer_value=dateFormat.format(now);
        timer.setText(timer_value);
        final SharedPreferences sp= getSharedPreferences("data", Context.MODE_PRIVATE);
        //退回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddPage.this,Main.class);
                startActivity(intent);
                AddPage.this.finish();
            }
        });
        //添加
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String head_value=head.getText().toString();
                String timer_value=timer.getText().toString();
                int style_value;
                String choice=style.getSelectedItem().toString();
                String name=sp.getString("name","");
                if(name==""){
                    Toast.makeText(AddPage.this,"获取用户名异常",Toast.LENGTH_SHORT).show();
                }else{
                    switch (choice){
                        case "紧急":style_value=1;break;
                        case "待办":style_value=2;break;
                        default:style_value=0;
                    }
                    String body_value=body.getText().toString();
                    final SQLiteDatabase db=helper.getWritableDatabase();
                    String sql=String.format("insert into res(_NAME,_DATE,_HEAD,_MSG,_TYPE) values('%s','%s','%s','%s',%d);",name,timer_value,head_value,body_value,style_value);
                    try{
                        db.execSQL(sql);
                        Toast.makeText(AddPage.this,"添加成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddPage.this,Main.class);
                        startActivity(intent);
                        AddPage.this.finish();
                    }catch (Exception e){
                        Toast.makeText(AddPage.this,"添加失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
