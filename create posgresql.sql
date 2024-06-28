-- Role table to store role names
CREATE TABLE roles (
  role_id SERIAL PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

-- User table to store user information
CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  profile_image VARCHAR(255) DEFAULT NULL,
  address VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  email_verified BOOLEAN DEFAULT FALSE,
  confirmation_token VARCHAR(255) DEFAULT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  role_id INT NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

-- Contact message table to store messages sent by users
CREATE TABLE contact_messages (
  contact_message_id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  message TEXT NOT NULL
);

-- Product category table to store category names of products
CREATE TABLE product_categories (
  category_id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

-- Product table to store product information
CREATE TABLE products (
  product_id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  size VARCHAR(50) NOT NULL,
  color VARCHAR(255) NOT NULL,
  gender VARCHAR(10) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  image VARCHAR(255) DEFAULT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category_id INT NOT NULL,
  FOREIGN KEY (category_id) REFERENCES product_categories (category_id)
);

-- Order table to store user orders
CREATE TABLE orders (
  order_id SERIAL PRIMARY KEY,
  user_id INT,
  order_total DECIMAL(10,2) NOT NULL,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

-- Order item table to store item order that references order
CREATE TABLE order_items (
  order_item_id SERIAL PRIMARY KEY,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  total_price DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES products (product_id)
);

-- User behavior tracking table (simplified)
CREATE TABLE user_behaviors (
  behavior_id SERIAL PRIMARY KEY,
  user_id INT,
  product_id INT,
  behavior_type VARCHAR(50) NOT NULL, -- view, purchase
  behavior_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (user_id),
  FOREIGN KEY (product_id) REFERENCES products (product_id)
);

-- User liked products
CREATE TABLE user_likes (
  like_id SERIAL PRIMARY KEY,
  user_id INT,
  product_id INT,
  FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES products (product_id)
);

-- Products in user shopping cart
CREATE TABLE user_shopping_carts (
  cart_id SERIAL PRIMARY KEY,
  user_id INT,
  product_id INT,
  FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES products (product_id)
);
