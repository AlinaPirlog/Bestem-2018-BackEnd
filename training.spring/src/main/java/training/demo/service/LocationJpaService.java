package training.demo.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import training.demo.model.Itinerary;
import training.demo.model.Location;
import training.demo.repository.ItineraryRepository;
import training.demo.repository.LocationRepository;

@Service
@Transactional
public class LocationJpaService{

    @Autowired
    private LocationRepository locationRepository;

    public boolean addLocation(Location location) {
        if(locationRepository.save(location) != null){
            return true;
        }
        return false;
    }

    public boolean updateLocation(Location location) {
        if(locationRepository.save(location) != null){
            return true;
        }
        return false;
    }

    public void deleteLocationByLocationId(int id) {
        locationRepository.deleteLocationByLocationId(id);
    }

    public Location findLocationById(int id) {
        return locationRepository.findByLocationId(id);
    }

    public List<Location> findAllLocations(){
        return locationRepository.findAll();
    }

}
