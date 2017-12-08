package pl.michal.olszewski.jpa.listvsSet.undirectional;

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
public class UndSetEntity extends EntityBase {

  @OneToMany(cascade = CascadeType.ALL)
  private Set<UndSetObject> set;

  public Set<UndSetObject> getSet() {
    if (set == null) {
      set = new HashSet<>();
    }
    return set;
  }
}
