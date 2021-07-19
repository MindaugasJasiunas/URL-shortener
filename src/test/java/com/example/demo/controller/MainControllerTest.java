package com.example.demo.controller;

import com.example.demo.configuration.SpringApplicationConfiguration;
import com.example.demo.entity.URLLink;
import com.example.demo.service.URLService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {
    private MockMvc mockMvc;
    @Mock
    private URLService service;
    @InjectMocks
    private MainController controller;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getIndexPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("shorten"))
                .andExpect(model().attributeExists("urlLink"));
    }


    @Test
    void redirectToFullLink() throws Exception {
        final ArgumentCaptor<String> stringArgumentCaptor=ArgumentCaptor.forClass(String.class);
        String shortenedLink="abc";
        Mockito.when(service.getFullLinkFromDBByShortenedLink(stringArgumentCaptor.capture())).thenReturn(shortenedLink);

        String link="temporary";
        this.mockMvc.perform(get("/{shortenedUrl}", link))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(shortenedLink));

        assertEquals(link, stringArgumentCaptor.getValue());
        Mockito.verify(service, Mockito.times(1)).getFullLinkFromDBByShortenedLink(anyString());
    }

    @DisplayName("redirectToFullLink() non existent short URL - return index page")
    @Test
    void redirectToFullLink_nonExistentShortURL() throws Exception {
        final ArgumentCaptor<String> stringArgumentCaptor=ArgumentCaptor.forClass(String.class);
        Mockito.when(service.getFullLinkFromDBByShortenedLink(stringArgumentCaptor.capture())).thenReturn(null);

        String link="temporary";
        this.mockMvc.perform(get("/{shortenedUrl}", link))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        assertEquals(link, stringArgumentCaptor.getValue());
        Mockito.verify(service, Mockito.times(1)).getFullLinkFromDBByShortenedLink(anyString());
    }

    @Test
    void shortenUrl() throws Exception {
        Mockito.when(service.saveURLToDB(any(URLLink.class))).thenReturn(new URLLink());
        Mockito.when(service.getFormattedURLByShortenedURL(nullable(String.class))).thenReturn("temp");

        this.mockMvc.perform(post("/shortenURL")
                        .param("fullLink", "http://www.example.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shortenedURL"))
                .andExpect(view().name("shortened"));

        Mockito.verify(service, Mockito.times(1)).saveURLToDB(any(URLLink.class));
        Mockito.verify(service, Mockito.times(1)).getFormattedURLByShortenedURL(nullable(String.class));

    }

    @DisplayName("shortenUrl() shortened link already in DB")
    @Test
    void shortenUrl_shortLinkInDBAlready() throws Exception {
        Mockito.when(service.saveURLToDB(any(URLLink.class))).thenReturn(null);

        this.mockMvc.perform(post("/shortenURL")
                .param("fullLink", "http://www.example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("shorten"))
                .andExpect(model().attributeHasFieldErrors("urlLink", "fullLink"));

        Mockito.verify(service, Mockito.times(1)).saveURLToDB(any(URLLink.class));
        Mockito.verify(service, Mockito.never()).getFormattedURLByShortenedURL(nullable(String.class));
    }

//  MockMvc doesn't work with BindingResult errors
    @DisplayName("shortenUrl() invalid full link entered")
    @Disabled
    @Test
    void shortenUrl_invalidFullLink() throws Exception {
        this.mockMvc.perform(post("/shortenURL")
                .param("fullLink", "www.example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("shorten"))
                .andExpect(model().attributeHasFieldErrors("urlLink", "fullLink"));

        Mockito.verify(service, Mockito.never()).saveURLToDB(any(URLLink.class));
        Mockito.verify(service, Mockito.never()).getFormattedURLByShortenedURL(nullable(String.class));
    }
}