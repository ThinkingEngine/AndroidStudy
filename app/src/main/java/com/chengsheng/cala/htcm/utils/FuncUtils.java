package com.chengsheng.cala.htcm.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.widget.Toast;


import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.chengsheng.cala.htcm.MyApplication.myContext;

public class FuncUtils {


    private FuncUtils() {
        throw new UnsupportedOperationException("禁止实例化该类!");
    }


    public static SharedPreferences putString(String key, String value) {
        myContext.getSharedPreferences("config", Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .commit();

        return myContext.getSharedPreferences("config", myContext.MODE_PRIVATE);
    }

    public static SharedPreferences putBoolean(String key, boolean value) {
        myContext.getSharedPreferences("config", myContext.MODE_PRIVATE)
                .edit()
                .putBoolean(key, value)
                .commit();

        return myContext.getSharedPreferences("config", myContext.MODE_PRIVATE);
    }

    public static String getString(String key, String defaultVal) {
        String result = myContext.getSharedPreferences("config", myContext.MODE_PRIVATE)
                .getString(key.trim(), defaultVal.trim());
        return result;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return myContext.getSharedPreferences("config", myContext.MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    public static SharedPreferences clear() {
        SharedPreferences sp = myContext.getSharedPreferences("config", myContext.MODE_PRIVATE);
        sp.edit().clear().commit();

        return myContext.getSharedPreferences("config", myContext.MODE_PRIVATE);
    }

    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int dip2px(int dp) {
        final float density = myContext.getResources().getDisplayMetrics().density;

        return (int) (dp * density + 0.5);
    }

    public static boolean isMobileNO(String mobileNum) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */

        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNum))
            return false;
        else
            return mobileNum.matches(telRegex);
    }

    public static boolean isIdentityCode(String code, Context context) {

        if (code == null || "".equals(code)) {
            return false;
        }
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾
        boolean matches = code.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (code.length() == 18) {
                try {
                    char[] charArray = code.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        Toast.makeText(context, "身份证最后一位:" + String.valueOf(idCardLast).toUpperCase()
                                + "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } catch (Exception e) {

                    Toast.makeText(context, "异常:" + code, Toast.LENGTH_SHORT).show();

                    return false;
                }
            }

        }
        return matches;

    }

    public static String getReal(Context context,Uri uri){
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, context);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null, context);
            }
            //根据图片路径显示图片
            //            displayImage(imagePath, null);

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null, context);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;

    }

    private static String getImagePath(Uri uri, String selection, Context context) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
