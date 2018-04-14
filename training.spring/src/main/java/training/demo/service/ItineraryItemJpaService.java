package training.demo.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import training.demo.model.Itinerary;
import training.demo.model.ItineraryItem;
import training.demo.model.User;
import training.demo.repository.ItineraryItemRepository;
import training.demo.repository.ItineraryRepository;

@Service
@Transactional
public class ItineraryItemJpaService{

    @Autowired
    private ItineraryItemRepository itineraryItemRepository;

    public boolean addItineraryItem(ItineraryItem itineraryItem) {
        if(itineraryItemRepository.save(itineraryItem) != null){
            return true;
        }
        return false;
    }

    public void deleteItineraryByItineraryItemId(int id) {
        itineraryItemRepository.deleteItineraryItemByItineraryItemId(id);
    }

    public ItineraryItem findItineraryByItineraryItemId(int id) {
        return itineraryItemRepository.findByItineraryItemId(id);
    }

    public List<ItineraryItem> findItinerariesByItinerary(Itinerary itinerary) {
        return itineraryItemRepository.findByItinerary(itinerary);
    }

    public List<ItineraryItem> findAllItineraries(){
        return itineraryItemRepository.findAll();
    }

}
