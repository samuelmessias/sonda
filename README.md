# Gestão de Aeronaves
## Sobre o projeto
O sistema Gestão de Aeronaves e um aplicação full stack web desenvolvida com Spring Boot e React.

O sistema consiste em um banco de aeronaves, os quais podem ser listados, incluidas, alteradas e deletadas aeronaves. Também e possivel pesquisar as aeronaves por modelo, marca, ano de fabricação e se estão como vendida no sistema.

# Tecnologias utilizadas
## Backend
- Java
- Spring Boot
- Spring Data JPA
- Lombok
- Banco H2
- Banco Postgres
- Specification
- Junit
- Mockito
- Maven

## Frontend
- React JS
- TypeScript
- Yarn
- React hooks form
- React Toastfy
- HTML
- CSS
- Bootstrap

# Como executar o projeto

## Pré-requisitos
- Java 11
- yarn
- docker instalado
- clonar repositório

## Banco Postgres
```bash
# dentro da pasta do projeto execute o comando
docker-compose up

```


## Back end

```bash

# entrar na pasta backend
cd backend

# executar o projeto
./mvnw spring-boot:run
```

## Front end

```bash
# entrar na pasta frontend
cd frontend

# instalar dependências
yarn install

# executar o projeto
yarn start
```
Acesse: http://localhost:3000/
