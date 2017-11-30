package pl.michal.olszewski.jpa.nproblem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.annotations.QueryHints;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@RunWith(JUnitPlatform.class)
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
public class SelectTest {

  @Autowired
  protected EntityManager entityManager;

  private Statistics statistics;

  private Session session;

  @BeforeEach
  void setUp() {
    session = (Session) this.entityManager.getDelegate();
    statistics = session.getSessionFactory().getStatistics();
  }

  @AfterEach
  void tearDown() {
    statistics.clear();
  }

  @Test
  void shouldUse2SelectAndAnother1PerObjectCollectionWithoutFetch() {  //uzywa dodatkowego selecta dla kazdej z kolekcji danego obiektu
    //given
    SelectProblemEntity entity = SelectProblemEntity.builder().build();
    entity.addObject(SelectObject.builder().value(BigDecimal.ONE).build());
    entity.addObject(SelectObject.builder().value(BigDecimal.TEN).build());
    SelectProblemEntity entity2 = SelectProblemEntity.builder().build();
    entity2.addObject(SelectObject.builder().value(BigDecimal.ZERO).build());
    entity2.addObject(SelectObject.builder().value(BigDecimal.valueOf(3L)).build());
    entityManager.persist(entity);
    entityManager.persist(entity2);
    entityManager.flush();
    entityManager.clear();
    statistics.clear();
    //when
    List<SelectProblemEntity> list = entityManager.createQuery("select s from SelectProblemEntity s", SelectProblemEntity.class).getResultList();
    list.forEach(v -> v.list.size());
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(3),
        () -> assertThat(statistics.getEntityLoadCount()).isEqualTo(6)
    );
  }

  @Test
  void shouldUse1SelectWithFetch() {
    //given
    SelectProblemEntity entity = SelectProblemEntity.builder().build();
    entity.addObject(SelectObject.builder().value(BigDecimal.TEN).build());
    entity.addObject(SelectObject.builder().value(BigDecimal.ONE).build());
    SelectProblemEntity entity2 = SelectProblemEntity.builder().build();
    entity2.addObject(SelectObject.builder().value(BigDecimal.ZERO).build());
    entity2.addObject(SelectObject.builder().value(BigDecimal.valueOf(4)).build());
    entityManager.persist(entity);
    entityManager.persist(entity2);
    entityManager.flush();
    entityManager.clear();
    statistics.clear();
    //when
    List<SelectProblemEntity> list = entityManager.createQuery("select s from SelectProblemEntity s left join fetch s.list", SelectProblemEntity.class).getResultList();
    list.forEach(v -> v.list.size());
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(1),
        () -> assertThat(statistics.getEntityLoadCount()).isEqualTo(6)
    );
  }

  @Test
  void shouldUse1SelectWith2FetchCollections() {
    //given
    SelectProblemEntity entity = SelectProblemEntity.builder().build();
    entity.addObject(SelectObject.builder().value(BigDecimal.TEN).build());
    entity.addObject(SelectObject.builder().value(BigDecimal.ZERO).build());
    entity.addObject(SelectObject2.builder().value(Boolean.FALSE).build());
    SelectProblemEntity entity2 = SelectProblemEntity.builder().build();
    entity2.addObject(SelectObject.builder().value(BigDecimal.ONE).build());
    entity2.addObject(SelectObject.builder().value(BigDecimal.valueOf(3)).build());
    entity.addObject(SelectObject2.builder().value(Boolean.TRUE).build());
    entityManager.persist(entity);
    entityManager.persist(entity2);
    entityManager.flush();
    entityManager.clear();
    statistics.clear();
    //when
    List<SelectProblemEntity> list = entityManager.createQuery("select s from SelectProblemEntity s left join fetch s.list left join fetch s.anotherList", SelectProblemEntity.class).getResultList();
    list.forEach(v -> v.list.size());
    list.forEach(v -> v.anotherList.size());
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(1),
        () -> assertThat(statistics.getEntityLoadCount()).isEqualTo(8)
    );
  }

  @Test
  void shouldUse2SelectWith2FetchCollections() {
    //given
    SelectProblemEntity entity = SelectProblemEntity.builder().build();
    entity.addObject(SelectObject.builder().value(BigDecimal.TEN).build());
    entity.addObject(SelectObject.builder().value(BigDecimal.ZERO).build());
    entity.addObject(SelectObject2.builder().value(Boolean.FALSE).build());
    SelectProblemEntity entity2 = SelectProblemEntity.builder().build();
    entity2.addObject(SelectObject.builder().value(BigDecimal.ONE).build());
    entity2.addObject(SelectObject.builder().value(BigDecimal.valueOf(3)).build());
    entity.addObject(SelectObject2.builder().value(Boolean.TRUE).build());
    entityManager.persist(entity);
    entityManager.persist(entity2);
    entityManager.flush();
    entityManager.clear();
    statistics.clear();
    //when
    List<SelectProblemEntity> list = entityManager.createQuery("select s from SelectProblemEntity s left join fetch s.list", SelectProblemEntity.class).getResultList();
    entityManager.createQuery("select s from SelectProblemEntity s left join fetch s.anotherList", SelectProblemEntity.class).getResultList();
    list.forEach(v -> v.list.size());
    list.forEach(v -> v.anotherList.size());
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(2),
        () -> assertThat(statistics.getEntityLoadCount()).isEqualTo(8)
    );
  }

}
