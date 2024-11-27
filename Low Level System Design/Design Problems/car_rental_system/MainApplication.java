package system_design.car_rental_system;

import system_design.car_rental_system.billing.Billing;
import system_design.car_rental_system.exception.VehicleNotAvailableException;
import system_design.car_rental_system.location.Location;
import system_design.car_rental_system.payment.Payment;
import system_design.car_rental_system.payment.PaymentStatus;
import system_design.car_rental_system.payment.PaymentType;
import system_design.car_rental_system.reservation.Reservation;
import system_design.car_rental_system.store.VehicleStore;
import system_design.car_rental_system.user.User;
import system_design.car_rental_system.user.UserType;
import system_design.car_rental_system.vehicle.Vehicle;
import system_design.car_rental_system.vehicle.VehicleStatus;
import system_design.car_rental_system.vehicle.VehicleType;

public class MainApplication {

  public static void main(String[] args) {
    CarRentalService carRentalService = new CarRentalService();

    User user = new User(1L, "John", "john@gmail.com", "1234567890", "123, New Street, New York",
        "DL123", "password", UserType.CUSTOMER);

    VehicleStore store = new VehicleStore(1L, "Store1", "123, New Street, New York", "New York",
        "New York", "123456", "USA", true);

    Vehicle vehicle = new Vehicle(1L, 1L, "KA01AB1234", "Toyota", 4, VehicleType.CAR,
        VehicleStatus.AVAILABLE, 20.0, null);

    carRentalService.addUser(user);
    carRentalService.addStore(store);
    carRentalService.addVehicle(store, vehicle);

    try {
      Reservation reservation = carRentalService.makeReservation(user, vehicle, 1L, 1L, new Location(1L, "Shahbadd"),
          new Location(2L, "Shahpura"));

      Billing billing = carRentalService.generateBill(reservation);
      Payment payment = carRentalService.processPayment(billing, PaymentType.UPI);

      if (payment.getStatus() == PaymentStatus.COMPLETED) {
        billing.confirmPayment();
        reservation.confirmReservation();
      }

      // pick up vehicle
      carRentalService.pickUpVehicle(reservation, vehicle);

      Billing dueBill = carRentalService.getDueBill(vehicle, reservation, true, 1000.50, System.currentTimeMillis());
      if (dueBill != null) {
        carRentalService.processPayment(dueBill, PaymentType.CASH);
      }

      carRentalService.dropVehicle(vehicle);

      // make vehicle available
      carRentalService.markVehicleAvailable(vehicle);
    } catch (VehicleNotAvailableException e) {
      System.out.println(e.getMessage());
    }
  }
}
