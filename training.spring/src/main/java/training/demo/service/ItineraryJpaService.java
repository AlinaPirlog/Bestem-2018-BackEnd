package training.demo.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import training.demo.model.Itinerary;
import training.demo.model.User;
import training.demo.repository.ItineraryRepository;

@Service
@Transactional
public class ItineraryJpaService{

    @Autowired
    private ItineraryRepository itineraryRepository;

    public boolean addItinerary(Itinerary itinerary) {
        if(itineraryRepository.save(itinerary) != null){
            return true;
        }
        return false;
    }

    public void deleteItineraryByItineraryId(int id) {
        itineraryRepository.deleteItineraryByItineraryId(id);
    }

    public Itinerary findItineraryByItineraryId(int id) {
        return itineraryRepository.findByItineraryId(id);
    }

    public List<Itinerary> findItineraryByUser(User user) {
        return itineraryRepository.findByUser(user);
    }

    public List<Itinerary> findAllItineraries(){
        return itineraryRepository.findAll();
    }

}
