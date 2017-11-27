package pl.michal.olszewski.jpa.listvsSet.bidirectional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.logging.Logger;
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


@DataJpaTest
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
    SetEntity setEntity = SetEntity.builder().build();
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
    ListEntity listEntity = ListEntity.builder().build();
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
  void shouldUse4StatementToPersistSetEntityWith3Elements() {
    //given
    SetEntity setEntity = SetEntity.builder().build();
    setEntity.addObject(SetObject.builder().value1(BigDecimal.ONE).build());
    setEntity.addObject(SetObject.builder().value1(BigDecimal.TEN).build());
    setEntity.addObject(SetObject.builder().value1(BigDecimal.ZERO).build());
    //when
    entityManager.persist(setEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(4),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(4)
    );
  }

  @Test
  void shouldUse4StatementToPersistListEntityWith3Elements() {
    //given
    ListEntity listEntity = ListEntity.builder().build();
    listEntity.addObject(ListObject.builder().value1(BigDecimal.ONE).build());
    listEntity.addObject(ListObject.builder().value1(BigDecimal.TEN).build());
    listEntity.addObject(ListObject.builder().value1(BigDecimal.ZERO).build());
    //when
    entityManager.persist(listEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(4),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(4)
    );
  }

  @Test
  void shouldUse2StatementToPersistSetEntityWith2TheSameElements() {
    //given
    SetEntity setEntity = SetEntity.builder().build();
    setEntity.addObject(SetObject.builder().value1(BigDecimal.ONE).build());
    setEntity.addObject(SetObject.builder().value1(BigDecimal.ONE).build());
    //when
    entityManager.persist(setEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(2),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(2)
    );
  }

  @Test
  void shouldUse3StatementToPersistListEntityWith2TheSameElements() {
    //given
    ListEntity listEntity = ListEntity.builder().build();
    listEntity.addObject(ListObject.builder().value1(BigDecimal.ONE).build());
    listEntity.addObject(ListObject.builder().value1(BigDecimal.ONE).build());
    //when
    entityManager.persist(listEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(3),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(3)
    );
  }

}
