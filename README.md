# Microservices Events & Tickets 🎟️

> **Este repositório foi criado como desafio técnico durante meu estágio.** Ele reúne dois microsserviços desenvolvidos em Java com Spring Boot, que foram deployados na AWS e acessíveis inclusive via celular. Foi uma experiência incrível! 🚀

Um projeto didático de backend modular e escalável. A arquitetura é dividida em dois microserviços independentes que se comunicam via Feign Client e RabbitMQ, utilizando MongoDB como banco de dados NoSQL.

## 🏗️ Estrutura do Projeto

O sistema é comporto por dois microserviços principais:
- **[ms-event-manager](./ms-event-manager/Readme.md)** (Porta 8080): Gerencia o cadastro, listagem, atualização e exclusão de eventos. Integra-se com a API ViaCep para consultar o endereço e com o ticket-manager para validações antes de exclusões.
- **[ms-ticket-manager](./ms-ticket-manager/Readme.md)** (Porta 8081): Responsável pelas operações de venda (criação), atualização e cancelamento lógico de ingressos associados aos eventos. Interage com o event-manager para validar a existência do evento e com RabbitMQ para publicações de mensageria assíncrona.

*As documentações detalhadas de cada serviço, com seus atributos e bibliotecas, estão nos Readmes dentro de suas respectivas pastas.*

## 🚀 Como Executar o Projeto Localmente via Docker

A maneira mais fácil de rodar ambos os serviços e todas as suas dependências (MongoDB e RabbitMQ) de forma orquestrada é utilizando o Docker Compose fornecido na raiz do projeto.

### 1. Pré-Requisitos
- Docker e Docker Compose instalados.
- Java 17 e Maven (para recompilar os projetos antes de gerar a imagem Docker).

### 2. Passos para a Execução

1. **Faça o clone do repositório**:
   ```sh
   git clone <url-do-seu-repositorio>
   cd microservices-events-tickets
   ```

2. **Gere os arquivos .jar executáveis**:
   Antes de subir o Docker, é preciso compilar ambos os projetos.
   ```sh
   cd ms-event-manager
   mvn clean package -DskipTests
   cd ..
   
   cd ms-ticket-manager
   mvn clean package -DskipTests
   cd ..
   ```

3. **Suba o ambiente completo**:
   Na raiz do projeto (onde está o `docker-compose.yml`), execute:
   ```sh
   docker compose up -d --build
   ```

### 3. Acessando a Aplicação
- A API de eventos poderá ser acessada em: `http://localhost:8080/events/...`
- A API de ingressos poderá ser acessada em: `http://localhost:8081/tickets/...`
- A base de dados principal usa o MongoDB na porta padrão local (`27017`).
- O painel administrativo do RabbitMQ responde acessando `http://localhost:15672` (login/senha: `guest`).

> **Nota:** Por ser um projeto de caráter estritamente didático, os arquivos `application.properties` (e senhas locais) foram mantidos com fins facilitadores e de histórico, não sendo voltados para ambientes reais de produção sem as devidas abstrações de segurança.
