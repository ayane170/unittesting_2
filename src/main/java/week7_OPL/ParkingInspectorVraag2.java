package week7_OPL;

import week7.mocking.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class ParkingInspectorVraag2 {
    private final SubscriptionService subscriptionService;
    private final AccountingService accountingService;
    private final PersonService personService;

    public ParkingInspectorVraag2(SubscriptionService subscriptionService, AccountingService accountingService, PersonService personService) {
        this.subscriptionService = subscriptionService;
        this.accountingService = accountingService;
        this.personService = personService;
    }

    public Optional<ParkingTicket> validateParkedVehicle(String licensePlate, LocalDateTime inspectionTime, ParkingZone location) {
        if (parkingAnt1InWeekend(location, inspectionTime)) {
            return generateTicket(licensePlate, inspectionTime, location);
        }
        if (parkingFreeLocationsDuringWeek(location, inspectionTime)) {
            return Optional.empty();
        }
        if (subscriptionService.hasActiveSubscription(licensePlate)) return Optional.empty();
        if (accountingService.hasActiveParkingTicket(licensePlate)) return Optional.empty();

        return generateTicket(licensePlate, inspectionTime, location);
    }

    private boolean parkingFreeLocationsDuringWeek(ParkingZone location, LocalDateTime inspectionTime) {
        return (location == ParkingZone.ANT4 || location == ParkingZone.ANT5) && !isWeekend(inspectionTime);
    }

    private boolean parkingAnt1InWeekend(ParkingZone location, LocalDateTime inspectionTime) {
        return location == ParkingZone.ANT1 && isWeekend(inspectionTime);
    }

    private static boolean isWeekend(LocalDateTime inspectionTime) {
        return inspectionTime.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || inspectionTime.getDayOfWeek() == java.time.DayOfWeek.SUNDAY;
    }

    private Optional<ParkingTicket> generateTicket(String licensePlate, LocalDateTime inspectionTime, ParkingZone location) {
        Person owner = personService.findVehicleOwner(licensePlate);

        return Optional.of(new ParkingTicket(location.name(), licensePlate, owner.getName(), owner.getAddress(), inspectionTime, location.getFine()));
    }
}
