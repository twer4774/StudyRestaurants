package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.Reservation;
import walter.study.restaurant.domain.ReservationRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation addReservation(Long restaurantId, Long userId, String name, String date, String time, Integer partySize) {


        Reservation build = Reservation.builder()
                .restaurantId(restaurantId)
                .userId(userId)
                .name(name)
                .date(date)
                .time(time)
                .partySize(partySize)
                .build();

        return reservationRepository.save(build);
    }
}
