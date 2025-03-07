package design_patterns.behavioural.observer;

public class TemperatureObserver implements IObserver {
    ISubject subject;

    TemperatureObserver(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public void update() {
        System.out.println("Temperature: " + subject.getTemperature());
    }
}
