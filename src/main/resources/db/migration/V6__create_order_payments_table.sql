CREATE TABLE
    order_payments (
        id INT AUTO_INCREMENT PRIMARY KEY,
        order_id INT NOT NULL UNIQUE,
        payment_method VARCHAR(100) NOT NULL,
        payment_status VARCHAR(50) NOT NULL,
        payment_amount DECIMAL(10, 2) NOT NULL,
        transaction_id VARCHAR(255) NULL,
        paid_at TIMESTAMP NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );