package com.example.demo.service;

import com.example.demo.entity.URLLink;
import com.example.demo.repository.URLLinkRepository;
import com.example.demo.util.ShortFunction;
import com.example.demo.util.ShortFunctionBase62;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:app.properties")
@Service
public class URLServiceV2Impl implements URLService {
    private final URLLinkRepository repository;
    @Value("${application.baseURL}")
    private String baseURL;

    public URLServiceV2Impl(URLLinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public URLLink saveURLToDB(URLLink urlLink){
        URLLink urlLinkSaved= repository.save(urlLink);
        urlLinkSaved.setShortenedLink(encodeURL(urlLinkSaved.getId())); //encode by ID
        return repository.save(urlLinkSaved);
    }

    @Override
    public String getFullLinkFromDBByShortenedLink(String shortenedLink){
        long id= decodeURL(shortenedLink);
        if(id==-1){ //error
            return null;
        }
        if(repository.findById(id).isPresent()){
            return repository.findById(id).get().getFullLink();
        }
        return null;
    }

    private String encodeURL(long id){
        return ShortFunctionBase62.encode(id);
    }
    private long decodeURL(String encodedId){
        return ShortFunctionBase62.decode(encodedId);
    }

    @Override
    public String getFormattedURLByShortenedURL(String shortenedURL){
        return baseURL+shortenedURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
}
