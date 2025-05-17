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
('admin', 'senha123', 'Administrador', 'admin@med9.com', 'ADMIN'),
('doctor', 'senha123', 'Dr. João Silva', 'doctor@med9.com', 'DOCTOR'),
('nurse', 'senha123', 'Enfermeira Maria', 'nurse@med9.com', 'NURSE'),
('patient', 'senha123', 'Paciente José', 'patient@med9.com', 'PATIENT');