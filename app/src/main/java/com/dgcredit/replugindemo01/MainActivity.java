package com.dgcredit.replugindemo01;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Binder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.qihoo360.replugin.RePlugin;

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
        //加载宿主中图片资源


        Resources resources = RePlugin.getHostContext().getResources();
        int identifier = resources.getIdentifier("icon_app", "mipmap", "com.dgcredit.repluginhostdemo");
        Log.i("TAG",identifier+"++++++++++++++++");
        ivShow.setImageResource(identifier);


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
