package pl.michal.olszewski.jpa.listvsSet.bidirectional;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.michal.olszewski.jpa.base.EntityBase;

@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SetEntity extends EntityBase {

  @OneToMany(
      mappedBy = "setEntity",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<SetObject> set;

  public void addObject(SetObject object) {
    if (set == null) {
      set = new HashSet<>();
    }
    set.add(object);
    object.setSetEntity(this);
  }

  public void removeObject(SetObject object) {
    if (set == null) {
      set = new HashSet<>();
    } else {
      set.remove(object);
      object.setSetEntity(null);
    }
  }
}
