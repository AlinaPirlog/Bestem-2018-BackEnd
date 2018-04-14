package training.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
	@Column(name = "id")
	private int userId;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;

	@Column(name = "role")
	private String role;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "company_name")
    private String companyName;

    @OneToMany(mappedBy = "user")
    private List<Itinerary> itineraries;

    @OneToMany(mappedBy = "organiser")
    private List<ItineraryItem> itineraryItems;

    @ManyToMany(mappedBy = "users")
    private List<Review> reviews;

    @ManyToMany(mappedBy = "participants")
    private List<ItineraryItem> itineraryItemsToParticipate;

    public User() {
        super();
    }

    public User(String username, String password,
                String email, String role,
                String phoneNumber, String companyName,
                List<Itinerary> itineraries, List<ItineraryItem> itineraryItems,
                List<Review> reviews, List<ItineraryItem> itineraryItemsToParticipate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.itineraries = itineraries;
        this.itineraryItems = itineraryItems;
        this.reviews = reviews;
        this.itineraryItemsToParticipate = itineraryItemsToParticipate;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    @Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<ItineraryItem> getItineraryItems() {
        return itineraryItems;
    }

    public void setItineraryItems(List<ItineraryItem> itineraryItems) {
        this.itineraryItems = itineraryItems;
    }

    public List<ItineraryItem> getItineraryItemsToParticipate() {
        return itineraryItemsToParticipate;
    }

    public void setItineraryItemsToParticipate(List<ItineraryItem> itineraryItemsToParticipate) {
        this.itineraryItemsToParticipate = itineraryItemsToParticipate;
    }

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			grantedAuthorities.add(new SimpleGrantedAuthority(getRole()));
        return grantedAuthorities;
	}

	@JsonIgnore
    @Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
    @Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
    @Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
