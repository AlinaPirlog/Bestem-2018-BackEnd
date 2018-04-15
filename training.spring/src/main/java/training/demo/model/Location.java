package training.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@location_id")
public class Location {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private int locationId;

    @Column(name = "location_name")
    private String locationName;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "description")
    private String description;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @OneToMany(mappedBy = "location")
    private List<ItineraryItem> itineraryItems;

    public Location() {
        super();
    }

    public Location(String locationName, String longitude,
                    String latitude, String description,
                    String city, String country, List<ItineraryItem> itineraryItems) {
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.city = city;
        this.country = country;
        this.itineraryItems = itineraryItems;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<ItineraryItem> getItineraryItems() {
        return itineraryItems;
    }

    public void setItineraryItems(List<ItineraryItem> itineraryItems) {
        this.itineraryItems = itineraryItems;
    }
}