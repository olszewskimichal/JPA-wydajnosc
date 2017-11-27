package pl.michal.olszewski.jpa.listvsSet.bidirectional;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.jpa.base.EntityBase;

@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListEntity extends EntityBase {

  @OneToMany(
      mappedBy = "listEntity",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  List<ListObject> list;

  public void addObject(ListObject object) {
    if (list == null) {
      list = new ArrayList<>();
    }
    list.add(object);
    object.setListEntity(this);
  }

  public void removeObject(ListObject object) {
    if (list == null) {
      list = new ArrayList<>();
    } else {
      list.remove(object);
      object.setListEntity(null);
    }
  }
}
