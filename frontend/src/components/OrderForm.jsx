import React, { useState } from 'react';

function OrderForm({ onOrderPlaced }) {
  const menu = [
    { name: 'Burger', price: 150.00 },
    { name: 'Pizza', price: 350.00 },
    { name: 'sandwich ', price: 100.00 },
    { name: 'Fries', price: 120.00 },
    { name: 'Icecream', price: 180.00 }
  ];

  const [customerName, setCustomerName] = useState('');
  const [item, setItem] = useState(menu[0].name);
  const [amount, setAmount] = useState(menu[0].price);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!customerName || !item || !amount) return;

    setLoading(true);
    try {
      const res = await fetch('http://localhost:8081/api/orders', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          customerName,
          itemName: item,
          amount: parseFloat(amount)
        })
      });

      if (res.ok) {
        setCustomerName('');
        setItem(menu[0].name);
        setAmount(menu[0].price);
        onOrderPlaced();
      }
    } catch (err) {
      console.error("Failed to place order", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="glass-panel">
      <h2 style={{ marginBottom: '1.5rem' }}>Place New Order</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Customer Name</label>
          <input
            type="text"
            className="form-control"
            value={customerName}
            onChange={e => setCustomerName(e.target.value)}
            placeholder="Enter Your Name"
            required
          />
        </div>

        <div className="form-group">
          <label>Food Item</label>
          <select
            className="form-control"
            value={item}
            onChange={e => {
              const selectedItem = e.target.value;
              setItem(selectedItem);
              const found = menu.find(m => m.name === selectedItem);
              if (found) setAmount(found.price);
            }}
            required
            style={{ appearance: 'auto', backgroundColor: 'var(--surface-hover)', color: 'var(--text)', border: '1px solid var(--border)', borderRadius: '8px', padding: '0.75rem 1rem', width: '100%', fontSize: '1rem' }}
          >
            {menu.map((m, idx) => (
              <option key={idx} value={m.name}>{m.name}</option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Amount (₹)</label>
          <input
            type="number"
            step="0.01"
            className="form-control"
            value={amount}
            readOnly
            style={{ backgroundColor: 'rgba(0,0,0,0.2)', color: 'var(--text-muted)' }}
          />
        </div>

        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? 'Processing...' : 'Place Order'}
        </button>
      </form>
    </div>
  );
}

export default OrderForm;
