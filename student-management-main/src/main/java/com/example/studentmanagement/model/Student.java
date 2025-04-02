package com.example.studentmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ім'я не може бути порожнім")
    @Size(min = 2, max = 50, message = "Ім'я повинно містити від 2 до 50 символів")
    private String name;

    @Min(value = 16, message = "Вік повинен бути не менше 16 років")
    @Max(value = 100, message = "Вік не може перевищувати 100 років")
    private int age;
}
