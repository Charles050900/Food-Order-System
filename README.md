# Online Food Order Processing System

A comprehensive, microservices-based Food Ordering System built from scratch. This project demonstrates a distributed architecture with multiple interconnected services to handle the end-to-end flow of food ordering, payment processing, kitchen management, and delivery. It uses an **ActiveMQ broker** for decoupled messaging and a central **Camunda BPMN workflow engine** to orchestrate the entire process.

## Technologies Used
- **Frontend**: React UI (Order form and live polling dashboard)
- **Backend**: Spring Boot (Java)
- **Database**: MySQL with JPA Entities
- **Messaging/Eventing**: Embedded ActiveMQ Broker (tcp://localhost:61616)
- **Orchestration**: Camunda BPMN Workflow Engine (Embedded)

## Architecture & Microservices

The application is highly cohesive, modular, and divided into several specialized microservices:

- **Frontend (`/frontend`)**: The React user interface for customers to browse the menu, place orders, and view their dashboard which polls for real-time statuses.
- **Order Service (`/order-service`)**: Exposes REST endpoints (`POST /api/orders`) to accept orders, saves them to the MySQL database (Status: PLACED), and publishes an `order.created` event to the ActiveMQ broker.
- **Camunda Service (`/camunda-service`)**: Contains the embedded Camunda Workflow Engine. It listens to ActiveMQ to start the business process (`food-order-process.bpmn`) and routes the order sequentially through Payment, Kitchen, and Delivery.
- **Payment Service (`/payment-service`)**: Handles payment processing and validation internally via REST calls from Camunda.
- **Kitchen Service (`/kitchen-service`)**: Manages food preparation, updating ticket statuses, and interacting with the kitchen staff.
- **Delivery Service (`/delivery-service`)**: Coordinates the dispatch and delivery of prepared orders by assigning a mock driver.

## Workflow Execution Flow
1. **Place Order**: User submits an order in the React Frontend.
2. **Order Creation**: Order Service saves the order and sends a message to the ActiveMQ queue (`order.created`).
3. **Orchestration**: Camunda Service picks up the message and starts the BPMN workflow.
4. **Payment**: Camunda invokes the Payment Service via REST.
5. **Kitchen**: On payment success, Camunda invokes the Kitchen Service via REST.
6. **Delivery**: Once food is ready, Camunda invokes the Delivery Service via REST.
7. **Completion**: Camunda updates the final status back to the Order Service, marking it as `DELIVERED`.

## Getting Started

*(Add instructions here on how to build and run the services, such as Docker Compose commands or Maven run commands, and how to start the React frontend).*

## Documentation
For more detailed documentation, see the `/docs` directory which contains:
- API Low-Level Design (LLD)
- Database ER Diagram and schemas
- Implementation reports and system architecture details
- Screenshots of the User Interface and Workflow executions
