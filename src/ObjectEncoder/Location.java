package ObjectEncoder;

public class Location 
{
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;

    public Location()
    {
        country = new String();
        city = new String();
    }

    public String getCountry()
    {
        return country;
    }

    public String getCity()
    {
        return city;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }
}