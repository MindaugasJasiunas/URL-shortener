package com.example.demo.service;

import com.example.demo.entity.URLLink;

public interface URLService {
//    boolean isShortenedLinkAlreadyInDB(String shortenedLink);
    URLLink saveURLToDB(URLLink urlLink);
    String getFullLinkFromDBByShortenedLink(String shortenedLink);
    String getFormattedURLByShortenedURL(String shortenedURL);

}
