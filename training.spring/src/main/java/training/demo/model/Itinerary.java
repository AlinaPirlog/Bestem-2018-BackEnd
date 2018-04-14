package training.demo.model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "itinerary")
public class Itinerary {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itinerary_id")
    private int itineraryId;

    @Column(name = "itinerary_name")
    private String itineraryName;
    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "itinerary")
    private List<Review> reviews;

    @OneToMany(mappedBy = "itinerary")
    private List<ItineraryItem> itineraryItems;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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
}