INSERT INTO `users` (password, enabled, name, email) VALUES ('$2a$10$9aUL.eyvqcjg.WGxR.5Po.EaliyBRVL2/Zx/5DUv5.CAYQgL8gkfK',1, 'Osmar Velezmoro','admin@mail.com');
INSERT INTO `users` (password, enabled, name, email) VALUES ('$2a$10$RK8NK/uTgH12PywrzqgbGuayapuAfowsT0fEeQm2.lAZw2Au2x1KG',1, 'Usuario Generico','usere@mail.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `user_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `user_roles` (user_id, role_id) VALUES (2, 2);