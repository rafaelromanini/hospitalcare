# 🏥 HospitalCare API

![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Containerization-2496ED?logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-orange?logo=apachemaven)
![REST API](https://img.shields.io/badge/REST-API-lightgrey?logo=swagger)

> 🚀 **HospitalCare** é uma API RESTful desenvolvida com **Spring Boot** e **PostgreSQL**, que gerencia pacientes, médicos e agendamentos de consultas médicas com regras reais de negócio, como validações de CPF, especialidades médicas e intervalos mínimos entre consultas.

---

## 🧭 Sumário
- ⚙️ Tecnologias
- 📁 Estrutura do Projeto
- 🧠 Funcionalidades Principais
- 📦 Instalação e Execução
- 🧩 Endpoints Principais
- 🚨 Tratamento de Erros

---

## ⚙️ Tecnologias

| Categoria | Ferramenta | Ícone |
|------------|-------------|--------|
| Linguagem | **Java 17** | ☕ |
| Framework | **Spring Boot 3.5.6** | ![Spring](https://img.shields.io/badge/Spring-6DB33F?logo=spring&logoColor=white) |
| Containerização | **Docker & Docker Compose** | ![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white) |
| Banco de Dados | **PostgreSQL** | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?logo=postgresql&logoColor=white) |
| ORM | **Spring Data JPA** | 🗃️ |
| Validações | **Jakarta Validation (Bean Validation)** | ✅ |
| Build Tool | **Maven** | ![Maven](https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white) |
| Controle de versão | **Git & GitHub** | 🧾 |
| IDE recomendada | IntelliJ IDEA / VSCode | 💻 |

---

## 📁 Estrutura do Projeto

```bash
src/
 ├── main/
 │   ├── java/com/hospitalcare/
 │   │   ├── controller/           # Controladores REST
 │   │   ├── service/              # Regras de negócio
 │   │   ├── model/                # Entidades JPA (Patient, Doctor, Appointment)
 │   │   │    ├── enums/           # Enums (Specialty, AppointmentStatus, etc.)
 │   │   ├── repository/           # Interfaces do Spring Data JPA
 │   │   ├── dto/                  # Request/Response DTOs
 │   │   ├── exceptions/           # Exceptions personalizadas
 │   │   └── infra/exception/      # Global Exception Handler
 │   └── resources/
 │        ├── application.yml      # Configuração do banco PostgreSQL
 │        └── static / templates   # (Reservado para futuras views)
 └── test/                         # Testes unitários e de integração
```

## 🧠 Funcionalidades Principais

### 👩‍⚕️ Doctors
- Cadastro de médicos com validação de **CRM** e **especialidade** (`enum Specialty`).
- Busca de médicos por nome.
- Verificação de duplicidade de CRM com `DoctorAlreadyExistsException`.

**Exemplo de erro (CRM duplicado):**
```json
{
  "error": "Duplicated doctor",
  "message": "Doctor with CRM 12345 already exists."
}
```

### 🧍‍♂️ Patients
- Cadastro de pacientes com validação de **CPF** e telefone via `@Pattern`.
- Evita duplicidade de CPF com `PatientAlreadyExistsException`.
- Atualização e listagem de pacientes.

**Exemplo de erro (CPF duplicado):**
```json
{
  "error": "Duplicated patient",
  "message": "Patient with CPF 123.456.789-00 already exists."
}
```
**Exemplo de validação de campos:**
```json
[
  { "field": "cpf", "message": "must match pattern XXX.XXX.XXX-XX" },
  { "field": "name", "message": "must not be blank" }
]
```
**Exemplo de erro (tentativa de alteração de CPF):**
```json
{
  "error": "Immutable field",
  "message": "CPF cannot be modified once registered."
}
```

### 🗓️ Appointments
- Criação de agendamentos entre médico e paciente.
- Regra de negócio: **cada consulta dura 30 minutos**, evitando sobreposição de horários.
- Alteração de status (`SCHEDULED`, `CANCELED`, `COMPLETED`).
- Erros de conflito tratados com `AppointmentConflictException`.

**Exemplo de erro (conflito de horário):**
```json
{
  "error": "Scheduling conflict",
  "message": "Doctor Dr. Alice Rodrigues already has an appointment scheduled at 2025-10-14T14:00."
}
```
**Exemplo de atualização de status:**
```json
{
  "id": 3,
  "doctorName": "Dr. Alice Rodrigues",
  "patientName": "Carlos Nunes",
  "patientCpf": "123.456.789-00",
  "dateTime": "2025-10-14T14:00:00",
  "status": "CANCELED"
}
```
---

## 📦 Instalação e Execução

### 🔧 Pré-requisitos
- ☕ **Java 17+**
- 🐘 **PostgreSQL**
- 🧰 **Maven**
- 🐳 Docker (opcional)

### 🗂️ Clonar o repositório
```
git clone https://github.com/SeuUsuario/hospitalcare.git
cd hospitalcare
```

### ⚙️ Configurar o banco de dados
Crie um banco no PostgreSQL:
```
CREATE DATABASE hospitalcare;
```
E configure no application.yml:
```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hospitalcare
    username: postgres
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```
▶️ Rodar o projeto
```
mvn spring-boot:run
```
API disponível em:
```
http://localhost:8080
```
## 🐳 Configuração com Docker (opcional)

Caso prefira não instalar o PostgreSQL manualmente, você pode usar o **Docker Compose** incluído no projeto.

### Arquivo: `docker-compose.yml`

```yaml
name: hospitalcare
services:
  postgres:
    container_name: postgres_db
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=hospitalcare_db'
      - 'POSTGRES_PASSWORD=password'
      - 'POSTGRES_USER=postgres'
    ports:
      - '5432:5432'
```
📦 Subir o container
```bash
docker compose up -d
docker ps
```
🗄️ Banco disponível em
```bash
jdbc:postgresql://localhost:5432/hospitalcare_db
```
⚙️ Atualize o application.yml se necessário
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hospitalcare_db
    username: postgres
    password: password
```

### 🧩 Endpoints Principais
| Método   | Endpoint                      | Descrição                 |
| -------- | ----------------------------- | ------------------------- |
| **POST** | `/patients`                   | Cadastrar paciente        |
| **GET**  | `/patients`                   | Listar todos os pacientes |
| **POST** | `/doctors`                    | Cadastrar médico          |
| **GET**  | `/doctors/search?name=Alice`  | Buscar médico por nome    |
| **POST** | `/appointments`               | Agendar consulta          |
| **PUT**  | `/appointments/{id}/cancel`   | Cancelar consulta         |
| **PUT**  | `/appointments/{id}/complete` | Concluir consulta         |

### 🚨 Tratamento de Erros (Global Exception Handler)

A classe ValidationHandler centraliza todos os erros e retorna respostas JSON amigáveis.

Exemplo de erro:
```json
[
  { "field": "cpf", "message": "must match pattern XXX.XXX.XXX-XX" },
  { "field": "name", "message": "must not be blank" }
]
```
