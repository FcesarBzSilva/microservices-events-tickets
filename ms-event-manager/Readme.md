# 📅 Event Manager Microservice

Microserviço responsável por gerenciar toda a lógica de eventos na plataforma.

## ✨ Funcionalidades

- **Criação de Eventos**: Recebe os dados de um evento (nome, data, etc) e se integra remotamente à **API ViaCep** para debulhar e armazenar o endereço completo (Logradouro, Bairro, UF, etc) a partir de um único `cep`.
- **Listagem e Busca**: Retorna detalhes de eventos específicos pelo `id` ou traz todos ordenados.
- **Atualização**: Atualiza informações do evento no banco de dados.
- **Exclusão com Restrições (Fail-Fast)**: Deleta eventos da base, mas bloqueia e lança exceção (HTTP 409) validando de forma síncrona, via `FeignClient` no `ms-ticket-manager`, se ingressos já formam emitidos para aquele id.

## 🛠️ Tecnologias Principais

- **Linguagem / Framework**: Java 17 + Spring Boot 3
- **Persistência**: Spring Data MongoDB (`eventosdb`) em `PORT 27017` Localhost/Docker.
- **Cliente HTTP**: Spring Cloud OpenFeign para comunicação entre microserviços e chamadas à APIs externas (ViaCep).
- **Tratamento de Exceções**: `@ControllerAdvice` mapeado de forma global inclusive para falhas em circuitos do FeignClient.

## 🚀 Uso das API

O serviço roda na porta `8080` (HTTP). O mapeamento das URIs no `EventController` possui:

| Método | Endpoint | Objetivo |
|--------|------|-----------|
| `POST` | `/events/create-event` | Adicionar Evento (Requer os campos `eventName`, `dateTime` e `cep`) |
| `GET` | `/events/get-event/{id}` | Exibir Evento via ID do Mongo |
| `GET` | `/events/pegar-todos-eventos` | Listar todos em JSON |
| `GET` | `/events/get-all-events/sorted`| Listar todos ordeandos pelo nome do evento. |
| `PUT` | `/events/update-event/{id}` | Edição do Evento |
| `DELETE`| `/events/delete-event/{id}` | Exclusão (Se sem ingressos atrelados) |
