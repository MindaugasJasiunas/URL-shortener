package com.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class URLLinkTest {
    URLLink urlLink;
    long id=1L;
    String shortenedLink= "shortLink";
    String fullLink= "fullLink";

    @BeforeEach
    void setUp() {
        urlLink=new URLLink();
        urlLink.setId(id);
        urlLink.setShortenedLink(shortenedLink);
        urlLink.setFullLink(fullLink);
    }

    @Test
    void getId() {
        assertEquals(id, urlLink.getId());
    }

    @Test
    void setId() {
        long newId=2L;
        urlLink.setId(newId);
        assertEquals(newId, urlLink.getId());
    }

    @Test
    void getFullLink() {
        assertEquals(fullLink, urlLink.getFullLink());
    }

    @Test
    void setFullLink() {
        String newFullLink="tempo";
        urlLink.setFullLink(newFullLink);
        assertEquals(newFullLink, urlLink.getFullLink());
    }

    @Test
    void getShortenedLink() {
        assertEquals(shortenedLink, urlLink.getShortenedLink());
    }

    @Test
    void setShortenedLink() {
        String newShortenedLink="temp";
        urlLink.setShortenedLink(newShortenedLink);
        assertEquals(newShortenedLink, urlLink.getShortenedLink());
    }

    @Test
    void testToString() {
        assertEquals("URLLink{"+"id=" +id+", fullLink='"+fullLink+'\''+", shortenedLink='"+shortenedLink + '\''+'}', urlLink.toString());
    }
}