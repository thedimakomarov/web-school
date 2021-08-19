package com.komarov.webschool.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TeacherDto {

    @Null(message = "should be null")
    private Long id;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String firstName;

    @NotNull(message = "should be not null")
    @Pattern(regexp = "(?m)^[^0-9_]{2,}$", message = "should be not empty, have at least 2 characters, not contain numbers or '_'")
    private String lastName;

    @Pattern(regexp = "\\d{9}", message = "should have 9 characters")
    private String mobile;

    public TeacherDto(Long id, String firstName, String lastName, String mobile) {
        this.id = id;
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.mobile = mobile;
    }
}
