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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  @Id
  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany
  private List<Subcategory> subcategories = new ArrayList<>();
}
