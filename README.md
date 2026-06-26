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

To run the entire system locally, you need to start the MySQL database and then run each service individually.

### Prerequisites
- **Java 17+**
- **Node.js & npm**
- **MySQL** running on localhost (ensure your credentials in the `application.properties` of each service match your setup).

### 1. Start the Microservices
Each backend service is a Spring Boot application. You can start them using the provided Maven wrapper (`mvnw`) from their respective nested directories. Open a separate terminal for each service:

**Order Service:**
```bash
cd order-service/order-service
./mvnw spring-boot:run
```

**Payment Service:**
```bash
cd payment-service/paymentservice
./mvnw spring-boot:run
```

**Kitchen Service:**
```bash
cd kitchen-service/kitchenservice
./mvnw spring-boot:run
```

**Delivery Service:**
```bash
cd delivery-service/deliveryservice
./mvnw spring-boot:run
```

**Camunda Service:**
*(Make sure this is started last, as it depends on the ActiveMQ broker initialized by the Order Service)*
```bash
cd camunda-service/camunda-service
./mvnw spring-boot:run
```

### 2. Start the React Frontend
Open a new terminal and run:
```bash
cd frontend
npm install
npm run dev
```
The UI should now be accessible at `http://localhost:5173` (or the port specified by Vite).

## Documentation
For more detailed documentation, see the `/docs` directory which contains:
- API Low-Level Design (LLD)
- Database ER Diagram and schemas
- Implementation reports and system architecture details
- Screenshots of the User Interface and Workflow executions
