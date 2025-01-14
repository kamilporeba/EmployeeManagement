-- Tworzenie tabeli employees
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,  -- Autoinkrementacja w PostgreSQL
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    position VARCHAR(100),
    email VARCHAR(255)
);

-- Tworzenie tabeli attendance_types
CREATE TABLE attendance_types (
    id SERIAL PRIMARY KEY,  -- Autoinkrementacja w PostgreSQL
    type VARCHAR(50) NOT NULL
);

-- Tworzenie tabeli attendance
CREATE TABLE attendance (
    id SERIAL PRIMARY KEY,  -- Autoinkrementacja w PostgreSQL
    employee_id INT,
    attendance_date DATE NOT NULL,
    check_in TIMESTAMP,  -- Używamy typu TIMESTAMP zamiast DATETIME w PostgreSQL
    check_out TIMESTAMP, -- Używamy typu TIMESTAMP zamiast DATETIME w PostgreSQL
    attendance_type_id INT,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (attendance_type_id) REFERENCES attendance_types(id)
);