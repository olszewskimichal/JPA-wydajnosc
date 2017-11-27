package pl.michal.olszewski.jpa.listvsSet.undirectional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import pl.michal.olszewski.jpa.base.Profiles;

@DataJpaTest
@ActiveProfiles(Profiles.TEST)
@RunWith(JUnitPlatform.class)
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
public class PersistTest {

  @Autowired
  protected EntityManager entityManager;

  private Statistics statistics;


  @BeforeEach
  void setUp() {
    Session session = (Session) this.entityManager.getDelegate();
    statistics = session.getSessionFactory().getStatistics();
  }

  @AfterEach
  void tearDown() {
    statistics.clear();
  }

  @Test
  void shouldUse1StatementToPersistEmtpySetEntity() {
    //given
    UndSetEntity setEntity = UndSetEntity.builder().build();
    //when
    entityManager.persist(setEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(1),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(1)
    );
  }

  @Test
  void shouldUse1StatementToPersistEmtpyListEntity() {
    //given
    UndListEntity listEntity = UndListEntity.builder().build();
    //when
    entityManager.persist(listEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(1),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(1)
    );
  }

  @Test
  void shouldUse3StatementToPersistSetEntityWith2Elements() {
    //given
    UndSetEntity setEntity = UndSetEntity.builder().build();
    setEntity.getSet().add(UndSetObject.builder().value1(BigDecimal.ONE).build());
    setEntity.getSet().add(UndSetObject.builder().value1(BigDecimal.TEN).build());
    //when
    entityManager.persist(setEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(5),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(3)
    );
  }

  @Test
  void shouldUse3StatementToPersistListEntityWith2Elements() {
    //given
    UndListEntity listEntity = UndListEntity.builder().list(Arrays.asList(UndListObject.builder().value1(BigDecimal.ONE).build(), UndListObject.builder().value1(BigDecimal.TEN).build())).build();
    //when
    entityManager.persist(listEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(5),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(3)
    );
  }

  @Test
  void shouldUse2StatementToPersistSetEntityWith2TheSameElements() {
    //given
    UndSetEntity setEntity = UndSetEntity.builder().set(new HashSet<>(Arrays.asList(UndSetObject.builder().value1(BigDecimal.ONE).build(), UndSetObject.builder().value1(BigDecimal.ONE).build())))
        .build();
    //when
    entityManager.persist(setEntity);
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(2),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(2)
    );
  }

  @Test
  void shouldUse3StatementToPersistListEntityWith2TheSameElements() {
    //given
    UndListEntity listEntity = UndListEntity.builder().list(Arrays.asList(UndListObject.builder().value1(BigDecimal.ONE).build(), UndListObject.builder().value1(BigDecimal.ONE).build())).build();
    //when
    entityManager.persist(listEntity);
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(3),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(3)
    );
  }

}
