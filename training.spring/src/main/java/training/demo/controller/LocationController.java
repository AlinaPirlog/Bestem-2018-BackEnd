package training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import training.demo.model.Location;
import training.demo.service.LocationJpaService;

@RestController
@RequestMapping(value = "/api/location",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocationController {

    private String tokenHeader = "Authorization";

    @Autowired
    LocationJpaService locationJpaService;

    @RequestMapping(
            value = "/get",
            method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getAll(){
        List<Location> locations = locationJpaService.findAllLocations();
        if(locations.isEmpty()){
            return new ResponseEntity<List<Location>>(HttpStatus.NOT_FOUND);
        }
        System.out.println(locations.toString());
        return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST)
    public ResponseEntity<Location> createEvent(@RequestBody Location location){
        if(locationJpaService.addLocation(location)){
            return new ResponseEntity<Location>(location, HttpStatus.OK);
        }
        return new ResponseEntity<Location>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST)
    public ResponseEntity<Location> updateEvent(@RequestBody Location location){
        Location searchedlocation = locationJpaService.findLocationById(location.getLocationId());
        if(searchedlocation == null){
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<Location>(searchedlocation, HttpStatus.OK);
        }
    }

    @RequestMapping(
            value = "/delete/",
            method = RequestMethod.POST)
    public ResponseEntity<Location> createPin(@RequestBody Location location){
        locationJpaService.deleteLocationByLocationId(location.getLocationId());
        return new ResponseEntity<Location>(HttpStatus.OK);
    }
}