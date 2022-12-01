package ru.netology;

import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    static final String[] columnMapping = {"id", "text", "type", "user", "upvotes"};

    public static void main(String[] args) throws IOException, ParseException {

        final String REMOTE_JSON =
                "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        final int CONNECT_TIMEOUT = 5000;
        final int SOCKET_TIMEOUT = 30_000;

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("My Homework")
                .setDefaultRequestConfig(RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SOCKET_TIMEOUT)

                        .setRedirectsEnabled(false)
                    .build())
                .build();
        // формируем запрос: стартовую строку и заголовок
        HttpGet request = new HttpGet(REMOTE_JSON);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        // посылаем запрос и получаем json
        CloseableHttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());
        // System.out.println(json);

        // Преобразуем json в экземпляры класса CatFact
        List<CatFact> catFactList = jsonToList(columnMapping, json);
        //
        Stream<CatFact> stream = catFactList.stream();
        stream.filter(val -> val.getUpvotes() > 0).forEach(System.out::println);
    }

    public static @NotNull List<CatFact> jsonToList(String[] column, String json) throws ParseException {

        String[] attr = new String[columnMapping.length];
        List<CatFact> cats = new ArrayList<>();

        JSONParser pars = new JSONParser();
        Object obj = pars.parse(json);
        JSONArray jsonObj = (JSONArray) obj;

        for (Object o : jsonObj) {
            JSONObject jsonArrayElement = (JSONObject) o;

            for (int j = 0; j < column.length; j++) {
                attr[j] = " ";
                attr[j] = (jsonArrayElement.get(column[j]) != null) ?
                        jsonArrayElement.get(column[j]).toString() : "0";
            }
            CatFact cat = new CatFact(attr);
            cats.add(cat);
        }
        return cats;
    }
}