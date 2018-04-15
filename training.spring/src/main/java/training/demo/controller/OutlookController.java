package training.demo.controller;

import com.outlook.dev.auth.AuthHelper;
import com.outlook.dev.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.demo.model.Itinerary;
import training.demo.model.ItineraryItem;
import training.demo.model.Location;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import com.outlook.dev.auth.AuthHelper;
import com.outlook.dev.auth.IdToken;
import com.outlook.dev.auth.TokenResponse;
import training.demo.model.User;
import training.demo.service.ItineraryItemJpaService;
import training.demo.service.UserJpaService;

@RestController
@RequestMapping(value = "/api/outlook",
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class OutlookController {

    private String tokenHeader = "Authorization";

    private MyHttpSession myHttpSession = null;

    @Autowired
    ItineraryItemJpaService itineraryItemService;
    @Autowired
    UserJpaService userService;

    @RequestMapping(
            value = "/geturl",
            method = RequestMethod.GET)
    public ResponseEntity<MyLoginUrl> getUrl(HttpServletRequest request) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        // Save the state and nonce in the session so we can
        // verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);

        MyLoginUrl myLoginUrl = new MyLoginUrl(loginUrl);

        return new ResponseEntity<MyLoginUrl>(myLoginUrl, HttpStatus.OK);
    }

    @RequestMapping(value="/authorize",
            method=RequestMethod.POST)
    public ResponseEntity<MyHttpSession> authorize(
            @RequestParam("code") String code,
            @RequestParam("id_token") String idToken,
            @RequestParam("state") UUID state,
            HttpServletRequest request) {
        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

        // Make sure that the state query parameter returned matches
        // the expected state
        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
                myHttpSession.getSession().setAttribute("tokens", tokenResponse);
                myHttpSession.getSession().setAttribute("userConnected", true);
                myHttpSession.getSession().setAttribute("userName", idTokenObj.getName());
                // Get user info
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
                OutlookUser user;
                try {
                    user = outlookService.getCurrentUser().execute().body();
                    myHttpSession.getSession().setAttribute("userEmail", "alina.pirlog@stud.acs.upb.ro");
                } catch (IOException e) {
                    session.setAttribute("error", e.getMessage());
                }
                myHttpSession.getSession().setAttribute("userTenantId", idTokenObj.getTenantId());
            } else {
                session.setAttribute("error", "ID token failed validation.");
            }
            myHttpSession = new MyHttpSession(session);

            TokenResponse tokens = (TokenResponse)myHttpSession.getSession().getAttribute("tokens");


            String tenantId = (String)myHttpSession.getSession().getAttribute("userTenantId");
            System.out.println("=========================================================================");
            tokens = AuthHelper.ensureTokens(tokens, tenantId);

            String email = (String)myHttpSession.getSession().getAttribute("userEmail");

            OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

            // Sort by start time in descending order
            String sort = "start/dateTime DESC";
            // Only return the properties we care about
            String properties = "organizer,subject,start,end";
            // Return at most 10 events
            Integer maxResults = 10;

            try {
                PagedResult<Event> events = outlookService.getEvents(
                        sort, properties, maxResults)
                        .execute().body();
                System.out.println("=================="+events.toString());
                Event[] events1 = events.getValue();
                User user = userService.findUserById(6);
                for (Event ev:events1) {
                    ItineraryItem itineraryItem = new ItineraryItem();
                    itineraryItem.setOrganiser(user);
                    itineraryItem.setStartDate(ev.getStart().getDateTime());
                    itineraryItem.setStartDate(ev.getEnd().getDateTime());
                    itineraryItemService.addItineraryItem(itineraryItem);
                }

            } catch (IOException e) {
                System.out.print(e.toString());
            }

            return new ResponseEntity<MyHttpSession>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<MyHttpSession>(HttpStatus.NOT_FOUND);
        }
    }

    // inner class
    private class MyLoginUrl{
        private String loginUrl;

        public MyLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }
    }

    private class MyHttpSession{
        private HttpSession session;

        public MyHttpSession(HttpSession session) {
            this.session = session;
        }

        public HttpSession getSession() {
            return session;
        }

        public void setSession(HttpSession session) {
            this.session = session;
        }
    }
}