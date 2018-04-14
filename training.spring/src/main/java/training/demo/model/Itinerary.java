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
    private int itineraryID;

    @Column(name = "itinerary_name")
    private String itineraryName;
    @Column(name = "rating")
    private int rating;
    @OneToMany(mappedBy = "itinerary")
    private List<User> users;

    public int getItineraryID() {
        return itineraryID;
    }

    public void setItineraryID(int itineraryID) {
        this.itineraryID = itineraryID;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}