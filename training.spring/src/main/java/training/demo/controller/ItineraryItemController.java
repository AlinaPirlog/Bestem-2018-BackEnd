package training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import training.demo.model.Itinerary;
import training.demo.model.ItineraryItem;
import training.demo.model.User;
import training.demo.security.JwtTokenUtil;
import training.demo.service.ItineraryItemJpaService;
import training.demo.service.ItineraryJpaService;
import training.demo.service.UserJpaService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/itinerary_item",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItineraryItemController {

    private String tokenHeader = "Authorization";

    @Autowired
    ItineraryItemJpaService itineraryItemService;
    @Autowired
    UserJpaService userService;
    @Autowired
    ItineraryJpaService itineraryService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(
            value = "/find/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<List<ItineraryItem>> getAll(@PathVariable("id") int idItinerary){
        Itinerary itinerary = itineraryService.findItineraryByItineraryId(idItinerary);

        List<ItineraryItem> itineraryItems = itineraryItemService.findItinerariesByItinerary(itinerary);
        if(itineraryItems.isEmpty()){
            return new ResponseEntity<List<ItineraryItem>>(itineraryItems, HttpStatus.NOT_FOUND);
        }
        System.out.println(itineraryItems.toString());
        return new ResponseEntity<List<ItineraryItem>>(itineraryItems, HttpStatus.OK);
    }


    @RequestMapping(
            value = "/create/{id}",
            method = RequestMethod.POST)
    public ResponseEntity<ItineraryItem> createItineraryItem(@RequestBody ItineraryItem itineraryItem,
                                                             @PathVariable("id") int idItinerary,
                                                             HttpServletRequest request){
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.loadUserByUsername(username);
        itineraryItem.setOrganiser(user);

        Itinerary itinerary = itineraryService.findItineraryByItineraryId(idItinerary);
        itineraryItem.setItinerary(itinerary);

        if(itineraryItemService.addItineraryItem(itineraryItem)){
            return new ResponseEntity<ItineraryItem>(itineraryItem, HttpStatus.OK);
        }
        return new ResponseEntity<ItineraryItem>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            value = "/update/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<ItineraryItem> updateItineraryItem(@PathVariable("id") int idItineraryItem,
                                                     @RequestBody ItineraryItem itineraryItem,
                                                     HttpServletRequest request){


        ItineraryItem currentItineraryItem = itineraryItemService
                .findItineraryByItineraryItemId(idItineraryItem);

        if(currentItineraryItem == null){
            return new ResponseEntity<ItineraryItem>(HttpStatus.NOT_FOUND);
        }

        currentItineraryItem.setItineraryItemName(itineraryItem.getItineraryItemName());
        currentItineraryItem.setDescription(itineraryItem.getDescription());
        currentItineraryItem.setStartDate(itineraryItem.getStartDate());
        currentItineraryItem.setEndDate(itineraryItem.getEndDate());
        currentItineraryItem.setOrganiser(currentItineraryItem.getOrganiser());
        currentItineraryItem.setParticipants(itineraryItem.getParticipants());

        if(itineraryItemService.addItineraryItem(currentItineraryItem)){
            return new ResponseEntity<ItineraryItem>(currentItineraryItem, HttpStatus.OK);
        }
        return new ResponseEntity<ItineraryItem>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Itinerary> deleteItineraryItem(@PathVariable("id") int idItinerary){
        itineraryService.deleteItineraryByItineraryId(idItinerary);
        return new ResponseEntity<Itinerary>(HttpStatus.OK);
    }
}