package sapi.com.learningrxjava.model;

public class Student {

    public String name;
    public String email;
    public int age;
    public String registrationDate;

    public Student() {
    }

    public Student(String name, String email, int age, String registrationDate) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.registrationDate = registrationDate;
    }
}
