package com.example.demo.service;

import com.example.demo.entity.URLLink;
import com.example.demo.repository.URLLinkRepository;
import com.example.demo.util.ShortFunction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class URLServiceImplTest {
    @Mock
    private URLLinkRepository repository;
    @InjectMocks
    URLServiceImpl service;
    private static MockedStatic<ShortFunction> shortFunctionMockedStatic;

    @Test
    public void isShortenedLinkAlreadyInDB(){
        //given
        Mockito.when(repository.findURLLinkByShortenedLink(anyString())).thenReturn(Optional.of(new URLLink()));
        //when
        boolean result= service.isShortenedLinkAlreadyInDB("temp");
        //then
        assertTrue(result);
        Mockito.verify(repository, Mockito.times(1)).findURLLinkByShortenedLink(anyString());
    }

    @Test
    public void saveURLToDB(){
        //given
        long id=1L;
        String fullLink="http://www.example.com";
        String shortLink="abcdef";
        URLLink urlLink=new URLLink();
        urlLink.setId(id);
        urlLink.setFullLink(fullLink);
        urlLink.setShortenedLink(shortLink);
        Mockito.when(repository.save(any(URLLink.class))).thenReturn(urlLink);
        Mockito.when(repository.findURLLinkByShortenedLink(anyString())).thenReturn(Optional.empty());
        //when
        URLLink saved= service.saveURLToDB(new URLLink());
        //then
        assertEquals(id, saved.getId());
        assertEquals(fullLink, saved.getFullLink());
        assertEquals(shortLink, saved.getShortenedLink());
        Mockito.verify(repository, Mockito.times(1)).save(any(URLLink.class));
        Mockito.verify(repository, Mockito.times(2)).findURLLinkByShortenedLink(anyString());
    }

    @Test
    public void getFullLinkFromDBByShortenedLink(){
        //given
        URLLink urlLink=new URLLink();
        urlLink.setShortenedLink("short");
        String fullLink="fullLink";
        urlLink.setFullLink(fullLink);
        Mockito.when(repository.findURLLinkByShortenedLink(anyString())).thenReturn(Optional.of(urlLink));
        //when
        String resultFullLink= service.getFullLinkFromDBByShortenedLink("short");
        //then
        assertEquals(fullLink, resultFullLink);
        Mockito.verify(repository, Mockito.times(2)).findURLLinkByShortenedLink(anyString());
    }

    @Test
    public void encodeURL(){
        shortFunctionMockedStatic = Mockito.mockStatic(ShortFunction.class);
        shortFunctionMockedStatic.when(ShortFunction::generate).thenReturn("abcdef");
        assertEquals("abcdef", ShortFunction.generate());
        shortFunctionMockedStatic.close();
    }

    @Test
    public void getFormattedURLByShortenedURL(){
        //given
        String shortenedUrl="temporary";
        String baseUrl="localhost:8080/";
        service.setBaseURL(baseUrl);
        //when
        String formattedURL= service.getFormattedURLByShortenedURL(shortenedUrl);
        //then
        assertEquals(baseUrl+shortenedUrl, formattedURL);
    }
}