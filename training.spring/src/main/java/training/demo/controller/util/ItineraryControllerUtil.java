package training.demo.controller.util;

import training.demo.model.Itinerary;
import training.demo.model.ItineraryItem;
import training.demo.model.User;
import training.demo.service.ItineraryItemJpaService;

import java.util.List;

public class ItineraryControllerUtil {

    public void saveItineraryItems(User organiser,
                                   ItineraryItemJpaService service,
                                   Itinerary itinerary,
                                   List<ItineraryItem> itineraryItemList) {

        if (itineraryItemList == null) {
            return;
        }

        for (ItineraryItem item: itineraryItemList) {
            item.setOrganiser(organiser);
            item.setItinerary(itinerary);
            service.addItineraryItem(item);
            
        }

        itinerary.setItineraryItems(itineraryItemList);
    }
}