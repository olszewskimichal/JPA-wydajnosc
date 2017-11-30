package pl.michal.olszewski.jpa.nproblem;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.michal.olszewski.jpa.base.EntityBase;

@Entity
@EqualsAndHashCode(callSuper = true, exclude = "object")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class SelectObject2 extends EntityBase {
  private Boolean value;

  @ManyToOne(fetch = FetchType.LAZY)
  SelectProblemEntity object;
}
