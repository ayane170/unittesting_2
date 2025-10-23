package week7.mocking;

public enum ParkingZone {

    ANT1(2.5, 10.0),
    ANT2(3.5, 15.0),
    ANT3(4.5, 20.0),
    ANT4(5.5, 25.0),
    ANT5(6.5, 30.0);

    private final double price;
    private final double fine;

    ParkingZone(double price, double fine) {
        this.price = price;
        this.fine = fine;
    }

    public double getPrice() {
        return price;
    }
    public double getFine() {
        return fine;
    }


}
