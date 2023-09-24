INSERT INTO roles (id, role_name, description)
VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO roles (id, role_name, description)
VALUES (2, 'ROLE_STANDARD', 'USER');

insert into Users (name, email, password, active)
values ('usuario Juan Perez 1', 'usuario1@server.com', '$2a$12$UJgVmRVKUqeOxruQOE8Lw.BbAYOBTG0U6NOTQCsH3/5tjxkTLnCLm',
        true); --pass1
insert into Users (name, email, password, active)
values ('usuario Juan Perez 2', 'usuario2@server.com', '$2a$12$9XM0Uw168DMGdVGOK5LF7u927gYi9v8QCGoAvM.9xXA4DDktKLKgm',
        false); -- pass2
insert into Users (name, email, password, active)
values ('usuario Juan Perez 3', 'usuario3@server.com', '$2a$12$IW1RtXflz9Zq8WDzh4Otx./50GEvzzx24q/WdJGS6ji/Q.pn4wfC2',
        true); -- pass3
insert into Users (name, email, password, active)
values ('Juan Rodriguez', 'juan@rodriguez.org', '$2a$12$hyApiRPkrPSiHQfYpGhsBuhNTAISS4RrpWmr.NggLM2FmcbRUTpJy',
        true); -- hunter2

insert into Phones(country_code, city_code, number, user_id)
values ('57', '1', '1234567', (select id from users where email = 'juan@rodriguez.org'))
