## JPA (Java Persistence API)

### Darbo principai

- Vieną kartą sukurti EntityManagerFactory ([singleton](https://www.baeldung.com/java-singleton))
- Kiekvienas Thread arba kiekviena užklausa turi gauti savo EntityManager

## Naudojant failą: persistence.xml

Ką būtina nurodyti:

- Išvardinti visas klases kurios turi @Entity anotaciją;
- Nurodyti prisijungimus prie DB (driver, url, user, password);
- Nurodyti hibernate kokia naudojama DB versija (dialect);

Papildomi parametrai:

- Galima nurodyti kad hibernate išvestų į konsolę generuojamus sql sakinius;
- Galima nurodyti ar hibernate tikrina java modelio atitikimą duomenų bazės lentelėms.
  Daugiau info: https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#configurations-hbmddl

### Naudinga info

- https://thoughts-on-java.org/hibernate-getting-started/
- https://thoughts-on-java.org/jpa-persistence-xml/
- https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html