-- src/main/resources/import.sql
INSERT INTO livro (titulo, autor, editora, ano, disponivel, quantidade, isbn) VALUES ('1984', 'George Orwell', 'Secker and Warburg', 1949, true, 10, '1234567890');
INSERT INTO livro (titulo, autor, editora, ano, disponivel, quantidade, isbn) VALUES ('To Kill a Mockingbird', 'Harper Lee', 'J.B. Lippincott & Co.', 1960, true, 8, '1234567891');

ALTER TABLE livro ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM livro) + 1;


INSERT INTO usuario (id, nome, email, senha, tipo_usuario) VALUES (1,'João Silva', 'joao.silva@example.com', '$2a$10$NljmOv7yytCgn2MOkD0Otun8b.6bWj3Wxq1SUbhvRdUYBYjKqeudm', 'Estudante');
INSERT INTO usuario (id, nome, email, senha, tipo_usuario) VALUES (2,'Maria Oliveira', 'maria.oliveira@example.com', '$2a$10$NljmOv7yytCgn2MOkD0Otun8b.6bWj3Wxq1SUbhvRdUYBYjKqeudm', 'Professor');
INSERT INTO usuario (id, nome, email, senha, tipo_usuario) VALUES (3,'Carlos Pereira', 'carlos.pereira@example.com', '$2a$10$NljmOv7yytCgn2MOkD0Otun8b.6bWj3Wxq1SUbhvRdUYBYjKqeudm', 'Administrador');
INSERT INTO usuario (id, nome, email, senha, tipo_usuario) VALUES (4,'Ana Costa', 'ana.costa@example.com', '$2a$10$NljmOv7yytCgn2MOkD0Otun8b.6bWj3Wxq1SUbhvRdUYBYjKqeudm', 'Estudante');
INSERT INTO usuario (id, nome, email, senha, tipo_usuario) VALUES (5,'Luiz Santos', 'luiz.santos@example.com', '$2a$10$NljmOv7yytCgn2MOkD0Otun8b.6bWj3Wxq1SUbhvRdUYBYjKqeudm', 'Professor');

ALTER TABLE usuario ALTER COLUMN id RESTART WITH (SELECT MAX(id) FROM usuario) + 1;