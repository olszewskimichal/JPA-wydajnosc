package pl.michal.olszewski.jpa.mappedBy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.michal.olszewski.jpa.base.EntityBase;

@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Setter
class MappedObject extends EntityBase {

  @ManyToOne(fetch = FetchType.LAZY)
  TestMappedByEntity object;

}
