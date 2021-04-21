package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.Reservation;
import walter.study.restaurant.domain.ReservationRepository;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;


    public List<Reservation> getReservations(long restaurantId) {

        return reservationRepository.findAllByRestaurantId(restaurantId);
    }
}
