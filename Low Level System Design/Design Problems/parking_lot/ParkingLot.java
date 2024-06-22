package system_design.parking_lot;

import system_design.parking_lot.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class ParkingLot {
    private final List<ParkingSpot> spots;

    public ParkingLot(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    private static PaymentReceipt getPaymentReceipt(ParkingTicket parkingTicket) {
        // this will be generated based on time parked and charge per hour
        // we can also add additional charges like parking tax, etc.
        LocalDateTime currentTime = LocalDateTime.now().plusMinutes(32);
        LocalDateTime parkedTime = parkingTicket.getIssueTime();
        double hoursParked = (currentTime.toEpochSecond(ZoneOffset.UTC) - parkedTime.toEpochSecond(ZoneOffset.UTC)) / 3600d;
        double amount = Math.round(hoursParked * parkingTicket.getChargePerHour());
        return new PaymentReceipt(
                amount,
                parkingTicket
        );
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : getAvailableSpots()) {
            if (vehicle.canFitInSpot(spot)) {
                spot.setParkedVehicle(vehicle);
                spot.setAvailable(false);

                // generate parking ticket
                return getParkingTicket(vehicle, spot);
            }
        }
        return null;
    }

    private ParkingTicket getParkingTicket(Vehicle vehicle, ParkingSpot spot) {
        ParkingTicket parkingTicket = new ParkingTicket();
        String ticketNumber = UUID.randomUUID().toString();
        parkingTicket.setTicketNumber(ticketNumber);
        parkingTicket.setVehicle(vehicle);
        parkingTicket.setParkingSpot(spot);
        parkingTicket.setChargePerHour(vehicle.getChargePerHour());
        return parkingTicket;
    }

    public PaymentReceipt removeVehicle(ParkingTicket parkingTicket) {
        ParkingSpot spot = parkingTicket.getParkingSpot();
        spot.setParkedVehicle(null);
        spot.setAvailable(true);

        // generate payment receipt
        return getPaymentReceipt(parkingTicket);
    }

    public List<ParkingSpot> getAvailableSpots() {
        return spots.stream().filter(ParkingSpot::isAvailable).toList();
    }

    public void printParkedVehicles() {
        for (ParkingSpot spot : spots) {
            if (!spot.isAvailable()) {
                System.out.println(spot.getParkedVehicle() + " is parked in spot " + spot);
            }
        }
    }
}
