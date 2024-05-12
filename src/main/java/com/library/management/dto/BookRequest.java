package com.library.management.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private Long id;

    @NotEmpty(message = "Title is required")
    private String title;
    @NotEmpty(message = "Author is required")
    private String author;
    @NotNull(message = "Publication year is required")
    private int publicationYear;
    @NotNull(message = "Publication year is required")
    private String isbn;

    private boolean availableForBorrow;
}
