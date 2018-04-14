package training.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "review")
public class Review {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private int reviewId;
    @Column(name = "rating")
    private int rating;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "itinerary_id", nullable = false)
    private Itinerary itinerary;
    @ManyToMany
    @JoinTable(
            name="review_user",
            joinColumns=@JoinColumn(name="review_id", referencedColumnName="review_id"),
            inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
}