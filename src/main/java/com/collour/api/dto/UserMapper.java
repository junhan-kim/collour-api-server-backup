package com.collour.api.dto;

import com.collour.api.domain.User;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "birth", ignore = true)
    @Mapping(target = "major", ignore = true)
    @Mapping(target = "photoUri", ignore = true)
    @Mapping(target = "teamList", ignore = true)
    @Mapping(target = "chatRoomList", ignore = true)
    @Mapping(target = "chatMessageList", ignore = true)
    User toUser(UserDTO userDTO, @Context PasswordEncoder bcryptEncoder);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "photoUri", ignore = true)
    UserDTO toUserDTO(User user);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "birth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "major", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "photoUri", ignore = true)
    @Mapping(target = "teamList", ignore = true)
    @Mapping(target = "chatRoomList", ignore = true)
    @Mapping(target = "chatMessageList", ignore = true)
    void updateUser(UserDTO userDTO, @MappingTarget User user, @Context PasswordEncoder bcryptEncoder);

    @BeforeMapping
    default void beforeUserMapping(UserDTO userDTO, @Context PasswordEncoder bcryptEncoder) {
        if (userDTO.getPassword() != null) userDTO.setPassword(bcryptEncoder.encode(userDTO.getPassword()));
    }


}
