-- Criar tabela de médicos
CREATE TABLE IF NOT EXISTS doctors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    crm VARCHAR(50) NOT NULL UNIQUE,
    specialty VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Criar tabela de pacientes
CREATE TABLE IF NOT EXISTS patients (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Inserir médico de teste
INSERT INTO doctors (name, crm, specialty, phone, email, user_id)
SELECT 'Dr. João Silva', 'CRM/SP 123456', 'Clínico Geral', '11999999999', 'doctor@med9.com', id
FROM users WHERE username = 'doctor';

-- Inserir paciente de teste
INSERT INTO patients (name, cpf, phone, email, user_id)
SELECT 'Paciente José', '123.456.789-00', '11988888888', 'patient@med9.com', id
FROM users WHERE username = 'patient';
