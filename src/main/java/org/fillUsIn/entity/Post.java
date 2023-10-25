package org.fillUsIn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_voteCount", columnList = "voteCount")})  // This line creates an index on voteCount
public class Post {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  private String title;
  private String body;
  private int voteCount;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updatedDate")
  private LocalDateTime updatedDate;

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonIgnore
  private User user;

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonIgnore
  private Subcategory subcategory;
  private String url;
  private String thumbnailUrl;

  @ManyToMany
  @JsonIgnore
  @JoinTable(
          name = "post_topic",
          joinColumns = @JoinColumn(name = "post_id"),
          inverseJoinColumns = @JoinColumn(name = "topic_id")
  )
  private List<Topic> topics;

  @ManyToMany
  @JoinTable(
          name = "post_userlikes",
          joinColumns = @JoinColumn(name = "post_id"),
          inverseJoinColumns = @JoinColumn(name = "username")
  )
  private List<User> userLikes;

  @ManyToMany
  @JoinTable(
          name = "post_userdislikes",
          joinColumns = @JoinColumn(name = "post_id"),
          inverseJoinColumns = @JoinColumn(name = "username")
  )
  private List<User> userDislikes;
}
