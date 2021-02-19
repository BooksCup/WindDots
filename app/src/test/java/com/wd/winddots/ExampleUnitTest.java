package com.wd.winddots;

import com.wd.winddots.utils.Logg;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void getMessage(){
        try{
            String s = "http://54.223.179.143:8091/conversations?fromId=a2c3b2f6d32345b2818be757f5adb54f";
            URL url = new URL(s);
            //得到connection对象。
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //连接
            connection.connect();
            //得到响应码
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                //得到响应流
                InputStream inputStream = connection.getInputStream();
                //将响应流转换成字符串

                //连接后，创建一个输入流来读取response
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                String response = "";
                //每次读取一行，若非空则添加至 stringBuilder
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                //读取所有的数据后，赋值给 response
                String result = stringBuilder.toString().trim();

                System.out.println(result);
            }
        } catch (Exception e){

        }
    }


}