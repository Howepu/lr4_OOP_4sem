package com.example.lr4_oop_4sem;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private String name;
    private String surname;
    private String group;
    private int age;
    private String subject;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", group='" + group + '\'' +
                ", age=" + age +
                ", subject='" + subject + '\'' +
                '}';
    }
}
