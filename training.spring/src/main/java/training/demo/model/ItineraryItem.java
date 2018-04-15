package training.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "itinerary_item")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@itinerary_item_id")
public class ItineraryItem {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itinerary_item_id")
    private int itineraryItemId;
    @Column(name = "name")
    private String itineraryItemName;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User organiser;

    public ItineraryItem() {
        super();
    }

    @ManyToMany
    @JoinTable(
            name="itinerary_user",
            joinColumns=@JoinColumn(name="itinerary_item_id", referencedColumnName="itinerary_item_id"),
            inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
    private List<User> participants;

    public ItineraryItem(String itineraryItemName, Date startDate,
                         Date endDate, String description,
                         Itinerary itinerary, User organiser,
                         List<User> participants, Location location) {
        this.itineraryItemName = itineraryItemName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.itinerary = itinerary;
        this.location = location;
        this.organiser = organiser;
        this.participants = participants;
    }

    public int getItineraryItemId() {
        return itineraryItemId;
    }

    public void setItineraryItemId(int itineraryItemId) {
        this.itineraryItemId = itineraryItemId;
    }

    public String getItineraryItemName() {
        return itineraryItemName;
    }

    public void setItineraryItemName(String itineraryItemName) {
        this.itineraryItemName = itineraryItemName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getOrganiser() {
        return organiser;
    }

    public void setOrganiser(User organiser) {
        this.organiser = organiser;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}