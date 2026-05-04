# Oscar API

API REST desenvolvida em **Spring Boot** para gerenciamento de **Filmes** e **Atores** indicados ao Oscar. Projeto desenvolvido para fins de estudo na FIAP.

---

## 📋 Sumário

- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como rodar a aplicação](#-como-rodar-a-aplicação)
  - [1. Subindo o Banco de Dados com Docker](#1-subindo-o-banco-de-dados-com-docker)
  - [2. Rodando a API Spring Boot](#2-rodando-a-api-spring-boot)
- [Documentação da API (Swagger)](#-documentação-da-api-swagger)
- [Endpoints Disponíveis](#-endpoints-disponíveis)
- [Exemplos de Requisições](#-exemplos-de-requisições)
- [Encerrando o ambiente](#-encerrando-o-ambiente)

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 4.0.3**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Boot DevTools
- **MySQL 8** (via Docker)
- **Maven** (gerenciador de dependências — wrapper `mvnw` incluído)
- **Lombok** (redução de boilerplate)
- **SpringDoc OpenAPI / Swagger UI** (documentação interativa)

---

## ✅ Pré-requisitos

Antes de começar, você precisa ter instalado na sua máquina:

- [Java JDK 17+](https://adoptium.net/)
- [Docker](https://www.docker.com/products/docker-desktop/) e [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/) (para clonar o repositório)

> 💡 **Não é necessário ter o Maven instalado!** O projeto inclui o Maven Wrapper (`mvnw` / `mvnw.cmd`).

---

## 📂 Estrutura do Projeto

```
oscar_api/
├── src/
│   └── main/
│       ├── java/br/com/fiap/oscar_api/
│       │   ├── Application.java
│       │   ├── controller/
│       │   │   ├── AtorController.java
│       │   │   └── FilmeController.java
│       │   ├── model/
│       │   │   ├── Ator.java
│       │   │   └── Filme.java
│       │   └── repository/
│       │       ├── AtorRepository.java
│       │       └── FilmeRepository.java
│       └── resources/
│           └── application.properties
├── docker-compose.yml
├── pom.xml
├── mvnw
└── README.md
```

---

## ▶️ Como rodar a aplicação

Siga os passos abaixo na ordem para subir o ambiente do zero.

### 1. Subindo o Banco de Dados com Docker

A aplicação espera um banco **MySQL** rodando em `localhost:3306` com as seguintes credenciais (definidas em `src/main/resources/application.properties`):

| Configuração | Valor |
|--------------|-------|
| Host         | `localhost` |
| Porta        | `3306` |
| Database     | `api` (criada automaticamente pela aplicação) |
| Usuário      | `root` |
| Senha        | `root_pwd` |

#### 📄 Crie o arquivo `docker-compose.yml` na raiz do projeto

> ⚠️ **Obrigatório:** se este arquivo ainda não existir no projeto, crie-o com o conteúdo abaixo. Ele é o que garante que qualquer pessoa consiga subir o banco do zero.

```yaml
version: "3.8"

services:
  mysql:
    image: mysql:8.0
    container_name: oscar_api_mysql
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: root_pwd
      MYSQL_DATABASE: api
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-uroot", "-proot_pwd"]
      interval: 10s
      timeout: 5s
      retries: 5

volumes:
  mysql_data:
```

#### 🐳 Suba o container

Na raiz do projeto (onde está o `docker-compose.yml`), execute:

```bash
docker compose up -d
```

> Em versões mais antigas do Docker, use `docker-compose up -d` (com hífen).

Verifique se o container está rodando corretamente:

```bash
docker ps
```

Você deve ver o container `oscar_api_mysql` com status `Up` e `healthy`.

#### 🔧 Alternativa: subindo apenas com `docker run`

Se preferir não usar Docker Compose, pode subir o MySQL diretamente com o comando:

```bash
docker run -d \
  --name oscar_api_mysql \
  -e MYSQL_ROOT_PASSWORD=root_pwd \
  -e MYSQL_DATABASE=api \
  -p 3306:3306 \
  mysql:8.0
```

---

### 2. Rodando a API Spring Boot

Com o banco de dados rodando, abra um terminal na raiz do projeto e execute:

```bash
./mvnw spring-boot:run
```

> Na primeira execução, o Maven irá baixar todas as dependências — pode levar alguns minutos.

A aplicação subirá em: **http://localhost:8080**

As tabelas `filmes` e `atores` serão criadas automaticamente pelo Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

---

## 📖 Documentação da API (Swagger)

Após subir a aplicação, acesse a documentação interativa em:

🔗 **http://localhost:8080/**

A interface do Swagger UI permite testar todos os endpoints diretamente pelo navegador.

A especificação OpenAPI (JSON) está disponível em:

🔗 **http://localhost:8080/v3/api-docs**

---

## 🔌 Endpoints Disponíveis

Todos os endpoints estão sob o prefixo `/api/v2`.

### Filmes — `/api/v2/filmes`

| Método | Endpoint                | Descrição                        |
|--------|-------------------------|----------------------------------|
| POST   | `/api/v2/filmes`        | Cria um novo filme               |
| GET    | `/api/v2/filmes`        | Lista todos os filmes            |
| GET    | `/api/v2/filmes/{id}`   | Busca um filme pelo ID           |
| PUT    | `/api/v2/filmes/{id}`   | Atualiza um filme existente      |
| DELETE | `/api/v2/filmes/{id}`   | Remove um filme                  |

### Atores — `/api/v2/atores`

| Método | Endpoint                | Descrição                        |
|--------|-------------------------|----------------------------------|
| POST   | `/api/v2/atores`        | Cria um novo ator                |
| GET    | `/api/v2/atores`        | Lista todos os atores            |
| GET    | `/api/v2/atores/{id}`   | Busca um ator pelo ID            |
| PUT    | `/api/v2/atores/{id}`   | Atualiza um ator existente       |
| DELETE | `/api/v2/atores/{id}`   | Remove um ator                   |

---

## 🧪 Exemplos de Requisições

### Criar um Filme

```bash
curl -X POST http://localhost:8080/api/v2/filmes \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "nome": "Oppenheimer",
    "numPremiacoes": 7,
    "qtdCategoriasDisputadas": 13,
    "categoria": "Melhor Filme"
  }'
```

### Listar Filmes

```bash
curl http://localhost:8080/api/v2/filmes
```

### Buscar Filme por ID

```bash
curl http://localhost:8080/api/v2/filmes/1
```

### Criar um Ator

```bash
curl -X POST http://localhost:8080/api/v2/atores \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "nome": "Cillian Murphy",
    "numFilmes": 35,
    "idade": 48,
    "numOscars": 1
  }'
```

### Atualizar um Ator

```bash
curl -X PUT http://localhost:8080/api/v2/atores/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Cillian Murphy",
    "numFilmes": 36,
    "idade": 49,
    "numOscars": 1
  }'
```

### Deletar um Filme

```bash
curl -X DELETE http://localhost:8080/api/v2/filmes/1
```

---

## 🛑 Encerrando o ambiente

Para parar a aplicação Spring Boot, basta pressionar `Ctrl + C` no terminal onde ela está rodando.

Para parar e remover o container do MySQL:

```bash
# Parar o container (mantendo os dados)
docker compose stop

# Parar e remover o container (mantendo os dados no volume)
docker compose down

# Parar, remover o container E apagar os dados do volume
docker compose down -v
```

Se você usou `docker run` em vez de `docker compose`:

```bash
docker stop oscar_api_mysql
docker rm oscar_api_mysql
```

---

## 👨‍💻 Autor

Lucas Almeida Bel Correa - RM: 558539

Projeto desenvolvido para a disciplina de Microsservices — **FIAP - 3°SIR**.

---
