package training.demo.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "itinerary")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@itineraryId")
public class Itinerary implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itinerary_id")
    private int itineraryId;

    @Column(name = "itinerary_name")
    private String itineraryName;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "color")
    private String color;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "itinerary")
    private List<Review> reviews;

    @OneToMany(mappedBy = "itinerary")
    private List<ItineraryItem> itineraryItems;

    public Itinerary() {
        super();
    }

    public Itinerary(String itineraryName, Integer rating,
                     User user, List<Review> reviews,
                     List<ItineraryItem> itineraryItems,
                     String color) {
        this.itineraryName = itineraryName;
        this.rating = rating;
        this.rating = rating;
        this.user = user;
        this.reviews = reviews;
        this.itineraryItems = itineraryItems;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getItineraryName() {
        return itineraryName;
    }

    public void setItineraryName(String itineraryName) {
        this.itineraryName = itineraryName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<ItineraryItem> getItineraryItems() {
        return itineraryItems;
    }

    public void setItineraryItems(List<ItineraryItem> itineraryItems) {
        this.itineraryItems = itineraryItems;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}