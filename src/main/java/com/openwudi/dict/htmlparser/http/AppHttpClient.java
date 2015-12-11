package com.openwudi.dict.htmlparser.http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by Administrator on 2015/12/11.
 */
public class AppHttpClient {
    public final static String DICT_URL = "http://dict.cn/";
    public final static String DICT_AUDIO_URL="http://audio.dict.cn/";

    private static CloseableHttpClient instance = null;

    public static synchronized CloseableHttpClient getInstance(){
        if (instance == null){
            instance = HttpClients.createDefault();
        }
        return instance;
    }

    public static HttpGet newWordMethod(String word){
        return new HttpGet(DICT_URL+word);
    }

    public static HttpGet newAudioMethod(String mp3){
        return new HttpGet(DICT_AUDIO_URL+mp3);
    }
}
