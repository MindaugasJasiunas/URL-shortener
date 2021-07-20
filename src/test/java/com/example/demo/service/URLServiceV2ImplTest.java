package com.example.demo.service;

import com.example.demo.entity.URLLink;
import com.example.demo.repository.URLLinkRepository;
import com.example.demo.util.ShortFunction;
import com.example.demo.util.ShortFunctionBase62;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class URLServiceV2ImplTest {
    @Mock
    private URLLinkRepository repository;
    @InjectMocks
    URLServiceV2Impl service;
    private static MockedStatic<ShortFunctionBase62> shortFunctionBase62MockedStatic;

    @Test
    void saveURLToDB() {
        //given
        long id=1L;
        String fullLink="http://www.example.com";
        String shortLink="abcdef";
        URLLink urlLink=new URLLink();
        urlLink.setId(id);
        urlLink.setFullLink(fullLink);
        urlLink.setShortenedLink(shortLink);
        Mockito.when(repository.save(any(URLLink.class))).thenReturn(urlLink);
        shortFunctionBase62MockedStatic= Mockito.mockStatic(ShortFunctionBase62.class);
        String encoded="encodedID";
        shortFunctionBase62MockedStatic.when(() -> ShortFunctionBase62.encode(anyLong())).thenReturn(encoded);
        //when
        URLLink saved= service.saveURLToDB(new URLLink());
        //then
        assertEquals(id, saved.getId());
        assertEquals(fullLink, saved.getFullLink());
        assertEquals(encoded, saved.getShortenedLink());
        Mockito.verify(repository, Mockito.times(2)).save(any(URLLink.class));

        shortFunctionBase62MockedStatic.close();
    }

    @Test
    void getFullLinkFromDBByShortenedLink() {
        //given
        shortFunctionBase62MockedStatic= Mockito.mockStatic(ShortFunctionBase62.class);
        long id=5L;
        shortFunctionBase62MockedStatic.when(() -> ShortFunctionBase62.decode(anyString())).thenReturn(id);

        String fullLink="fullLink";
        URLLink urlLink=new URLLink();
        urlLink.setFullLink(fullLink);
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(urlLink));
        //when
        String result= service.getFullLinkFromDBByShortenedLink("temp");
        //then
        assertEquals(fullLink, result);

        shortFunctionBase62MockedStatic.close();
    }

    @DisplayName("getFullLinkFromDBByShortenedLink() invalid input - returned -1")
    @Test
    void getFullLinkFromDBByShortenedLink_invalidId() {
        //given
        shortFunctionBase62MockedStatic= Mockito.mockStatic(ShortFunctionBase62.class);
        shortFunctionBase62MockedStatic.when(() -> ShortFunctionBase62.decode(anyString())).thenReturn(-1L);
        //when
        String result= service.getFullLinkFromDBByShortenedLink("temp");
        //then
        assertNull(result);

        shortFunctionBase62MockedStatic.close();
    }

    @Test
    void getFormattedURLByShortenedURL() {
        //given
        String baseUrl="http://localhost:8080/";
        String shortenedUrl="1K";
        service.setBaseURL(baseUrl);
        //when
        String result= service.getFormattedURLByShortenedURL(shortenedUrl);
        //then
        assertEquals(baseUrl+shortenedUrl, result);
    }
}