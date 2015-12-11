package com.openwudi.dict.htmlparser;

import com.google.common.io.CharStreams;
import com.openwudi.dict.htmlparser.http.AppHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2015/12/11.
 */
public class App {
    public static void main(String[] args) throws IOException, ParserException {
        CloseableHttpClient client = AppHttpClient.getInstance();
        HttpGet get = AppHttpClient.newWordMethod("winning");
//        CloseableHttpResponse response = client.execute(get);
//        System.out.println(response.getStatusLine());
//        if (response.getStatusLine().getStatusCode() == 200){
//            String html = CharStreams.toString(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//            System.out.println(html);
//            int start = html.lastIndexOf("<ul class=\"dict-basic-ul\">");
//            int end = html.indexOf("<li style=\"padding-top: 25px;\">");
//
//            System.out.println();
//            System.out.println(html.substring(start,end));
//        }

        Document doc = Jsoup.connect(get.getURI().toString()).get();

        System.out.println("\n单词：");
        Elements eul = doc.select("ul[class=dict-basic]");
        for(Element element : eul){
            Elements li = element.getElementsByTag("li");
            for (Element eli : li){
                System.out.println(eli.text());
            }
        }

        System.out.println("\n语音：");
        Elements ediv = doc.select("div[class=phonetic]");
        for(Element element : ediv){
            Elements spans = element.getElementsByTag("span");
            for (Element span : spans){
                System.out.println(span.text());
                Elements sounds = span.select("i[naudio]");
                for (Element sound : sounds){
                    System.out.println(AppHttpClient.DICT_AUDIO_URL+sound.attr("naudio"));
                }
            }
        }

        //例句
        System.out.println("\n例句：");
        Elements eliju = doc.select("ol[slider=2]");
        for(Element element : eliju){
            Elements li = element.getElementsByTag("li");
            for (Element eli : li){
                System.out.println(eli.text());
            }
        }
    }
}
