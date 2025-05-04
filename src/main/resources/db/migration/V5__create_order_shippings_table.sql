CREATE TABLE
    order_shippings (
        id INT AUTO_INCREMENT PRIMARY KEY,
        order_id INT NOT NULL,
        recipient_name VARCHAR(255) NOT NULL,
        recipient_phone VARCHAR(50) NOT NULL,
        address_line1 VARCHAR(255) NOT NULL,
        address_line2 VARCHAR(255),
        city VARCHAR(100) NOT NULL,
        postal_code VARCHAR(20) NOT NULL,
        country VARCHAR(100) NOT NULL,
        shipping_method VARCHAR(100),
        shipping_fee DECIMAL(10, 2),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );