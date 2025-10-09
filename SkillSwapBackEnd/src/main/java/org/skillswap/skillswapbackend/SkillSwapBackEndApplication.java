package org.skillswap.skillswapbackend;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.dto.UserDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SkillSwapBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillSwapBackEndApplication.class, args);
    }

}