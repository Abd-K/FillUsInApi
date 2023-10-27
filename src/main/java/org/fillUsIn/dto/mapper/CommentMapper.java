package org.fillUsIn.dto.mapper;

import org.fillUsIn.dto.CommentDTO;
import org.fillUsIn.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;


@Mapper
public interface CommentMapper {
  CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
  CommentDTO commentToCommentDTO(Comment comment);

  default List<CommentDTO> commentListToCommentDTOList(List<Comment> comments) {
    if (comments == null) {
      return null;
    }
    return comments.stream()
            .map(this::commentToCommentDTO)
            .collect(Collectors.toList());
  }
}