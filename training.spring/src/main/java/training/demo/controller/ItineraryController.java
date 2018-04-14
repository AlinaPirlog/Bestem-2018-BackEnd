package training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import training.demo.model.Itinerary;
import training.demo.service.ItineraryJpaService;

@RestController
@RequestMapping(value = "/api/itinerary",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItineraryController {

    private String tokenHeader = "Authorization";

    @Autowired
    ItineraryJpaService itineraryService;

    @RequestMapping(
            value = "/get",
            method = RequestMethod.GET)
    public ResponseEntity<List<Itinerary>> getAll(){
        List<Itinerary> itineraries = itineraryService.findAllItineraries();
        if(itineraries.isEmpty()){
            return new ResponseEntity<List<Itinerary>>(HttpStatus.NOT_FOUND);
        }
        System.out.println(itineraries.toString());
        return new ResponseEntity<List<Itinerary>>(itineraries, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST)
    public ResponseEntity<Itinerary> createEvent(@RequestBody Itinerary itinerary){
        if(itineraryService.addItinerary(itinerary)){
            return new ResponseEntity<Itinerary>(itinerary, HttpStatus.OK);
        }
        return new ResponseEntity<Itinerary>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            value = "/update/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Itinerary> updateEvent(@PathVariable("id") int idEvent){
        Itinerary event = itineraryService.findItineraryById(idEvent);
        if(event == null){
            return new ResponseEntity<Itinerary>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<Itinerary>(event,HttpStatus.OK);
        }
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Itinerary> createPin(@PathVariable("id") int idEvent){
        itineraryService.deleteItineraryByItineraryId(idEvent);
        return new ResponseEntity<Itinerary>(HttpStatus.OK);
    }
}