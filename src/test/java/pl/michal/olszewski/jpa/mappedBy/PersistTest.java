package pl.michal.olszewski.jpa.mappedBy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
  void shouldUse5StatementToPersistNotMappedByListWith2Elements() {
    //given
    TestMappedByEntity listEntity = TestMappedByEntity.builder().build();
    listEntity.getList().add(new NotMappedObject());
    listEntity.getList().add(new NotMappedObject());
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
  void shouldUse3StatementToPersistMappedByListWith2Elements() {
    //given
    TestMappedByEntity listEntity = TestMappedByEntity.builder().build();
    listEntity.addObject(new MappedObject());
    listEntity.addObject(new MappedObject());
    //when
    entityManager.persist(listEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(3),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(3)
    );
  }

  @Test
  void shouldUse5StatementToPersistJoinNotMappedListWith2Elements() {
    //given
    TestMappedByEntity listEntity = TestMappedByEntity.builder().build();
    listEntity.getJoinList().add(new JoinMappedObject());
    listEntity.getJoinList().add(new JoinMappedObject());
    //when
    entityManager.persist(listEntity);
    entityManager.flush();
    //then
    assertAll(
        () -> assertThat(statistics.getPrepareStatementCount()).isEqualTo(5),
        () -> assertThat(statistics.getEntityInsertCount()).isEqualTo(3)
    );
  }

}
