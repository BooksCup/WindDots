package com.wd.winddots.message.record;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.wd.winddots.message.record.audio.AudioPlayManager;
import com.wd.winddots.message.record.audio.IAudioPlayListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * FileName: Player
 * Author: 郑
 * Date: 2020/11/19 2:03 PM
 * Description:
 */
public class Player  {

    private static final int BUFFER_SIZE =  1024; // 8k ~ 32K

   static public void play(final Context context, final String urlStr){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //需要在子线程中处理的逻辑
                InputStream in = null;
                FileOutputStream out = null;
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(false);
                    urlConnection.setConnectTimeout(10 * 1000);
                    urlConnection.setReadTimeout(10 * 1000);
                    urlConnection.setRequestProperty("Connection", "Keep-Alive");
                    urlConnection.setRequestProperty("Charset", "UTF-8");
                    urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
                    urlConnection.connect();
                    long bytetotal = urlConnection.getContentLength();
                    long bytesum = 0;
                    int byteread = 0;
                    in = urlConnection.getInputStream();
                    File dir = com.nostra13.universalimageloader.utils.StorageUtils.getCacheDirectory(context);
                    String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
                    File file = new File(dir, apkName);
                    out = new FileOutputStream(file);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while ((byteread = in.read(buffer)) != -1) {
                        bytesum += byteread;
                        out.write(buffer, 0, byteread);
                    }

                    AudioPlayManager.getInstance().startPlay(context, Uri.parse(file.toString()), new IAudioPlayListener() {
                        @Override
                        public void onStart(Uri var1) {
                            //mView.startPlayAnim(position);
                        }

                        @Override
                        public void onStop(Uri var1) {
                            //mView.stopPlayAnim();
                        }

                        @Override
                        public void onComplete(Uri var1) {
                            //mView.stopPlayAnim();
                        }
                    });

                } catch (Exception e) {
                    Log.e("net666", "download audio file error");
                    Log.e("net666", String.valueOf(e));
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ignored) {

                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ignored) {

                        }
                    }
                }
            }
        }).start();

    }
}
