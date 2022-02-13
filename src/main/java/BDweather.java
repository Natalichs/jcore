import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDweather {

    private final static String DB_PATH = "jdbc:sqlite:C:\\Users\\n.borisova\\JavaCoreTest\\untitled\\src\\main\\resources\\weather.db";

    private Connection connection;
    public BDweather() throws SQLException{
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(DB_PATH);
    }

    public void addDate(Weather weather) throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO weather_info ('city', 'localDate', 'weather_text', 'temperature') VALUES(?,?,?,?)");
       statement.setObject(1,weather.getCity());
        statement.setObject(2,weather.getLocalDate());
        statement.setObject(3,weather.getWeather_text());
        statement.setObject(4,weather.getTemperature());
        statement.execute();

    }

    public List<Weather> getAllLine() throws SQLException {
        List<Weather> weathers =new ArrayList<>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM weather_info");
        while (resultSet.next()){
            String city = resultSet.getString("city");
            String localDate = resultSet.getString("localDate");
            String weather_text = resultSet.getString("weather_text");
            Double temperature = resultSet.getDouble("temperature");

            Weather weather = new Weather(city,localDate,weather_text,temperature);
            weathers.add(weather);
        }
        return weathers;

    }
    public void deleteDate() throws SQLException{
        PreparedStatement statement = this.connection.prepareStatement("DELETE FROM weather_info");
        statement.execute();

    }



}
