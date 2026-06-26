# Food Order System

A comprehensive, microservices-based Food Ordering System. This project demonstrates a distributed architecture with multiple interconnected services to handle the end-to-end flow of food ordering, payment processing, kitchen management, and delivery.

## Architecture & Microservices

The application is divided into several specialized microservices and a frontend:

- **Frontend (`/frontend`)**: The user interface for customers to browse the menu, place orders, and view their dashboard.
- **Order Service (`/order-service`)**: Manages the creation and lifecycle of customer orders.
- **Payment Service (`/payment-service`)**: Handles payment processing and validation.
- **Kitchen Service (`/kitchen-service`)**: Manages food preparation, interacting with the kitchen staff.
- **Delivery Service (`/delivery-service`)**: Coordinates the dispatch and delivery of prepared orders to the customer.
- **Camunda Service (`/camunda-service`)**: A workflow orchestration engine (Camunda) that manages the complex business process spanning across all these different services.

## Getting Started

*(Add instructions here on how to build and run the services, such as Docker Compose commands or Maven run commands).*

## Documentation
For more detailed documentation, see the `/docs` directory which contains API designs, database schemas, and system architecture details.
