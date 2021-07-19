package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="url_link")
public class URLLink {
    @Id
    @SequenceGenerator( name = "url_link_sequence", sequenceName = "url_link_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "url_link_sequence")
    @Column(name = "id")
    private Long id;
    @Column(name = "full_link")
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)", message = "Check provided url and try again. Dont forget http or https protocol at the beginning.")
    String fullLink;
    @Column(unique = true, name = "shortened_link")
    String shortenedLink;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullLink() {
        return fullLink;
    }

    public void setFullLink(String fullLink) {
        this.fullLink = fullLink;
    }

    public String getShortenedLink() {
        return shortenedLink;
    }

    public void setShortenedLink(String shortenedLink) {
        this.shortenedLink = shortenedLink;
    }

    @Override
    public String toString() {
        return "URLLink{" +
                "id=" + id +
                ", fullLink='" + fullLink + '\'' +
                ", shortenedLink='" + shortenedLink + '\'' +
                '}';
    }
}
