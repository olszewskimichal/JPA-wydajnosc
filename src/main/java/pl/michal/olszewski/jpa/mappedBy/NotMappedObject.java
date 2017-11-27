package pl.michal.olszewski.jpa.mappedBy;

import javax.persistence.Entity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.jpa.base.EntityBase;

@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
class NotMappedObject extends EntityBase {


}
