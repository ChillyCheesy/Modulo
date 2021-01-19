package fr.owle.hometracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
public class StaticController {

    @GetMapping("favicon.ico")
    public byte[] icon() throws IOException {
        return Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("favicon.ico")).readAllBytes();
    }

}
