public class Weather {
    private String city;
    private String localDate;
    private String weather_text;
    private Double temperature;

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", localDate='" + localDate + '\'' +
                ", weather_text='" + weather_text + '\'' +
                ", temperature=" + temperature +
                '}'+'\n';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getWeather_text() {
        return weather_text;
    }

    public void setWeather_text(String weather_text) {
        this.weather_text = weather_text;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Weather(String city, String localDate, String weather_text, Double temperature) {
        this.city = city;
        this.localDate = localDate;
        this.weather_text = weather_text;
        this.temperature = temperature;
    }
}
