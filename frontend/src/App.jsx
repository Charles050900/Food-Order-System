import React, { useState, useEffect } from 'react';
import OrderForm from './components/OrderForm';
import OrderDashboard from './components/OrderDashboard';

function App() {
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState(null);

  const fetchOrders = async () => {
    try {
      const res = await fetch('http://localhost:8081/api/orders');
      if (res.ok) {
        const data = await res.json();
        // Sort descending by ID to show newest first
        setOrders(data.sort((a, b) => b.id - a.id));
        setError(null); // Clear error on successful fetch
      } else {
        setError("Failed to fetch orders from server.");
      }
    } catch (err) {
      console.error("Failed to fetch orders", err);
      setError("Cannot connect to server. Is it running?");
    }
  };

  useEffect(() => {
    fetchOrders();
    const interval = setInterval(fetchOrders, 2000); // Poll every 2 seconds
    return () => clearInterval(interval);
  }, []);

  const handleOrderPlaced = () => {
    fetchOrders();
  };

  return (
    <div className="container">
      <header className="header">
        <h1>Food Delivery</h1>
        <p>Order processing orchestration with Camunda & ActiveMQ</p>
      </header>

      <div className="grid">
        <div>
          <OrderForm onOrderPlaced={handleOrderPlaced} />
        </div>
        <div>
          <OrderDashboard orders={orders} error={error} />
        </div>
      </div>
    </div>
  );
}

export default App;
