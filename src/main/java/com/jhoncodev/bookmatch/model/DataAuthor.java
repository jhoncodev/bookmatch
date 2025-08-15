package com.jhoncodev.bookmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAuthor(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer birthdate,
        @JsonAlias("death_year") Integer deathdate
) {}
