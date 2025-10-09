package org.skillswap.skillswapbackend.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<UserDTO, User> userDTOToUserConverter = new AbstractConverter<UserDTO, User>() {
            @Override
            protected User convert(UserDTO userDTO) {
                User user = new User();
                user.setId(userDTO.getId());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setPassword(userDTO.getPassword());
                user.setRole(userDTO.getRole());
                return user;
            }
        };

        modelMapper.addConverter(userDTOToUserConverter);

        return modelMapper;
    }
}
