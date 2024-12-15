package org.fillUsIn.dto.mapper;

import org.fillUsIn.dto.PostDTO;
import org.fillUsIn.dto.PostSummaryDTO;
import org.fillUsIn.entity.Post;
import org.fillUsIn.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = { CommentMapper.class })
public interface PostMapper {

  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

  @Mapping(source = "subcategory.name", target = "subcategoryName")
  @Mapping(source = "subcategory.category.name", target = "categoryName")
  @Mapping(source = "user.username", target = "username")
  PostDTO postToPostDTO(Post post);

  @Mapping(source = "subcategory.name", target = "subcategoryName")
  @Mapping(source = "subcategory.category.name", target = "categoryName")
  @Mapping(source = "user.username", target = "username")
  @Mapping(target = "commentCount", ignore = true)
  PostSummaryDTO postToPostSummaryDTO(Post post);

  List<PostSummaryDTO> postsToPostSummaryDTOs(List<Post> posts);

  @AfterMapping
  default void afterMappingSummary(Post post, @MappingTarget PostSummaryDTO postSummaryDTO) {
    postSummaryDTO.setCommentCount(post.getComments().size());
  }

  @AfterMapping
  default void mapUserListsToUsernameLists(Post post, @MappingTarget PostDTO postDto) {
    postDto.setCommentCount(post.getComments().size());

    if (post.getUserLikes() != null) {
      postDto.setUserLikesUsernames(post.getUserLikes().stream().map(User::getUsername).collect(Collectors.toList()));
    }
    if (post.getUserDislikes() != null) {
      postDto.setUserDislikesUsernames(post.getUserDislikes().stream().map(User::getUsername).collect(Collectors.toList()));
    }
  }
}