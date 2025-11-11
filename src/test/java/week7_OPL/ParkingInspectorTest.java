package week7_OPL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import week7.mocking.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingInspectorTest {
    private static final String LICENSE_PLATE = "ABC-123";
    private static final ParkingZone PARKING_ZONE_ANT1 = ParkingZone.ANT1;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final String PERSON_ADDRESS = "personAddress";
    private static final String PERSON_NAME = "personName";
    @InjectMocks
    ParkingInspector parkingInspector;

    @Mock
    SubscriptionService subscriptionService;
    @Mock
    AccountingService accountingService;
    @Mock
    PersonService personService;

    @Test
    void validateParkedVehicle_hasActiveSubscription_returnsEmpty() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(true);
        
        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, NOW, PARKING_ZONE_ANT1);

        assertTrue(result.isEmpty());
    }

    @Test
    void validateParkedVehicle_hasActiveParkingTicket_returnsEmpty() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(false);
        when(accountingService.hasActiveParkingTicket(LICENSE_PLATE)).thenReturn(true);

        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, NOW, PARKING_ZONE_ANT1);

        assertTrue(result.isEmpty());
    }

    @Test
    void validateParkedVehicle_personIsIllegallyParked_returnsTicket() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(false);
        when(accountingService.hasActiveParkingTicket(LICENSE_PLATE)).thenReturn(false);
        when(personService.findVehicleOwner(LICENSE_PLATE)).thenReturn(new Person(PERSON_NAME, PERSON_ADDRESS));

        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, NOW, PARKING_ZONE_ANT1);

        assertFalse(result.isEmpty());
        assertEquals(new ParkingTicket(PARKING_ZONE_ANT1.name(), LICENSE_PLATE, PERSON_NAME, PERSON_ADDRESS, NOW, PARKING_ZONE_ANT1.getFine())
                    , result.get());
    }
}