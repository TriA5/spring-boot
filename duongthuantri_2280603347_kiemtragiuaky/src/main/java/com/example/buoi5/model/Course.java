package com.example.buoi5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Course name is required")
    private String name;

    private String image;

    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    private Integer credits;

    @NotBlank(message = "Lecturer is required")
    private String lecturer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
