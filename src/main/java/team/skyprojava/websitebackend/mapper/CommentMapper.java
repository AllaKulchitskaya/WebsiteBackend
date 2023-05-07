package team.skyprojava.websitebackend.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import team.skyprojava.websitebackend.dto.CommentDto;
import team.skyprojava.websitebackend.entity.Comment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {LocalDateTime.class, ZoneOffset.class})
public interface CommentMapper{

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentDto commentDto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorImage", expression = "java(\"/users/\" + comment.getAuthor().getId() + \"/image\")")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now().toInstant(ZoneOffset.ofHours(3)).toEpochMilli())")
    CommentDto toDto(Comment comment);
}