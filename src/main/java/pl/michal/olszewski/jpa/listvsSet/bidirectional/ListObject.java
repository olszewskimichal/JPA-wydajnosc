package pl.michal.olszewski.jpa.listvsSet.bidirectional;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.michal.olszewski.jpa.base.EntityBase;

@Entity
@EqualsAndHashCode(callSuper = true, exclude = "listEntity")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class ListObject extends EntityBase {

  private BigDecimal value1;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "list_id")
  private ListEntity listEntity;

}
