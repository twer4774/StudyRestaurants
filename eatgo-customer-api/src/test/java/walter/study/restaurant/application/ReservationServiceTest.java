package walter.study.restaurant.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import walter.study.restaurant.domain.Reservation;
import walter.study.restaurant.domain.ReservationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ReservationServiceTest {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    public void addReservation(){

        Long restaurantId = 369L;
        Long userId = 1004L;
        String name = "John";
        String date  = "2021-04-21";
        String time = "20:00";
        Integer partySize = 20;


        given(reservationRepository.save(any())).will(inovation -> {
            Reservation reservation = inovation.getArgument(0);
            return reservation;
        });


       Reservation reservation = reservationService.addReservation(restaurantId, userId, name, date, time, partySize);

       assertEquals(reservation.getName(), name);

       verify(reservationRepository).save(any());

    }

}