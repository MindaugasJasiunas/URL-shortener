package com.example.demo.service;

import com.example.demo.entity.URLLink;
import com.example.demo.repository.URLLinkRepository;
import com.example.demo.util.ShortFunction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:app.properties")
@Service
public class URLServiceImpl implements URLService {
    private final URLLinkRepository repository;
    @Value("${application.baseURL}")
    private String baseURL;

    public URLServiceImpl(URLLinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isShortenedLinkAlreadyInDB(String shortenedLink){
        if(shortenedLink!=null){
            if(repository.findURLLinkByShortenedLink(shortenedLink).isPresent()){
                return true;
            }
        }
        return false;
    }

    @Override
    public URLLink saveURLToDB(URLLink urlLink){
        //encode full link
        urlLink.setShortenedLink(encodeURL());

        if(isShortenedLinkAlreadyInDB(urlLink.getShortenedLink())){
            return null;
        }
        return repository.save(urlLink);
    }

    @Override
    public String getFullLinkFromDBByShortenedLink(String shortenedLink){
        if(isShortenedLinkAlreadyInDB(shortenedLink)){
            return repository.findURLLinkByShortenedLink(shortenedLink).get().getFullLink();
        }
        return null;
    }

    @Override
    public String encodeURL(){
        String randomString = ShortFunction.generate();
        while(isShortenedLinkAlreadyInDB(randomString)){
            randomString = ShortFunction.generate();
        }
        return randomString;
    }

    @Override
    public String getFormattedURLByShortenedURL(String shortenedURL){
        return baseURL+shortenedURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
}
