package org.fillUsIn.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
  private List<Post> posts = new ArrayList<>();

  @ManyToOne
  @JoinColumn(nullable = false)
  private Category category;
}
