package training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import training.demo.controller.util.ItineraryControllerUtil;
import training.demo.model.Itinerary;
import training.demo.model.ItineraryItem;
import training.demo.model.User;
import training.demo.security.JwtTokenUtil;
import training.demo.service.ItineraryItemJpaService;
import training.demo.service.ItineraryJpaService;
import training.demo.service.UserJpaService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/itinerary",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItineraryController {

    private String tokenHeader = "Authorization";

    @Autowired
    ItineraryJpaService itineraryService;
    @Autowired
    UserJpaService userService;
    @Autowired
    ItineraryItemJpaService itineraryItemService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(
            value = "/get",
            method = RequestMethod.GET)
    public ResponseEntity<List<Itinerary>> getAll(HttpServletRequest request){
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.loadUserByUsername(username);

        List<Itinerary> itineraries = itineraryService.findItineraryByUser(user);
        for (Itinerary itinerary:
             itineraries) {
            itinerary.setUser(null);
            List<ItineraryItem> itinerarieItems = itinerary.getItineraryItems();
            for (ItineraryItem item:
                 itinerarieItems) {
                item.setOrganiser(null);
                item.setItinerary(null);
            }
        }
        if(itineraries.isEmpty()){
            return new ResponseEntity<List<Itinerary>>(HttpStatus.NOT_FOUND);
        }
        System.out.println(itineraries.toString());
        return new ResponseEntity<List<Itinerary>>(itineraries, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST)
    public ResponseEntity<Itinerary> createItinerary(@RequestBody Itinerary itinerary,
                                                     HttpServletRequest request){
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.loadUserByUsername(username);
        itinerary.setUser(user);

        List<ItineraryItem> itineraryItemList = itinerary.getItineraryItems();

        if(itineraryService.addItinerary(itinerary)){
            ItineraryControllerUtil itineraryControllerUtil = new ItineraryControllerUtil();
            itineraryControllerUtil.saveItineraryItems(user, itineraryItemService, itinerary, itineraryItemList);
            itinerary.setUser(null);
            return new ResponseEntity<Itinerary>(itinerary, HttpStatus.OK);
        }
        return new ResponseEntity<Itinerary>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            value = "/update/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Itinerary> updateItinerary(@PathVariable("id") int idItinerary,
                                                     @RequestBody Itinerary itinerary,
                                                     HttpServletRequest request){
        Itinerary currentItinerary = itineraryService.
                findItineraryByItineraryId(idItinerary);

        if(currentItinerary == null){
            return new ResponseEntity<Itinerary>(HttpStatus.NOT_FOUND);
        }
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.loadUserByUsername(username);
        currentItinerary.setUser(user);
        currentItinerary.setItineraryName(itinerary.getItineraryName());
        currentItinerary.setRating(itinerary.getRating());
        currentItinerary.setReviews(itinerary.getReviews());
        currentItinerary.setItineraryItems(itinerary.getItineraryItems());

        if(itineraryService.addItinerary(currentItinerary)){
            return new ResponseEntity<Itinerary>(itinerary, HttpStatus.OK);
        }
        return new ResponseEntity<Itinerary>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Itinerary> deleteItinerary(@PathVariable("id") int idItinerary){
        itineraryService.deleteItineraryByItineraryId(idItinerary);
        return new ResponseEntity<Itinerary>(HttpStatus.OK);
    }
}