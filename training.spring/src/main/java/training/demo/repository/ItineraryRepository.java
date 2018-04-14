package training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.demo.model.Itinerary;

import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {

    void deleteItineraryByItineraryId(int id);

    Itinerary findByItineraryId(int id);

    List<Itinerary> findAll();
}