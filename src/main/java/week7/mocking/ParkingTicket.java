package week7.mocking;

import java.time.LocalDateTime;

public record ParkingTicket(String parkedLocation, String licensePlate, String ownerName, String ownerAddress, LocalDateTime ticketDate, double amount) {
}
