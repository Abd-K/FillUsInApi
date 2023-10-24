package org.fillUsIn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  @Id
  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "category",
          cascade = {MERGE, DETACH, REFRESH, REMOVE})
  private List<Subcategory> subcategories = new ArrayList<>();
}
