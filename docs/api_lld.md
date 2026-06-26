# API Low-Level Design (LLD)

## 1. REST Endpoints

### 1.1 Create Order
**Endpoint:** `POST /api/orders`
**Service:** Order Service
**Purpose:** Accepts order details, saves to DB (Status: PLACED), and publishes an event to ActiveMQ.

**Request Payload:**
```json
{
  "customerName": "John Doe",
  "itemName": "Pizza",
  "amount": 350.00
}
```

**Response (HTTP 201 Created):**
```json
{
  "id": 1,
  "customerName": "John Doe",
  "itemName": "Pizza",
  "amount": 350.00,
  "status": "PLACED"
}
```

### 1.2 Get All Orders
**Endpoint:** `GET /api/orders`
**Service:** Order Service
**Purpose:** Returns a list of all orders and their real-time statuses for the UI dashboard.

**Response (HTTP 200 OK):**
```json
[
  {
    "id": 1,
    "customerName": "John Doe",
    "itemName": "Pizza",
    "amount": 350.00,
    "status": "DELIVERED"
  }
]
```

---

## 2. ActiveMQ Queues

### 2.1 Order Created Event
**Queue Name:** `order.created`
**Publisher:** Order Service
**Consumer:** Camunda Workflow Engine

**Message Format (String / Long):**
```text
1
```
*(The message payload is simply the Order ID as a Long/String).*

---

## 3. Internal Service Tasks (REST)
These are called internally by the Camunda Workflow Engine.

### 3.1 Process Payment
**Endpoint:** `POST /api/payment/process/{orderId}`
**Service:** Payment Service
**Response:** `true` or `false` (Boolean representing success/failure).

### 3.2 Prepare Food
**Endpoint:** `POST /api/kitchen/prepare/{orderId}`
**Service:** Kitchen Service
**Response:** HTTP 200 OK

### 3.3 Assign Delivery
**Endpoint:** `POST /api/delivery/assign/{orderId}`
**Service:** Delivery Service
**Response:** HTTP 200 OK

### 3.4 Update Final Status
**Endpoint:** `PUT /api/orders/{orderId}/status`
**Service:** Order Service
**Request Parameter:** `?status=DELIVERED`
**Response:** HTTP 200 OK

---

## 4. Error Handling & Edge Cases
- **Payment Failure:** If `PaymentService` returns `false`, Camunda evaluates an XOR Gateway and routes the token to the `OrderCancelled` path. It will call `OrderService` to update the status to `CANCELLED`.
- **Broker Unavailability:** If ActiveMQ is down, `OrderService` will throw a JMS connection exception when saving an order. `camunda-service` will auto-retry connection using `FixedBackOffExecution`.
