package training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.demo.model.Itinerary;
import training.demo.model.ItineraryItem;
import training.demo.model.User;

import java.util.List;

public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, Integer> {

    void deleteItineraryItemByItineraryItemId(int id);

    ItineraryItem findByItineraryItemId(int id);

    List<ItineraryItem> findAll();

    List<ItineraryItem> findByItinerary(Itinerary itinerary);
}