-- Criar tabela de usuários
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL
);

-- Inserir usuários de teste
INSERT INTO users (username, password, name, email, role) VALUES
('admin', '$2a$10$KTZtP0xAa46ga9IwMWJ2ou5.udAp8T1HLrxsUuUo8HsLmY9lIoSwS', 'Administrador', 'admin@med9.com', 'ADMIN'),
('doctor', '$2a$10$KTZtP0xAa46ga9IwMWJ2ou5.udAp8T1HLrxsUuUo8HsLmY9lIoSwS', 'Dr. João Silva', 'doctor@med9.com', 'DOCTOR'),
('nurse', '$2a$10$KTZtP0xAa46ga9IwMWJ2ou5.udAp8T1HLrxsUuUo8HsLmY9lIoSwS', 'Enfermeira Maria', 'nurse@med9.com', 'NURSE'),
('patient', '$2a$10$KTZtP0xAa46ga9IwMWJ2ou5.udAp8T1HLrxsUuUo8HsLmY9lIoSwS', 'Paciente José', 'patient@med9.com', 'PATIENT');