import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        WeatherRequest.get1dayWeather("Moscow");
        WeatherRequest.get5dayWeather("Moscow");



    }

}