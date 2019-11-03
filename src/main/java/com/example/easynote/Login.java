package com.example.easynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText e1,e2;
    Button bt;
    TextView txt;
    UserOpenHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e1=findViewById(R.id.name);
        e2=findViewById(R.id.pswd);
        bt=findViewById(R.id.login);
        txt=findViewById(R.id.sign);
        helper=new UserOpenHelper(this);


        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        String isChecked=sp.getString("isChecked","no");
        if (isChecked.equals("no")){
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("isChecked","yes");
            editor.commit();
            e1.setText(sp.getString("name",""));
            e2.setText(sp.getString("pass",""));
        }else{
            //快速登录
            Intent intent=new Intent(Login.this,Main.class);
            startActivity(intent);
            Login.this.finish();

        }

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("name",e1.getText().toString());
                editor.putString("pass",e2.getText().toString());
                editor.commit();
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
                Login.this.finish();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //链接数据库，尝试登录
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("name",e1.getText().toString());
                editor.putString("pass",e2.getText().toString());
                editor.putString("isCheck","no");
                editor.commit();
                String user_value=e1.getText().toString();
                String pswd_value=e2.getText().toString();
                if (user_value.length()==0 || pswd_value.length()==0){
                    Toast.makeText(Login.this,"请输入用户名/密码",Toast.LENGTH_SHORT).show();
                }else{
//                    try{
                        SQLiteDatabase db=helper.getWritableDatabase();
                        String sql=String.format("select _NAME,_PSWD from users where _NAME='%s' and _PSWD='%s';",user_value,pswd_value);
                        Cursor cursor=db.rawQuery(sql,null);
                        if (cursor!=null && cursor.getCount()>0){
                            Intent intent=new Intent(Login.this,Main.class);
                            Toast.makeText(Login.this,"欢迎使用EasyNote，"+user_value+"!",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            Login.this.finish();
                        }else{
                            Toast.makeText(Login.this,"用户名/密码错误",Toast.LENGTH_SHORT).show();
                        }

                }

            }
        });

    }


}
