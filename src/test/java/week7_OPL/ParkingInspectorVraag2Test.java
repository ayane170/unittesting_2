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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingInspectorVraag2Test {
    private static final String LICENSE_PLATE = "ABC-123";
    private static final ParkingZone PARKING_ZONE_ANT1 = ParkingZone.ANT1;
    // now() is niet meer bruikbaar, anders faalt de test in het weekend
    private static final LocalDateTime FRIDAY = LocalDateTime.of(2025,10,31, 15, 14, 13);
    private static final LocalDateTime SATURDAY = LocalDateTime.of(2025,11,1, 15, 14, 13);
    private static final String PERSON_ADDRESS = "personAddress";
    private static final String PERSON_NAME = "personName";

    @InjectMocks
    ParkingInspectorVraag2 parkingInspector;

    @Mock
    SubscriptionService subscriptionService;
    @Mock
    AccountingService accountingService;
    @Mock
    PersonService personService;

    @Test
    void validateParkedVehicle_hasActiveSubscription_returnsEmpty() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(true);
        
        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, FRIDAY, PARKING_ZONE_ANT1);

        assertTrue(result.isEmpty());
    }

    @Test
    void validateParkedVehicle_hasActiveParkingTicket_returnsEmpty() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(false);
        when(accountingService.hasActiveParkingTicket(LICENSE_PLATE)).thenReturn(true);

        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, FRIDAY, PARKING_ZONE_ANT1);

        assertTrue(result.isEmpty());
    }

    @Test
    void validateParkedVehicle_personIsIllegallyParked_returnsTicket() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(false);
        when(accountingService.hasActiveParkingTicket(LICENSE_PLATE)).thenReturn(false);
        when(personService.findVehicleOwner(LICENSE_PLATE)).thenReturn(new Person(PERSON_NAME, PERSON_ADDRESS));

        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, FRIDAY, PARKING_ZONE_ANT1);

        assertFalse(result.isEmpty());
        assertEquals(new ParkingTicket(PARKING_ZONE_ANT1.name(), LICENSE_PLATE, PERSON_NAME, PERSON_ADDRESS, FRIDAY, PARKING_ZONE_ANT1.getFine())
                    , result.get());
    }

    @Test
    void validateParkedVehicle_parkedOnANT1DuringWeekend_returnsTicket() {
        when(personService.findVehicleOwner(LICENSE_PLATE)).thenReturn(new Person(PERSON_NAME, PERSON_ADDRESS));

        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, SATURDAY, PARKING_ZONE_ANT1);

        assertFalse(result.isEmpty());
        assertEquals(new ParkingTicket(PARKING_ZONE_ANT1.name(), LICENSE_PLATE, PERSON_NAME, PERSON_ADDRESS, SATURDAY, PARKING_ZONE_ANT1.getFine())
                , result.get());

        // Om er zeker van te zijn dat we de subscriptionService en accountingService niet aanroepen in onze code:
        verifyNoInteractions( subscriptionService, accountingService);
    }

    @Test
    void validateParkedVehicle_parkedOnANT4OrANT5DuringWeek_returnsEmpty() {
        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, FRIDAY, ParkingZone.ANT4);
        assertTrue(result.isEmpty());
        result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, FRIDAY, ParkingZone.ANT5);
        assertTrue(result.isEmpty());

        // Om er zeker van te zijn dat we de subscriptionService en accountingService niet aanroepen in onze code:
        verifyNoInteractions( subscriptionService, accountingService);
    }

    @Test
    void validateParkedVehicle_parkedOnANT4OrANT5DuringWeekend_returnsTicketIfNotPayed() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(false);
        when(accountingService.hasActiveParkingTicket(LICENSE_PLATE)).thenReturn(false);
        when(personService.findVehicleOwner(LICENSE_PLATE)).thenReturn(new Person(PERSON_NAME, PERSON_ADDRESS));

        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, SATURDAY, ParkingZone.ANT4);

        assertFalse(result.isEmpty());
        assertEquals(new ParkingTicket(ParkingZone.ANT4.name(), LICENSE_PLATE, PERSON_NAME, PERSON_ADDRESS, SATURDAY, ParkingZone.ANT4.getFine())
                , result.get());

        result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, SATURDAY, ParkingZone.ANT5);

        assertFalse(result.isEmpty());
        assertEquals(new ParkingTicket(ParkingZone.ANT5.name(), LICENSE_PLATE, PERSON_NAME, PERSON_ADDRESS, SATURDAY, ParkingZone.ANT5.getFine())
                , result.get());
    }

    @Test
    void validateParkedVehicle_parkedOnANT4OrANT5DuringWeekend_returnsEmptyIfSubscription() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(true);

        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, SATURDAY, ParkingZone.ANT4);
        assertTrue(result.isEmpty());

        result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, SATURDAY, ParkingZone.ANT5);
        assertTrue(result.isEmpty());
    }

    @Test
    void validateParkedVehicle_parkedOnANT4OrANT5DuringWeekend_returnsEmptyIfPayed() {
        when(subscriptionService.hasActiveSubscription(LICENSE_PLATE)).thenReturn(false);
        when(accountingService.hasActiveParkingTicket(LICENSE_PLATE)).thenReturn(true);

        Optional<ParkingTicket> result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, SATURDAY, ParkingZone.ANT4);
        assertTrue(result.isEmpty());

        result = parkingInspector.validateParkedVehicle(LICENSE_PLATE, SATURDAY, ParkingZone.ANT5);
        assertTrue(result.isEmpty());
    }
}