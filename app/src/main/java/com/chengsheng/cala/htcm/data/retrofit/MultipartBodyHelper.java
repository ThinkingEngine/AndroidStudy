package com.chengsheng.cala.htcm.data.retrofit;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class MultipartBodyHelper {

    public static MultipartBody.Part transform(String path, String key) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(key, file.getName(), requestFile);
    }
}
