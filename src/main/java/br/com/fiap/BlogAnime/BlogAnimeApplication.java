package br.com.fiap.BlogAnime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.fiap.BlogAnime")
public class BlogAnimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAnimeApplication.class, args);
    }
}
