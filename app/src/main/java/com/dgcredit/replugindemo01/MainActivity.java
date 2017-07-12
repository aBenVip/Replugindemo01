package com.dgcredit.replugindemo01;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.qihoo360.replugin.RePlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView viewById = (TextView) findViewById(R.id.tv_show);
        ImageView ivShow = (ImageView) findViewById(R.id.imageView);

        ClassLoader classLoader = RePlugin.getHostClassLoader();

        //加载宿主中图片资源，测试不能用
       /* Resources resources = RePlugin.getHostContext().getResources();
        int identifier = resources.getIdentifier("icon_app", "drawable", "com.dgcredit.repluginhostdemo");
        Log.i("TAG",identifier+"++++++++++++++++");
        ivShow.setImageResource(identifier);*/
        try{
            Class clazz = classLoader.loadClass("com.dgcredit.repluginhostdemo.R$drawable");
            Field field = clazz.getField("bg_about");
            int identifier = (int)field.get(null);
            ivShow.setImageResource(identifier);
        }catch(Exception e){
            Log.i("Loader", "error:"+Log.getStackTraceString(e));
        }

        //加载宿主中的工具类
        try {
            Class<?> aClass = classLoader.loadClass("com.dgcredit.repluginhostdemo.DateHelper");
            Method formatDate = aClass.getMethod("formatDate", Date.class);
            String invoke = (String) formatDate.invoke(null, new Date());
            viewById.setText(invoke);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
