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
        @AttributeOverride(name = "url_github", column = @Column(name = "url_github")),
        @AttributeOverride(name = "url_vk", column = @Column(name = "url_vk")),
        @AttributeOverride(name = "url_facebook", column = @Column(name = "url_facebook")),
        @AttributeOverride(name = "url_instagram", column = @Column(name = "url_instagram"))
})
public class Contacts {

    private String url_github;
    private String url_vk;
    private String url_facebook;
    private String url_instagram;
}
