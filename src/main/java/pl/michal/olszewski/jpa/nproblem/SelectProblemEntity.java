package pl.michal.olszewski.jpa.nproblem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.jpa.base.EntityBase;



@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SelectProblemEntity extends EntityBase {

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "object")
  @Builder.Default
  Set<SelectObject> list = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "object")
  @Builder.Default
  Set<SelectObject2> anotherList = new HashSet<>();

  public void addObject(SelectObject object) {
    list.add(object);
    object.setObject(this);
  }

  public void addObject(SelectObject2 object) {
    anotherList.add(object);
    object.setObject(this);
  }
}
