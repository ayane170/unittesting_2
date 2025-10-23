package week7.mocking;

import java.time.LocalDateTime;
import java.util.Optional;

public class ParkingInspector {
    private final SubscriptionService subscriptionService;
    private final AccountingService accountingService;
    private final PersonService personService;

    public ParkingInspector(SubscriptionService subscriptionService, AccountingService accountingService, PersonService personService) {
        this.subscriptionService = subscriptionService;
        this.accountingService = accountingService;
        this.personService = personService;
    }

    public Optional<ParkingTicket> validateParkedVehicle(String licensePlate, LocalDateTime inspectionTime, ParkingZone location) {
        if (subscriptionService.hasActiveSubscription(licensePlate)) return Optional.empty();
        if (accountingService.hasActiveParkingTicket(licensePlate)) return Optional.empty();

        Person owner = personService.findVehicleOwner(licensePlate);

        return Optional.of(new ParkingTicket(location.name(), licensePlate, owner.getName(), owner.getAddress(), inspectionTime, location.getFine()));
    }
}
