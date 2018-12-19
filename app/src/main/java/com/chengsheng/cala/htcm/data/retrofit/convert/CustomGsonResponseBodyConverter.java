package com.chengsheng.cala.htcm.data.retrofit.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();
//        BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
//
//        String errorMsg;
//
//        if (String.valueOf(baseEntity.getCode()).startsWith("4")) {
//            //token过期
//            value.close();
//            if (baseEntity.getMessage() == null || baseEntity.getMessage().isEmpty()) {
//                errorMsg = "登录已失效";
//            } else {
//                errorMsg = baseEntity.getMessage();
//            }
//
//            throw new InvalidLoginException(errorMsg);
//
//        } else if (!String.valueOf(baseEntity.getCode()).startsWith("2")) {
//            value.close();
//            if (baseEntity.getMessage() == null || baseEntity.getMessage().isEmpty()) {
//                errorMsg = "系统异常";
//            } else {
//                errorMsg = baseEntity.getMessage();
//            }
//
//            throw new BusinessException(baseEntity.getCode(), errorMsg);
//        }

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}

