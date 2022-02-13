import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Weather weatherFor1day =  WeatherRequest.get1dayWeather("Moscow");
        //System.out.println(weatherFor1day);
        List<Weather> weathers = WeatherRequest.get5dayWeather("Moscow");


        try {
            BDweather bDweather = new BDweather();
            bDweather.addDate(weatherFor1day);
            for(Weather w:weathers){
                bDweather.addDate(w);
            }
            //bDweather.deleteDate();
            System.out.println(bDweather.getAllLine());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}