package org.fillUsIn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {

  @Id
  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "subcategory")
  @OrderBy("voteCount DESC")
  private List<Post> posts = new ArrayList<>();

  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonIgnore
  private Category category;
}
