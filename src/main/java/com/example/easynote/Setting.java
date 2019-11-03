package com.example.easynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Setting extends AppCompatActivity {
EditText name,pswd,sign;
Spinner gender;
Button update,quit;
String name_value,pswd_value,sign_value;
int gender_value;
ImageButton back;
UserOpenHelper helper;
String[] gender_option={"保密","男","女"};

ArrayList userList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        name=findViewById(R.id.name);
        pswd=findViewById(R.id.pswd);
        sign=findViewById(R.id.sign);
        gender=findViewById(R.id.gender);
        update=findViewById(R.id.update);
        quit=findViewById(R.id.quit);
        back=findViewById(R.id.back);
        SharedPreferences sp= getSharedPreferences("data", Context.MODE_PRIVATE);
        name_value=sp.getString("name","");
        pswd_value=sp.getString("pass","");
        name.setText(name_value);
        pswd.setText(pswd_value);


        helper=new UserOpenHelper(this);
        final SQLiteDatabase db=helper.getWritableDatabase();

        userList.clear();
        String sql=String.format("select _GENDER,_SIGN from msg where _NAME='%s'",name_value);
        Cursor cursor=db.rawQuery(sql,null);
        if (cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                userList.add(cursor.getString(0));  //gender
                userList.add(cursor.getString(1));  //sign
                gender_value= Integer.parseInt(userList.get(0).toString());
                sign_value= String.valueOf(userList.get(1));
            }
        }else{
            //新用户初始化通道
            sign_value="这个人很懒，啥也不想说";
            String insert=String.format("insert into msg(_NAME,_SIGN) values('%s','%s') ",name_value,sign_value);
            db.execSQL(insert);
        }
        final ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, gender_option);
        gender.setAdapter(genderAdapter);
        gender.setSelection(gender_value,true);
        sign.setText(sign_value);





        //返回上一级
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Setting.this,Main.class);
                startActivity(intent);
                Setting.this.finish();
            }
        });
        //返回登录页
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("isChecked","no");
                editor.putString("name",name_value);
                editor.putString("pass",pswd_value);
                editor.commit();
                Intent intent=new Intent(Setting.this,Login.class);
                startActivity(intent);
                Setting.this.finish();
            }
        });

        //提交修改
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_value=sign.getText().toString();
                pswd_value=pswd.getText().toString();
                String choice=gender.getSelectedItem().toString();
                switch (choice){
                    case "男":gender_value=1;break;
                    case "女":gender_value=2;break;
                    default:gender_value=0;break;
                }
                String updatePass=String.format("update users set _PSWD='%s' where _NAME='%s';",pswd_value,name_value);
                String updateMsg=String.format("update msg set _SIGN='%s',_GENDER=%d where _NAME='%s';",sign_value,gender_value,name_value);
                try{
                    db.execSQL(updatePass);
                    db.execSQL(updateMsg);
                    Toast.makeText(Setting.this,"成功",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(Setting.this,"失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
