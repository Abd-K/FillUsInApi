package org.fillUsIn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;

  @Column(nullable = false)
  private String title;

  @ManyToMany(mappedBy = "topics",
          cascade = {MERGE, DETACH, REFRESH, PERSIST})
  private List<Subcategory> subcategories = new ArrayList<>();

  @ManyToMany(mappedBy = "topics",
          cascade = {MERGE, DETACH, REFRESH, PERSIST})
  private List<Post> pickedPosts = new ArrayList<>();

}
