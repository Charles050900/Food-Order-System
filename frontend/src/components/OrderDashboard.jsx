import React from 'react';

function OrderDashboard({ orders, error }) {
  return (
    <div className="glass-panel" style={{height: '100%', minHeight: '500px'}}>
      <div className="dashboard-header">
        <h2>Live Orders Dashboard</h2>
        <span style={{fontSize: '0.875rem', color: 'var(--danger)', fontWeight: '600', display: 'flex', alignItems: 'center', gap: '6px'}}>
          <span style={{width: '8px', height: '8px', backgroundColor: 'var(--danger)', borderRadius: '50%', display: 'inline-block', animation: 'pulse 2s infinite'}}></span>
          Live Updates • Polling
        </span>
      </div>
      
      {error ? (
        <p style={{color: 'var(--danger)', textAlign: 'center', marginTop: '2rem', padding: '1rem', backgroundColor: 'rgba(255,0,0,0.1)', borderRadius: '8px'}}>
          {error}
        </p>
      ) : orders.length === 0 ? (
        <p style={{color: 'var(--text-muted)', textAlign: 'center', marginTop: '2rem'}}>
          No orders yet. Place an order to see it here!
        </p>
      ) : (
        <div className="orders-list">
          {orders.map(order => (
            <div key={order.id} className="order-card">
              <div className="order-details">
                <h3>Order #{order.id}</h3>
                <p>{order.customerName} ordered <strong>{order.itemName}</strong></p>
              </div>
              
              <div style={{textAlign: 'right'}}>
                <div className={`status-badge status-${order.status || 'PLACED'}`}>
                  {order.status || 'PLACED'}
                </div>
                <div className="order-amount" style={{marginTop: '0.5rem'}}>
                  ₹{order.amount?.toFixed(2) || '0.00'}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default OrderDashboard;
