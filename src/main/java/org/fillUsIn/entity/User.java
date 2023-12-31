package org.fillUsIn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "email", unique = true)
  private String email;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updatedDate")
  private LocalDateTime updatedDate;

  @Column(name = "password")
  @JsonIgnore
  private String password;

  @OneToMany(
          mappedBy = "user",
          cascade = {MERGE, REFRESH},
          fetch = FetchType.LAZY)
  @OrderBy("voteCount DESC")
  @JsonIgnore
  private List<Post> posts = new ArrayList<>();

  @OneToMany(
          mappedBy = "user",
          cascade = {MERGE, REFRESH},
          fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Comment> comments = new ArrayList<>();

  @ManyToMany(mappedBy = "userLikes",
          cascade = {MERGE, DETACH, REFRESH})
  @JsonIgnore
  private List<Post> postLikes = new ArrayList<>();


  @ManyToMany(mappedBy = "userDislikes",
          cascade = {MERGE, DETACH, REFRESH})
  @JsonIgnore
  private List<Post> postDislikes = new ArrayList<>();
}
