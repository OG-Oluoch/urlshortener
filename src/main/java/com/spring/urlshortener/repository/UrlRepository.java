package com.spring.urlshortener.repository;

import com.spring.urlshortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<ShortUrl,Long> {

    Optional<ShortUrl> findByShortUrl(String shortUrl);
    Optional<ShortUrl> findByOriginalUrl(String originalUrl);
}
