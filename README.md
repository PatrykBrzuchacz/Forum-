# SpringProject for PGS

nazwa bazy: mojabaza
username=root
password=password

#Projekt służy do rejestracji użytkowników, każdy z użytkowników może dodawać tematy oraz posty, 
  dodajemy komunikat do bazy danych  przed stworzeniem innych uzytkownikow w celu dostepu do panelu admina, potem mozemy tworzyc uzytkownikow
"
insert into user_role(role) VALUES ("ROLE_ADMIN");
insert into user(email,password) VALUES ("Admin","$2a$10$xqyfP/i76NAJNSseQCIvXuQb8j90WGYjicirsbl55J1Iz1Wc5.9TG");
insert into user_roles(user_id,roles_id) Values(1,1)
"
login: admin     haslo: qwerty   - moze on edytowac uzytkownikow,usuwac ich
                                                        na co zabraklo czasu : estetyczny wygląd, edycja tematow,postow, lepsze filtrowanie, wiecej testow m.in. dla walidacji
