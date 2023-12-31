package org.fillUsIn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {

  @Id
  @Column(nullable = false, unique = true)
  private String name;

  private String thumbnailUrl;
  private String description;

  @OneToMany(
          mappedBy = "subcategory",
          cascade = {MERGE, DETACH, REFRESH},
          fetch = FetchType.LAZY)
  @OrderBy("voteCount DESC")
  private List<Post> posts = new ArrayList<>();

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonIgnore
  private Category category;

  @ManyToMany
  @JsonIgnore
  @JoinTable(
          name = "subcategory_topic",
          joinColumns = @JoinColumn(name = "subcategory_name"),
          inverseJoinColumns = @JoinColumn(name = "topic_id")
  )
  private List<Topic> topics;
}
