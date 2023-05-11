package team.skyprojava.websitebackend.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import team.skyprojava.websitebackend.dto.RegisterReqDto;
import team.skyprojava.websitebackend.dto.UserDto;
import team.skyprojava.websitebackend.entity.User;

/**
 * Предоставляет методы для маппинга User to Dto`s
 */
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userImage", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "email", source = "username")
    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userImage", ignore = true)
    User toEntity(RegisterReqDto registerReqDto);

    @Mapping(target = "image", expression = "java(\"/users/\" + user.getId() + \"/image\")")
    UserDto toDto(User user);
}