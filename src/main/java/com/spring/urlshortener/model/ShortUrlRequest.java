package com.spring.urlshortener.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlRequest {


    @NotBlank(message = "URL cannot be blank")
    @URL(message = "Invalid URL format")
    private String url;

}
