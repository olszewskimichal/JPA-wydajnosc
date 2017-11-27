package pl.michal.olszewski.jpa.base;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class EntityBase {

  @GeneratedValue
  @Id
  private Long id;
}
