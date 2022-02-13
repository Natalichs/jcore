import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherRequest {

    private static String host ="dataservice.accuweather.com";
    private static String key = "qbiTuZVxZGTVLGzHFZacSDtvBEUjeHVD";
    private static String scheme ="http";
    static OkHttpClient okHttpClient = new OkHttpClient();
    static ObjectMapper objectMapper = new ObjectMapper();


    public static String getCityId(String city) throws IOException {

        HttpUrl searchCityId = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("cities")
                .addPathSegment("search")
                .addQueryParameter("apikey", key)
                .addQueryParameter("q", city)
                .build();

        Request request = new Request.Builder()
                .addHeader("Accept","application/json")
                .url(searchCityId)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String jsonCityId = response.body().string();
        String key = objectMapper.readTree(jsonCityId).get(0).at("/Key").asText();

        return key;

    }

    public static Weather get1dayWeather(String city) throws IOException {
        String keyCity = getCityId(city);
        HttpUrl weaterFor1day = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("1day")
                .addPathSegment(keyCity)
                .addQueryParameter("apikey", key)
                .addQueryParameter("metric", "true")
                .build();

        Request request = new Request.Builder()
                .addHeader("Accept","application/json")
                .url(weaterFor1day)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String jsonWeather = response.body().string();
        String date = objectMapper.readTree(jsonWeather).at("/DailyForecasts").get(0).at("/Date").asText();
        double tmin = objectMapper.readTree(jsonWeather).at("/DailyForecasts").get(0).at("/Temperature/Minimum/Value").asDouble();
        double tmax = objectMapper.readTree(jsonWeather).at("/DailyForecasts").get(0).at("/Temperature/Maximum/Value").asDouble();
        double tmiddle=(tmin+tmax)/2;
        String wText = objectMapper.readTree(jsonWeather).at("/DailyForecasts").get(0).at("/Day/IconPhrase").asText();
        Weather weather = new Weather(city,date,wText,tmiddle);
        //System.out.printf("В городе %s на дату %s ожидается %s, средняя температура - %s C  \n \n",city,date,wText,tmiddle);
        return weather;

    }
    public static List<Weather> get5dayWeather(String city) throws IOException{
        String keyCity = getCityId(city);
        HttpUrl weatherFor5day = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("5day")
                .addPathSegment(keyCity)
                .addQueryParameter("apikey", key)
                .addQueryParameter("metric", "true")
                .build();

        Request request = new Request.Builder()
                .addHeader("Accept","application/json")
                .url(weatherFor5day)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String jsonWeather = response.body().string();
        //objectMapper.writeValue(new File("test.json"),jsonWeather);
        String[] date = new String[5];
        double[] tmin = new double[5];
        double[] tmax = new double[5];
        double[] tmiddle = new double[5];;
        String[] wText = new String[5];
        List<Weather> weathers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            date[i] = objectMapper.readTree(jsonWeather).at("/DailyForecasts").get(i).at("/Date").asText();
            tmin[i] = objectMapper.readTree(jsonWeather).at("/DailyForecasts").get(i).at("/Temperature/Minimum/Value").asDouble();
            tmax[i] = objectMapper.readTree(jsonWeather).at("/DailyForecasts").get(i).at("/Temperature/Maximum/Value").asDouble();
            tmiddle[i]=(tmin[i] + tmax[i])/2;
            wText[i] = objectMapper.readTree(jsonWeather).at("/DailyForecasts").get(i).at("/Day/IconPhrase").asText();
            weathers.add(new Weather(city,date[i],wText[i],tmiddle[i]));
            //System.out.printf("В городе %s на дату %s ожидается %s, средняя температура - %s C  \n",city,date[i],wText[i],tmiddle[i]);

        }
        return weathers;



    }



}
