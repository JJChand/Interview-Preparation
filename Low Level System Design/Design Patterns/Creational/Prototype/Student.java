package design_patterns.Creational.Prototype;

public class Student implements Prototype {

  private String name;
  private int age;
  private int rollNo;

  public Student(String name, int age, int rollNo) {
    this.name = name;
    this.age = age;
    this.rollNo = rollNo;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public int getRollNo() {
    return rollNo;
  }

  @Override
  public Student clone() {
    return new Student(this.name, this.age, this.rollNo);
  }
}
