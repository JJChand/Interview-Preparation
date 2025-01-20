package design_patterns.SolidPrinciples.LiskovSubstitutePrinciple.Solution;

public class Car implements EngineVehicle {

  @Override
  public boolean hasEngine() {
    return true;
  }

  @Override
  public int noOfWheels() {
    return 4;
  }
}
