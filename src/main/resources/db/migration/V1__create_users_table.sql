-- Criar tabela de usuários
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL
);

-- Limpar dados existentes
TRUNCATE TABLE users CASCADE;

-- Inserir usuários de teste
-- Senha: 123456 (BCrypt hash)
INSERT INTO users (username, password, name, email, role) VALUES
('admin', '$2a$10$3vQzLFZzWGnFxYWBn6WAyOoXB8ZCPQF1QJxhq.kWK1RwUYIrgK9Uy', 'Administrador', 'admin@med9.com', 'ADMIN'),
('doctor', '$2a$10$3vQzLFZzWGnFxYWBn6WAyOoXB8ZCPQF1QJxhq.kWK1RwUYIrgK9Uy', 'Dr. João Silva', 'doctor@med9.com', 'DOCTOR'),
('nurse', '$2a$10$3vQzLFZzWGnFxYWBn6WAyOoXB8ZCPQF1QJxhq.kWK1RwUYIrgK9Uy', 'Enfermeira Maria', 'nurse@med9.com', 'NURSE'),
('patient', '$2a$10$3vQzLFZzWGnFxYWBn6WAyOoXB8ZCPQF1QJxhq.kWK1RwUYIrgK9Uy', 'Paciente José', 'patient@med9.com', 'PATIENT'); 