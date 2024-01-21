INSERT INTO role VALUES (1, "ROLE_USER"), (2, "ROLE_ADMIN");

INSERT INTO product_category VALUES (1, "Shirts"), (2, "Jackets"), (3, "Pants"), (4, "Shoes"), (5, "Handbags"), (6, "Accessories");

-- Shirts
INSERT INTO product (name, description, size, color, gender, price, category_id) VALUES
  ('Casual Shirt', 'Comfortable cotton shirt for everyday wear', 'M', 'Blue', 'Men', 29.99, 1),
  ('Formal Blouse', 'Elegant blouse suitable for formal occasions', 'S', 'White', 'Women', 39.99, 1),
  ('Striped Polo', 'Casual polo shirt with stripes pattern', 'L', 'Red/White', 'Children', 24.99, 1);

-- Jackets
INSERT INTO product (name, description, size, color, gender, price, category_id) VALUES
  ('Leather Jacket', 'Classic leather jacket for a stylish look', 'XL', 'Black', 'Men', 149.99, 2),
  ('Denim Jacket', 'Casual denim jacket for a laid-back style', 'M', 'Blue', 'Women', 79.99, 2),
  ('Windbreaker', 'Lightweight windbreaker for outdoor activities', 'L', 'Green', 'Children', 54.99, 2);

-- Pants
INSERT INTO product (name, description, size, color, gender, price, category_id) VALUES
  ('Slim Fit Jeans', 'Trendy slim fit jeans for a modern look', '32x30', 'Dark Blue', 'Men', 49.99, 3),
  ('Cargo Pants', 'Comfortable cargo pants with multiple pockets', '28x32', 'Khaki', 'Women', 39.99, 3),
  ('Athletic Joggers', 'Athletic jogger pants for a sporty style', 'XL', 'Gray', 'Children', 29.99, 3);

-- Shoes
INSERT INTO product (name, description, size, color, gender, price, category_id) VALUES
  ('Running Shoes', 'Durable running shoes for active individuals', '9', 'Black/White', 'Children', 89.99, 4),
  ('High Heels', 'Elegant high heels for special occasions', '7', 'Red', 'Women', 69.99, 4),
  ('Casual Sneakers', 'Comfortable sneakers for everyday wear', '10', 'Navy Blue', 'Men', 54.99, 4);

-- Handbags
INSERT INTO product (name, description, size, color, gender, price, category_id) VALUES
  ('Leather Tote Bag', 'Spacious leather tote bag for daily essentials', '', 'Brown', 'Women', 119.99, 5),
  ('Crossbody Purse', 'Compact crossbody purse for on-the-go style', '', 'Black', 'Women', 49.99, 5),
  ('Canvas Backpack', 'Casual canvas backpack for a laid-back look', '', 'Gray', 'Children', 34.99, 5);

-- Accessories
INSERT INTO product (name, description, size, color, gender, price, category_id) VALUES
  ('Classic Watch', 'Timeless wristwatch for a polished appearance', '', 'Silver', 'Children', 79.99, 6),
  ('Statement Necklace', 'Bold statement necklace for a fashionable look', '', 'Gold', 'Women', 29.99, 6),
  ('Leather Belt', 'Stylish leather belt to complete your outfit', '36', 'Brown', 'Men', 19.99, 6);


update product set image = "https://i.ibb.co/QP29Svp/1.webp" WHERE product_id = 1;
update product set image = "https://i.ibb.co/k3K2ZSh/2.webp" WHERE product_id = 2;
update product set image = "https://i.ibb.co/q0qXhTY/3.webp" WHERE product_id = 3;
update product set image = "https://i.ibb.co/t4Mp81g/4.webp" WHERE product_id = 4;
update product set image = "https://i.ibb.co/hyVj4WJ/5.webp" WHERE product_id = 5;
update product set image = "https://i.ibb.co/RgDWmp5/6.webp" WHERE product_id = 6;
update product set image = "https://i.ibb.co/BsL5sFB/7.webp" WHERE product_id = 7;
update product set image = "https://i.ibb.co/Br74zfP/8.webp" WHERE product_id = 8;
update product set image = "https://i.ibb.co/y4jpXXD/9.webp" WHERE product_id = 9;
update product set image = "https://i.ibb.co/fGSnDkc/10.webp" WHERE product_id = 10;
update product set image = "https://i.ibb.co/BgV2sHh/11.webp" WHERE product_id = 11;
update product set image = "https://i.ibb.co/Z8cTH9K/12.webp" WHERE product_id = 12;
update product set image = "https://i.ibb.co/C19NWcq/13.webp" WHERE product_id = 13;
update product set image = "https://i.ibb.co/Hxqs0tV/14.webp" WHERE product_id = 14;
update product set image = "https://i.ibb.co/1LZtNVj/15.webp" WHERE product_id = 15;
update product set image = "https://i.ibb.co/Vm9zwg8/16.webp" WHERE product_id = 16;
update product set image = "https://i.ibb.co/bPw8GPb/17.webp" WHERE product_id = 17;
update product set image = "https://i.ibb.co/Nt53mk5/18.webp" WHERE product_id = 18;