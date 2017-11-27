package pl.michal.olszewski.jpa.listvsSet.undirectional;

import java.math.BigDecimal;
import javax.persistence.Entity;
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
@Setter
@Getter
class UndSetObject extends EntityBase {

  private BigDecimal value1;

}
