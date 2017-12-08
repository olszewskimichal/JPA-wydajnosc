package pl.michal.olszewski.jpa.mappedBy;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.jpa.base.EntityBase;


@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TestMappedByEntity extends EntityBase {

  @OneToMany(cascade = CascadeType.ALL)
  @Builder.Default
  List<NotMappedObject> list = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "object")
  @Builder.Default
  List<MappedObject> mappedList = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "join_id")
  @Builder.Default
  List<JoinMappedObject> joinList = new ArrayList<>();

  public void addObject(MappedObject object) {
    mappedList.add(object);
    object.setObject(this);
  }

}
