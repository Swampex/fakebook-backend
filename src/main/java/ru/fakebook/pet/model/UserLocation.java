package ru.fakebook.pet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "town", column = @Column(name = "user_town")),
        @AttributeOverride(name = "country", column = @Column(name = "user_country")),
})

public class UserLocation {
    private String town;
    private String country;
}