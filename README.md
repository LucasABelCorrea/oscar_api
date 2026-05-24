# Oscar API

API REST desenvolvida em **Spring Boot** para gerenciamento de **Filmes** e **Atores** indicados ao Oscar. Projeto desenvolvido para fins de estudo na FIAP.

A aplicação foi refatorada para seguir uma separação de responsabilidades mais clara, utilizando **Controllers**, **Services**, **Repositories**, **Models** e **DTOs**.

---

## 📋 Sumário

- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Novidades da versão atual](#-novidades-da-versão-atual)
- [Arquitetura da Aplicação](#-arquitetura-da-aplicação)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Como rodar a aplicação](#-como-rodar-a-aplicação)
  - [1. Subindo o Banco de Dados com Docker](#1-subindo-o-banco-de-dados-com-docker)
  - [2. Rodando a API Spring Boot](#2-rodando-a-api-spring-boot)
- [Documentação da API (Swagger)](#-documentação-da-api-swagger)
- [Endpoints Disponíveis](#-endpoints-disponíveis)
- [Modelos, DTOs e campos esperados](#-modelos-dtos-e-campos-esperados)
- [Exemplos de Requisições](#-exemplos-de-requisições)
- [Encerrando o ambiente](#-encerrando-o-ambiente)
- [Autor](#-autor)

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 4.0.3**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Boot DevTools
  - Spring Validation
- **MySQL 8**
- **Maven** (gerenciador de dependências — wrapper `mvnw` incluído)
- **Lombok** (redução de boilerplate)
- **ModelMapper** (mapeamento entre DTOs e Models)
- **SpringDoc OpenAPI / Swagger UI** (documentação interativa)

---

## ✅ Pré-requisitos

Antes de começar, você precisa ter instalado na sua máquina:

- [Java JDK 17+](https://adoptium.net/)
- [Docker](https://www.docker.com/products/docker-desktop/) e [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/) (para clonar o repositório)

> 💡 **Não é necessário ter o Maven instalado!** O projeto inclui o Maven Wrapper (`mvnw` / `mvnw.cmd`).

---

## 🆕 Novidades da versão atual

Esta versão inclui uma refatoração estrutural da API, com foco em organização, manutenção e padronização das entradas e saídas dos endpoints.

### Camada de Service

Foi criada uma camada de serviço para centralizar as regras de acesso e manipulação dos dados:

- `AtorService`
- `FilmeService`

Os Controllers não acessam mais diretamente os Repositories. Agora eles chamam os Services, que por sua vez utilizam os Repositories para persistência e consulta no banco de dados.

### DTOs de entrada e saída

Foram adicionados DTOs para separar os dados recebidos e retornados pela API dos Models persistidos no banco.

Para **Atores**:

- `AtorCreateRequest`
- `AtorUpdateRequest`
- `AtorResponse`
- `AtorMapper`

Para **Filmes**:

- `FilmeCreateRequest`
- `FilmeUpdateRequest`
- `FilmeResponse`
- `FilmeMapper`

Essa separação evita expor diretamente as entidades JPA nos endpoints e permite controlar melhor quais campos entram no cadastro, quais campos entram na atualização e quais campos são devolvidos na resposta.

### Geração automática de ID no Model

Os identificadores agora são gerados automaticamente pelo banco/JPA com `@GeneratedValue(strategy = GenerationType.AUTO)`:

- `Ator.id`
- `Filme.id`

Com isso, o campo `id` **não deve ser enviado no corpo das requisições POST**. Ele é retornado pela API depois que o registro é criado.

### Controllers refatorados

Os Controllers foram ajustados para:

- Receber DTOs de request com `@RequestBody`.
- Validar entradas com `@Valid`.
- Usar os Mappers para converter DTOs em Models e Models em DTOs de resposta.
- Delegar operações de criação, consulta, atualização e remoção para a camada de Service.
- Retornar `ResponseEntity` com status HTTP adequado, como `201 Created`, `200 OK`, `204 No Content` e `404 Not Found`.

---

## 🧱 Arquitetura da Aplicação

A aplicação está organizada em camadas:

| Camada | Responsabilidade |
|--------|------------------|
| `controller` | Expõe os endpoints REST e recebe as requisições HTTP. |
| `dto` | Define objetos de entrada, saída e mapeamento entre DTOs e Models. |
| `service` | Centraliza a lógica de aplicação e intermedia Controller e Repository. |
| `repository` | Realiza a comunicação com o banco usando Spring Data JPA. |
| `model` | Representa as entidades JPA persistidas no banco de dados. |

Fluxo principal da API:

```text
Requisição HTTP
      ↓
Controller
      ↓
DTO / Mapper
      ↓
Service
      ↓
Repository
      ↓
Banco de Dados
```

---

## 📂 Estrutura do Projeto

```text
oscar_api/
├── src/
│   └── main/
│       ├── java/br/com/fiap/oscar_api/
│       │   ├── Application.java
│       │   ├── controller/
│       │   │   ├── AtorController.java
│       │   │   └── FilmeController.java
│       │   ├── dto/
│       │   │   ├── AtorCreateRequest.java
│       │   │   ├── AtorMapper.java
│       │   │   ├── AtorResponse.java
│       │   │   ├── AtorUpdateRequest.java
│       │   │   ├── FilmeCreateRequest.java
│       │   │   ├── FilmeMapper.java
│       │   │   ├── FilmeResponse.java
│       │   │   └── FilmeUpdateRequest.java
│       │   ├── model/
│       │   │   ├── Ator.java
│       │   │   └── Filme.java
│       │   ├── repository/
│       │   │   ├── AtorRepository.java
│       │   │   └── FilmeRepository.java
│       │   └── service/
│       │       ├── AtorService.java
│       │       └── FilmeService.java
│       └── resources/
│           └── application.properties
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## ▶️ Como rodar a aplicação

Siga os passos abaixo na ordem para subir o ambiente do zero.

### 1. Subindo o Banco de Dados com Docker

A aplicação espera um banco **MySQL** rodando em `localhost:3306` com as seguintes credenciais, definidas em `src/main/resources/application.properties`:

| Configuração | Valor |
|--------------|-------|
| Host | `localhost` |
| Porta | `3306` |
| Database | `api` |
| Usuário | `root` |
| Senha | `root_pwd` |

A URL configurada cria o banco automaticamente se ele ainda não existir:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/api?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

#### Opção com Docker Run

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

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação subirá em:

```text
http://localhost:8080
```

As tabelas `atores` e `filmes` serão criadas/atualizadas automaticamente pelo Hibernate, conforme a configuração:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

## 📖 Documentação da API (Swagger)

Após subir a aplicação, acesse a documentação interativa em:

```text
http://localhost:8080/
```

A interface do Swagger UI permite testar todos os endpoints diretamente pelo navegador.

A especificação OpenAPI em JSON está disponível em:

```text
http://localhost:8080/v3/api-docs
```

---

## 🔌 Endpoints Disponíveis

Todos os endpoints utilizam o prefixo configurado em `application.properties`:

```properties
api.version=v2
```

Portanto, o prefixo atual é:

```text
/api/v2
```

### Atores — `/api/v2/atores`

| Método | Endpoint | Descrição | Body |
|--------|----------|-----------|------|
| POST | `/api/v2/atores` | Cria um novo ator | `AtorCreateRequest` |
| GET | `/api/v2/atores` | Lista todos os atores | Não possui |
| GET | `/api/v2/atores/{id}` | Busca um ator pelo ID | Não possui |
| PUT | `/api/v2/atores/{id}` | Atualiza um ator existente | `AtorUpdateRequest` |
| DELETE | `/api/v2/atores/{id}` | Remove um ator | Não possui |

### Filmes — `/api/v2/filmes`

| Método | Endpoint | Descrição | Body |
|--------|----------|-----------|------|
| POST | `/api/v2/filmes` | Cria um novo filme | `FilmeCreateRequest` |
| GET | `/api/v2/filmes` | Lista todos os filmes | Não possui |
| GET | `/api/v2/filmes/{id}` | Busca um filme pelo ID | Não possui |
| PUT | `/api/v2/filmes/{id}` | Atualiza um filme existente | `FilmeUpdateRequest` |
| DELETE | `/api/v2/filmes/{id}` | Remove um filme | Não possui |

---

## 📦 Modelos, DTOs e campos esperados

### Ator

Entidade persistida: `Ator`

| Campo | Tipo | Observação |
|-------|------|------------|
| `id` | `Long` | Gerado automaticamente. |
| `nome` | `String` | Obrigatório no cadastro. |
| `numFilmes` | `Integer` | Quantidade de filmes do ator. |
| `idade` | `Integer` | Idade do ator. |
| `numOscars` | `Integer` | Quantidade de Oscars recebidos. |

#### `AtorCreateRequest`

Usado no `POST /api/v2/atores`.

```json
{
  "nome": "Fernanda Torres",
  "numFilmes": 30,
  "idade": 59,
  "numOscars": 0
}
```

#### `AtorUpdateRequest`

Usado no `PUT /api/v2/atores/{id}`.

```json
{
  "nome": "Fernanda Torres",
  "numFilmes": 31,
  "idade": 59,
  "numOscars": 0
}
```

#### `AtorResponse`

Resposta retornada pela API.

```json
{
  "id": 1,
  "nome": "Fernanda Torres",
  "numFilmes": 30,
  "idade": 59,
  "numOscars": 0
}
```

---

### Filme

Entidade persistida: `Filme`

| Campo | Tipo | Observação |
|-------|------|------------|
| `id` | `Long` | Gerado automaticamente. |
| `nome` | `String` | Obrigatório no cadastro. |
| `numPremiacoes` | `Integer` | Quantidade de premiações recebidas. |
| `qtdCategoriasDisputadas` | `Integer` | Quantidade de categorias disputadas. |
| `categoria` | `String` | Categoria principal do filme. |

#### `FilmeCreateRequest`

Usado no `POST /api/v2/filmes`.

```json
{
  "nome": "Ainda Estou Aqui",
  "numPremiacoes": 1,
  "qtdCategoriasDisputadas": 3,
  "categoria": "Melhor Filme Internacional"
}
```

#### `FilmeUpdateRequest`

Usado no `PUT /api/v2/filmes/{id}`.

```json
{
  "nome": "Ainda Estou Aqui",
  "numPremiacoes": 2,
  "qtdCategoriasDisputadas": 3,
  "categoria": "Melhor Filme Internacional"
}
```

#### `FilmeResponse`

Resposta retornada pela API.

```json
{
  "id": 1,
  "nome": "Ainda Estou Aqui",
  "numPremiacoes": 1,
  "qtdCategoriasDisputadas": 3,
  "categoria": "Melhor Filme Internacional"
}
```

---

## 🧪 Exemplos de Requisições

### Criar um Ator

> O campo `id` não deve ser enviado. Ele é gerado automaticamente.

```bash
curl -X POST http://localhost:8080/api/v2/atores \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Fernanda Torres",
    "numFilmes": 30,
    "idade": 59,
    "numOscars": 0
  }'
```

### Listar Atores

```bash
curl http://localhost:8080/api/v2/atores
```

### Buscar Ator por ID

```bash
curl http://localhost:8080/api/v2/atores/1
```

### Atualizar um Ator

```bash
curl -X PUT http://localhost:8080/api/v2/atores/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Fernanda Torres",
    "numFilmes": 31,
    "idade": 59,
    "numOscars": 0
  }'
```

### Remover um Ator

```bash
curl -X DELETE http://localhost:8080/api/v2/atores/1
```

---

### Criar um Filme

> O campo `id` não deve ser enviado. Ele é gerado automaticamente.

```bash
curl -X POST http://localhost:8080/api/v2/filmes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Ainda Estou Aqui",
    "numPremiacoes": 1,
    "qtdCategoriasDisputadas": 3,
    "categoria": "Melhor Filme Internacional"
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

### Atualizar um Filme

```bash
curl -X PUT http://localhost:8080/api/v2/filmes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Ainda Estou Aqui",
    "numPremiacoes": 2,
    "qtdCategoriasDisputadas": 3,
    "categoria": "Melhor Filme Internacional"
  }'
```

### Remover um Filme

```bash
curl -X DELETE http://localhost:8080/api/v2/filmes/1
```

---

## 🛑 Encerrando o ambiente

Para parar a aplicação Spring Boot, pressione `Ctrl + C` no terminal onde ela está rodando.

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
