package com.wd.winddots.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.wd.winddots.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import cn.jmessage.support.qiniu.android.utils.StringUtils;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class Utils {

    public static final String DOCUMENTS_DIR = "documents";

    /*
     * 将 json 转化为 map
     * */
    public static Map getMapForJson(String jsonStr) {

        if (StringUtils.isNullOrEmpty(jsonStr)) return null;

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonStr);

            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map valueMap = new HashMap();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map> getListForJson(String jsonString) {
        List<Map> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject;
            list = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(getMapForJson(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //dp转px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //px转dp
    public static int px2dip(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }




    public static Uri path2uri(Context context,String path){
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[] {path.substring(path.lastIndexOf("/") + 1)},
                null);

        Uri uri = null;
        if(cursor.moveToFirst()) {
            uri = ContentUris.withAppendedId(mediaUri,
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
        }
        cursor.close();
        return uri;
    }

    /*
     * uri 转 file
     * */
    public static File uri2File(Context context, Uri uri) {
        String img_path;

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = context.getContentResolver().query(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);

        return file;
    }

    /*
     * urirequestBody 对象  上传用
     * */
    public static RequestBody uri2requestBody(Context context, Uri uri) {
        File file = Utils.uri2File(context, uri);
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("picture", file.getName(), requestBody3)
                .build();
        return multipartBody;
    }


    public static RequestBody list2requestBody(List<Map> data) {
        Gson gson = new Gson();
        String bodyString = gson.toJson(data);
        Log.e("net666", bodyString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
        return requestBody;
    }

    public static RequestBody map2requestBody(Map data) {
        Gson gson = new Gson();
        String bodyString = gson.toJson(data);
        Log.e("net666", bodyString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
        return requestBody;
    }


    /*
     * 获取屏幕宽度
     * */
    public static int getScreenWidth(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        return widthPixel;
        //int mItemS = widthPixel/3;
    }

    /*
     * 获取屏幕高度
     * */
    public static int getScreenHeight(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int heightPixel = outMetrics.heightPixels;
        return heightPixel;
        //int mItemS = widthPixel/3;
    }

    public static String nullOrEmpty(String s) {
        if (StringUtils.isNullOrEmpty(s)){
            return "";
        }else if ("null".equals(s)){
            return "";
        }else {
            return s;
        }


    }

    public static String numberNullOrEmpty(String s) {
        if (StringUtils.isNullOrEmpty(s)){
            return "0";
        }else if ("null".equals(s)){
            return "0";
        }else {
            return s;
        }
    }


    public static String OSSImageSize(int size) {
        return "?x-oss-process=image/resize,m_fill,h_" + size + ",w_" + size;
    }


    public static String int2String(int number) {
        String numberS = "";
        if (number < 10) {
            numberS = "0" + number;
        } else {
            numberS = "" + number;
        }
        return numberS;
    }


    /*
    *
    * 获取当前年月日
    * */
    public static String getCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + Utils.int2String((month + 1)) + "-" + Utils.int2String(day);
    }

    public static String getCurrencyByNumber(Context context, int currency){
        String currencyType;
        switch (currency) {
            case 1:
                currencyType = context.getString(R.string.currency_dollar);
                break;
            case 2:
                currencyType = context.getString(R.string.currency_euro);
                break;
            case 3:
                currencyType = context.getString(R.string.currency_pound);
                break;
            case 4:
                currencyType = context.getString(R.string.currency_hkd);
                break;
            case 5:
                currencyType = context.getString(R.string.currency_cad);
                break;
            case 6:
                currencyType = context.getString(R.string.currency_yen);
                break;
            case 7:
                currencyType = context.getString(R.string.currency_ntc);
                break;
            case 8:
                currencyType = context.getString(R.string.currency_vnd);
                break;
            case 9:
                currencyType = context.getString(R.string.currency_rmb);
                break;
            default:
                currencyType = context.getString(R.string.currency_rmb);
                break;
        }
        return currencyType;
    }

    public static String getCurrencyByString(Context context, String currency){
        String currencyType;
        switch (currency) {
            case "1":
                currencyType = context.getString(R.string.currency_dollar);
                break;
            case "2":
                currencyType = context.getString(R.string.currency_euro);
                break;
            case "3":
                currencyType = context.getString(R.string.currency_pound);
                break;
            case "4":
                currencyType = context.getString(R.string.currency_hkd);
                break;
            case "5":
                currencyType = context.getString(R.string.currency_cad);
                break;
            case "6":
                currencyType = context.getString(R.string.currency_yen);
                break;
            case "7":
                currencyType = context.getString(R.string.currency_ntc);
                break;
            case "8":
                currencyType = context.getString(R.string.currency_vnd);
                break;
            case "9":
                currencyType = context.getString(R.string.currency_rmb);
                break;
            default:
                currencyType = context.getString(R.string.currency_rmb);
                break;
        }
        return currencyType;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String diskUri2FilePath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (id != null && id.startsWith("raw:")) {
                    return id.substring(4);
                }
                String[] contentUriPrefixesToTry = new String[]{
                        "content://downloads/public_downloads",
                        "content://downloads/my_downloads"
                };
                for (String contentUriPrefix : contentUriPrefixesToTry) {
                    try {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                        String path = getDataColumn(context, contentUri, null, null);
                        if (path != null && !path.equals("")) {
                            return path;
                        }
                    } catch (Exception e) {
                    }
                }
                // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                String fileName = getFileName(context, uri);
                File cacheDir = getDocumentCacheDir(context);
                File file = generateFileName(fileName, cacheDir);
                String destinationPath = null;
                if (file != null) {
                    destinationPath = file.getAbsolutePath();
                    saveFileFromUri(context, uri, destinationPath);
                }

                return destinationPath;
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            String path = getDataColumn(context, uri, null, null);
            if (path != null && !path.equals("")) return path;

            // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
            String fileName = getFileName(context, uri);
            File cacheDir = getDocumentCacheDir(context);
            File file = generateFileName(fileName, cacheDir);
            String destinationPath = null;
            if (file != null) {
                destinationPath = file.getAbsolutePath();
                saveFileFromUri(context, uri, destinationPath);
            }
            return destinationPath;
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        String path = "";
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                path = cursor.getString(column_index);
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return path;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getFileName(Context context, Uri uri) {
        String mimeType = context.getContentResolver().getType(uri);
        String filename = null;

        if (mimeType == null && context != null) {
            String path = diskUri2FilePath(context, uri);
            if (path == null) {
                filename = getName(uri.toString());
            } else {
                File file = new File(path);
                filename = file.getName();
            }
        } else {
            Cursor returnCursor = context.getContentResolver().query(uri, null,
                    null, null, null);
            if (returnCursor != null) {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                filename = returnCursor.getString(nameIndex);
                returnCursor.close();
            }
        }

        return filename;
    }

    public static String getName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('/');
        return filename.substring(index + 1);
    }

    public static File getDocumentCacheDir(Context context) {
        File dir = new File(context.getCacheDir(), DOCUMENTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    @Nullable
    public static File generateFileName(@Nullable String name, File directory) {
        if (name == null) {
            return null;
        }

        File file = new File(directory, name);

        if (file.exists()) {
            String fileName = name;
            String extension = "";
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = name.substring(0, dotIndex);
                extension = name.substring(dotIndex);
            }

            int index = 0;

            while (file.exists()) {
                index++;
                name = fileName + '(' + index + ')' + extension;
                file = new File(directory, name);
            }
        }

        try {
            if (!file.createNewFile()) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        return file;
    }


    private static void saveFileFromUri(Context context, Uri uri, String destinationPath) {
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            bos = new BufferedOutputStream(new FileOutputStream(destinationPath, false));
            byte[] buf = new byte[1024];
            is.read(buf);
            do {
                bos.write(buf);
            } while (is.read(buf) != -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bos != null) bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

}
