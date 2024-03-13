INSERT INTO auth_token (id, token) VALUES (1,'43bad51d-f40f-42e5-ba0f-5c65ba3f48a9');
INSERT INTO auth_token (id, token) VALUES (2,'e65f54b7-0e4d-47c6-98ab-edf328d0db26');
INSERT INTO auth_token (id, token) VALUES (3,'c3c4bd97-bac2-4c09-969c-93b96f5d5f02');
INSERT INTO auth_token (id, token) VALUES (4,'ae3f00e1-b406-49f4-915f-b625c22e62e5');

INSERT INTO users (id, username, password, role, auth_token_id) VALUES (1,'admin', 'admin','ROLE_ADMIN',1);
INSERT INTO users (id, username, password, role, auth_token_id) VALUES (2,'posts', 'posts','ROLE_POSTS',2);
INSERT INTO users (id, username, password, role, auth_token_id) VALUES (3,'users', 'users','ROLE_USERS',3);
INSERT INTO users (id, username, password, role, auth_token_id) VALUES (4,'almubs', 'almubs','ROLE_ALBUMS',4);