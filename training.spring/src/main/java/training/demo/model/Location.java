package training.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int userId;
}