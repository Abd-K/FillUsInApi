package org.fillUsIn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updatedDate")
  private LocalDateTime updatedDate;
  private String body;
  private int voteCount;

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonIgnore
  private User user;

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonIgnore
  private Post post;

  @ManyToOne
  @JoinColumn(name = "parent_comment_id")
  @JsonIgnore
  private Comment parentComment;

  @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
  private List<Comment> replyComments = new ArrayList<>();

  @ManyToMany
  @JoinTable(
          name = "comment_userlikes",
          joinColumns = @JoinColumn(name = "comment_id"),
          inverseJoinColumns = @JoinColumn(name = "username")
  )
  private List<User> userLikes;

  @ManyToMany
  @JoinTable(
          name = "comment_userdislikes",
          joinColumns = @JoinColumn(name = "comment_id"),
          inverseJoinColumns = @JoinColumn(name = "username")
  )
  private List<User> userDislikes;
}
