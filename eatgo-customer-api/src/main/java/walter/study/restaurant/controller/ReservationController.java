package walter.study.restaurant.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import walter.study.restaurant.application.ReservationService;
import walter.study.restaurant.domain.Reservation;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Reservation resource
                                    ) throws URISyntaxException {

        Claims claims = (Claims) authentication.getPrincipal();

        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);
        String date  = resource.getDate();
        String time = resource.getTime();
        Integer partySize = resource.getPartySize();


        Reservation reservation = reservationService.addReservation(restaurantId, userId, name, date, time, partySize);

        String url = "/restaurants/"+restaurantId+"/reservations/"+reservation.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
