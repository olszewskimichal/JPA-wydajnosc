package pl.michal.olszewski.jpa.nproblem;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.michal.olszewski.jpa.base.EntityBase;

@Entity
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = "object",callSuper = true)
public class SelectObject extends EntityBase {

  private BigDecimal value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "object_id")
  SelectProblemEntity object;
}
