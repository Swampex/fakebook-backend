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
        @AttributeOverride(name = "photo_small", column = @Column(name = "photo_small")),
        @AttributeOverride(name = "photo_large", column = @Column(name = "photo_large"))
})
public class Photos {

    @Column(length = 1000)
    private String photo_large;

    @Column(length = 1000)
    private String photo_small;
}
