package entities;

public class UvIndex {

    private String city;

    private double uv_min;
    private long uv_min_time;
    private double uv_max;
    private long uv_max_time;
    private long sunrise_time;
    private long sunset_time;
    
    public UvIndex() {
        
    }

    public String getCity() {
        return city;
    }

    public double getUvMin() {
        return uv_min;
    }

    public long getUvMinTime() {
        return uv_min_time;
    }

    public double getUvMax() {
        return uv_max;
    }

    public long getUvMaxTime() {
        return uv_max_time;
    }

    public long getSunriseTime() {
        return sunrise_time;
    }

    public long getSunsetTime() {
        return sunset_time;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public void setUv_min(double uv_min) {
        this.uv_min = uv_min;
    }

    public void setUv_min_time(long uv_min_time) {
        this.uv_min_time = uv_min_time;
    }

    public void setUv_max(double uv_max) {
        this.uv_max = uv_max;
    }

    public void setUv_max_time(long uv_max_time) {
        this.uv_max_time = uv_max_time;
    }

    public void setSunrise_time(long sunrise_time) {
        this.sunrise_time = sunrise_time;
    }

    public void setSunset_time(long sunset_time) {
        this.sunset_time = sunset_time;
    }
    
}
