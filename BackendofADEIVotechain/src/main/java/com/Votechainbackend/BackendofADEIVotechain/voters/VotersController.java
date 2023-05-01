package com.Votechainbackend.BackendofADEIVotechain.voters;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This is a REST API Controller
@RequestMapping(path = "api/v1/voters")
public class VotersController {

    @GetMapping
    public String getVoters() {
        return List.of(
                new VotersModel(
                        1L,
                        "John",
                        "john.smith@gmail.com",
                        "password",
                        "1234567890"
                ));
    }

}
