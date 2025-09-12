package org.skillswap.skillswapbackend.config;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOToUserConverter implements Converter<UserDTO, User> {

    @Override
    public User convert(MappingContext<UserDTO, User> context) {
        UserDTO source = context.getSource();
        User destination = context.getDestination() != null ? context.getDestination() : new User();

        destination.setId(source.getId());
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setEmail(source.getEmail());
        destination.setPassword(source.getPassword());
        


        return destination;
    }
}
