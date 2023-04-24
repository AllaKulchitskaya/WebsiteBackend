package team.skyprojava.websitebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import team.skyprojava.websitebackend.dto.CommentDto;
//import team.skyprojava.websitebackend.entity.Comment;

@Mapper
@Component
public interface CommentMapper{

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "author.id")
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    <Comment>
    Comment toEntity(CommentDto commentDto);

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(target = "authorImage", expression = "java(comment.getAuthor().getUserImage().getId().toString())")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", ignore = true)
    <Comment>
    CommentDto toDto(Comment comment);
}
