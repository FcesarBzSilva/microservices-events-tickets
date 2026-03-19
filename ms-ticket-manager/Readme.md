# 🎟️ Ticket Manager Microservice

Microserviço responsável por gerenciar e registrar logicamente as compras de ingressos vinculados aos eventos da plataforma.

## ✨ Operações Principais

- **Venda de Ingressos**: Cria o ticket (recebendo dados do usuário e valores), consultando de imediato se aquele Evento atrelado realmente existe no banco do `ms-event-manager`. Se o evento existir, o ticket será criado e a aplicação publicará, de forma **assíncrona via RabbitMQ**, uma notificação do ticket.
- **Consultas**: Fornece filtragens para tickets por status, Eventos que hospedam aqueles tickets, ou de acordo com ID direto.
- **Cancelamento (Soft Delete)**: Nunca remove fisicamente os tickets do MongoDB. O endpoint inativa a venda marcando o `Status` como cancelado com segurança.
- **Atualizações e Transferências**: Atualiza informações nominais dos clientes do ticket pela URL.

## 🛠️ Stack Tecnológica

- **Java 17 / Spring Boot 3**: Aplicação Base.
- **NoSQL via Spring Data MongoDB**: Armazena ingressos (`ticketsdb`), com a URI configurada via docker-compose para escalar e não expor host local de forma travada.
- **Spring AMQP (RabbitMQ)**: Serviço de filas (topic exchanges/queues) para enviar Payload Serializado (Publish) num Message Broker. Muito bom para disparo de e-mails em background futuramente.
- **Spring Cloud OpenFeign**: Proxy Declarativo que checa no provedor de Eventos o ciclo de vida do ID consumido na hora da venda.

## 🚀 Uso da API

Trafega na porta local/Docker na porta `8081`. Os Endpoints roteados em `TicketController`:

| HTTP Method | URI | Resumo Funcional |
|-------------|------|-------------------|
| `POST` | `/tickets/create-ticket` | Adquire compra ligada ao `eventId`. Dispara eventos na fila `ticketQ`. |
| `GET` | `/tickets/get-ticket/{id}` | Retorna dados completos do ticket. Exibe erro para tickets "Cancelados". |
| `GET` | `/tickets/check-tickets-by-event/{eventId}` | Exibe listas de Ingressos Comprados p/ aquele ID de Evento específico. |
| `PUT` | `/tickets/update-ticket/{id}` | Salva novas alterações no Titular. |
| `DELETE`| `/tickets/cancel-ticket/{id}` | Realiza Soft Delete. |
