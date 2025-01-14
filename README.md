# Employee Attendance Management System

## Project Description
This project is designed to manage employee attendance in a company. It consists of three tables:
- **`employees`**: Stores employee information.
- **`attendance_types`**: Defines the types of attendance (e.g., vacation, sick leave, remote work).
- **`attendance`**: Records employee attendance, including check-in and check-out times, as well as the attendance type.

## Database Structure

### 1. `employees` Table
This table stores information about employees.

```sql
CREATE TABLE employees (
    id SERIAL PRIMARY KEY,  -- Auto-incrementing ID for employee
    first_name VARCHAR(100) NOT NULL,  -- First name
    last_name VARCHAR(100) NOT NULL,  -- Last name
    position VARCHAR(100),  -- Position
    email VARCHAR(255)  -- Email address
);````

### 2. `attendance_types` Table
This table stores the different types of employee attendance.


```sql
CREATE TABLE attendance_types (
    id SERIAL PRIMARY KEY,  -- Auto-incrementing ID for attendance type
    type VARCHAR(50) NOT NULL  -- Type of attendance (e.g., vacation, sick leave)
);````

### 3. `attendance` Table
This table records the attendance of employees, including their check-in and check-out times, and associates them with an attendance type.

```sql
CREATE TABLE attendance (
    id SERIAL PRIMARY KEY,  -- Auto-incrementing ID for attendance record
    employee_id INT,  -- Employee ID (foreign key to employees)
    attendance_date DATE NOT NULL,  -- Date of attendance
    check_in TIMESTAMP,  -- Check-in time
    check_out TIMESTAMP,  -- Check-out time
    attendance_type_id INT,  -- Attendance type ID (foreign key to attendance_types)
    FOREIGN KEY (employee_id) REFERENCES employees(id),  -- Relation to employees table
    FOREIGN KEY (attendance_type_id) REFERENCES attendance_types(id)  -- Relation to attendance_types table
);````

## Inserting Data
### 1. Example of Adding Employees to the `employees` Table:
```sql
INSERT INTO employees (first_name, last_name, position, email)
VALUES
    ('Jan', 'Kowalski', 'Developer', 'jan.kowalski@example.com'),
    ('Anna', 'Nowak', 'Manager', 'anna.nowak@example.com'),
    ('Piotr', 'Wiśniewski', 'Designer', 'piotr.wisniewski@example.com'),
    ('Maria', 'Kowalska', 'Tester', 'maria.kowalska@example.com'),
    ('Michał', 'Wójcik', 'Developer', 'michal.wojcik@example.com'),
    ('Katarzyna', 'Lewandowska', 'HR', 'katarzyna.lewandowska@example.com'),
    ('Tomasz', 'Zieliński', 'Administrator', 'tomasz.zielinski@example.com'),
    ('Olga', 'Szymańska', 'Manager', 'olga.szymanska@example.com'),
    ('Adam', 'Krawczyk', 'Developer', 'adam.krawczyk@example.com'),
    ('Ewa', 'Jankowska', 'Analyst', 'ewa.jankowska@example.com');````

## License
This project is licensed under the MIT License.
