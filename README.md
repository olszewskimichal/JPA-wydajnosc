# JPA-wydajnosc

Testy sprawdzające jak wydajnie tworzyć encje / pisać zapytania z wykorzystaniem JPA i Hibernate.

### Problemy poruszone dotychczas w testach
* czy lepiej używać S
* czy warto uzywać mappedBy
* n+1 selectów


### Jak to jest sprawdzane
Uzywam statystyk Hibernate jako dowodu do tego ile rzeczywiście zapytań zostało wykonanych.
```
application.properties
spring.jpa.properties.hibernate.generate_statistics=true
```
```
  @BeforeEach
  void setUp() {
    session = (Session) this.entityManager.getDelegate();
    statistics = session.getSessionFactory().getStatistics();
  }
```
