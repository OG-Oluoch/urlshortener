package com.spring.urlshortener.service;

import com.spring.urlshortener.entity.ShortUrl;
import com.spring.urlshortener.repository.UrlRepository;
import com.spring.urlshortener.utils.ShortCodeGenerator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private static final int MAX_RETRIES = 10;


    public UrlShortenerService(UrlRepository urlRepository, ShortCodeGenerator shortCodeGenerator) {
        this.urlRepository = urlRepository;
        this.shortCodeGenerator = shortCodeGenerator;
    }

    /**
     * Create a short url for the given original url
     * if the url already exists, return the existing short url
     * otherwise, generate a new short url and save it to the database
     */
    public ShortUrl createShortUrl(String originalUrl){

        //check if url exists
        Optional<ShortUrl> urlExists = urlRepository.findByOriginalUrl(originalUrl);
        if(urlExists.isPresent()){

            return urlExists.get();

        }

        //generate a unique short url
        String shortUrl = generateUniqueShortUrl();

        //create and save new short url
        ShortUrl newShortUrl = new ShortUrl();
        newShortUrl.setOriginalUrl(originalUrl);
        newShortUrl.setShortUrl(shortUrl);

        return urlRepository.save(newShortUrl);


    }

    /**
     *Generate a unique short code by handling potential collisions
     *
     *returns a unique short code that does not exist in the database
     */

    private String generateUniqueShortUrl() {

        int attempts = 0;
        String shortCode;

        do{
            shortCode = shortCodeGenerator.generate();
            attempts++;

            if(attempts>MAX_RETRIES){

                throw new RuntimeException("Failed to generate unique short code after "+ MAX_RETRIES+" attempts");


            }

        } while (urlRepository.findByShortUrl(shortCode).isPresent());

        return shortCode;
    }

    /**
     * retrieve the original url for a given short code
      *return Optional containing the original URL if found, empty otherwise
     */

    @Transactional
    public Optional<String> getOriginalUrl(String shortCode){

        return urlRepository.findByShortUrl(shortCode)
                .map(ShortUrl::getOriginalUrl);

    }


}
