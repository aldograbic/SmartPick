INSERT INTO roles (role_id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO product_categories (category_id, name) VALUES (1, 'Shirts'), (2, 'Jackets'), (3, 'Pants'), (4, 'Shoes'), (5, 'Handbags'), (6, 'Accessories');

-- Shirts
INSERT INTO products (name, description, size, color, gender, price, category_id, image) VALUES
  ('Casual Shirt', 'Comfortable cotton shirt for everyday wear', 'M', 'Blue', 'Men', 29.99, 1, 'https://i.ibb.co/QP29Svp/1.webp'),
  ('Formal Blouse', 'Elegant blouse suitable for formal occasions', 'S', 'White', 'Women', 39.99, 1, 'https://i.ibb.co/k3K2ZSh/2.webp'),
  ('Striped Polo', 'Casual polo shirt with stripes pattern', 'L', 'Red/White', 'Children', 24.99, 1, 'https://i.ibb.co/q0qXhTY/3.webp'),
  ('Checked Button-down', 'Casual checked button-down shirt for a versatile look', 'XL', 'Green/Blue', 'Men', 27.99, 1, 'https://i.ibb.co/SPvCYSb/19.webp'),
  ('Kids Graphic Tee', 'Fun graphic tee for kids with playful design', 'S', 'Green', 'Children', 19.99, 1, 'https://i.ibb.co/GCQwtWc/20.webp'),
  ('Graphic Tee', 'Stylish graphic t-shirt for casual wear', 'L', 'Black', 'Men', 19.99, 1, 'https://i.ibb.co/SVdsWZC/21.webp'),
  ('Silk Top', 'Chic silk top for an elegant look', 'M', 'Beige', 'Women', 49.99, 1, 'https://i.ibb.co/5smg48K/22.webp');

-- Jackets
INSERT INTO products (name, description, size, color, gender, price, category_id, image) VALUES
  ('Leather Jacket', 'Classic leather jacket for a stylish look', 'XL', 'Black', 'Men', 149.99, 2, 'https://i.ibb.co/t4Mp81g/4.webp'),
  ('Denim Jacket', 'Casual denim jacket for a laid-back style', 'M', 'Blue', 'Women', 79.99, 2, 'https://i.ibb.co/hyVj4WJ/5.webp'),
  ('Windbreaker', 'Lightweight windbreaker for outdoor activities', 'L', 'Green', 'Children', 54.99, 2, 'https://i.ibb.co/RgDWmp5/6.webp'),
  ('Quilted Bomber Jacket', 'Quilted bomber jacket for a sporty yet stylish appearance', 'M', 'Blue', 'Men', 89.99, 2, 'https://i.ibb.co/p2g6cxW/23.webp'),
  ('Faux Fur Coat', 'Chic faux fur coat to stay warm in winter', 'S', 'Pink', 'Women', 129.99, 2, 'https://i.ibb.co/CBV6xkZ/24.webp'),
  ('Kids Hooded Jacket', 'Hooded jacket for kids to stay cozy in colder weather', 'L', 'Purple', 'Children', 44.99, 2, 'https://i.ibb.co/cYm5GZV/25.webp');

-- Pants
INSERT INTO products (name, description, size, color, gender, price, category_id, image) VALUES
  ('Slim Fit Jeans', 'Trendy slim fit jeans for a modern look', '32x30', 'Dark Blue', 'Men', 49.99, 3, 'https://i.ibb.co/BsL5sFB/7.webp'),
  ('Cargo Pants', 'Comfortable cargo pants with multiple pockets', '28x32', 'Khaki', 'Women', 39.99, 3, 'https://i.ibb.co/Br74zfP/8.webp'),
  ('Athletic Joggers', 'Athletic jogger pants for a sporty style', 'XL', 'Gray', 'Children', 29.99, 3, 'https://i.ibb.co/y4jpXXD/9.webp'),
  ('Chino Trousers', 'Classic chino trousers for a polished look', '34x32', 'Beige', 'Men', 54.99, 3, 'https://i.ibb.co/89hrvq8/26.webp'),
  ('Kids Sweatpants', 'Cozy sweatpants for active kids', 'M', 'Blue', 'Children', 24.99, 3, 'https://i.ibb.co/yQGBptd/27.webp'),
  ('Wide Leg Pants', 'Fashionable wide leg pants for comfort and style', 'M', 'Black', 'Women', 59.99, 3, 'https://i.ibb.co/0CtRHBz/28.webp');

-- Shoes
INSERT INTO products (name, description, size, color, gender, price, category_id, image) VALUES
  ('Running Shoes', 'Durable running shoes for active individuals', '9', 'Black/White', 'Children', 89.99, 4, 'https://i.ibb.co/fGSnDkc/10.webp'),
  ('High Heels', 'Elegant high heels for special occasions', '7', 'Red', 'Women', 69.99, 4, 'https://i.ibb.co/BgV2sHh/11.webp'),
  ('Casual Sneakers', 'Comfortable sneakers for everyday wear', '10', 'Navy Blue', 'Men', 54.99, 4, 'https://i.ibb.co/Z8cTH9K/12.webp'),
  ('Hiking Boots', 'Sturdy hiking boots for outdoor adventures', '8', 'Brown', 'Men', 79.99, 4, 'https://i.ibb.co/ZVzLTNZ/29.webp'),
  ('Ankle Strap Sandals', 'Trendy ankle strap sandals for warm days', '6', 'Brown', 'Women', 39.99, 4, 'https://i.ibb.co/jyHcwZF/30.webp'),
  ('Kids Velcro Sneakers', 'Easy-to-wear velcro sneakers for kids', '11', 'Pink/Blue', 'Children', 29.99, 4, 'https://i.ibb.co/KLW7Yxm/31.webp'),
  ('Leather Boots', 'Durable leather boots for rugged style', '11', 'Black', 'Men', 119.99, 4, 'https://i.ibb.co/YWFNS7Y/32.webp'),
  ('Ballet Flats', 'Comfortable and stylish ballet flats', '8', 'Pink', 'Women', 49.99, 4, 'https://i.ibb.co/gmFLRrR/33.webp');

-- Handbags
INSERT INTO products (name, description, size, color, gender, price, category_id, image) VALUES
  ('Leather Tote Bag', 'Spacious leather tote bag for daily essentials', '', 'Brown', 'Women', 119.99, 5, 'https://i.ibb.co/C19NWcq/13.webp'),
  ('Crossbody Purse', 'Compact crossbody purse for on-the-go style', '', 'Black', 'Women', 49.99, 5, 'https://i.ibb.co/Hxqs0tV/14.webp'),
  ('Canvas Backpack', 'Casual canvas backpack for a laid-back look', '', 'Gray', 'Children', 34.99, 5, 'https://i.ibb.co/1LZtNVj/15.webp'),
  ('Satchel Bag', 'Chic satchel bag for a sophisticated look', '', 'Brown', 'Women', 89.99, 5, 'https://i.ibb.co/LYPGQDs/34.webp'),
  ('Messenger Bag', 'Stylish messenger bag for a modern and casual vibe', '', 'Olive Green', 'Men', 59.99, 5, 'https://i.ibb.co/F4GKxr2/35.webp'),
  ('Kids Mini Backpack', 'Adorable mini backpack for little ones', '', 'Purple/Pink', 'Children', 29.99, 5, 'https://i.ibb.co/cXLKqLQ/36.webp'),
  ('Weekender Bag', 'Large weekender bag for short trips', '', 'Navy Blue', 'Women', 89.99, 5, 'https://i.ibb.co/WcpbH0B/37.webp'),
  ('Sports Backpack', 'Durable backpack for sports or school', '', 'Blue', 'Children', 49.99, 5, 'https://i.ibb.co/VH9MCk3/38.webp');

-- Accessories
INSERT INTO products (name, description, size, color, gender, price, category_id, image) VALUES
  ('Classic Watch', 'Timeless wristwatch for a polished appearance', '', 'Silver', 'Children', 79.99, 6, 'https://i.ibb.co/Vm9zwg8/16.webp'),
  ('Statement Necklace', 'Bold statement necklace for a fashionable look', '', 'Gold', 'Women', 29.99, 6, 'https://i.ibb.co/bPw8GPb/17.webp'),
  ('Leather Belt', 'Stylish leather belt to complete your outfit', '36', 'Brown', 'Men', 19.99, 6, 'https://i.ibb.co/Nt53mk5/18.webp'),
  ('Smartwatch', 'Modern smartwatch with advanced features', '', 'Black', 'Men', 129.99, 6, 'https://i.ibb.co/RTG4CkJ/39.webp'),
  ('Boho Earrings', 'Bohemian-inspired earrings for a free-spirited style', '', 'Turquoise', 'Women', 24.99, 6, 'https://i.ibb.co/dGhtzRF/40.webp'),
  ('Kids Sunglasses', 'Cute sunglasses for kids with UV protection', '', 'Blue/Yellow', 'Children', 14.99, 6, 'https://i.ibb.co/nR4V3qZ/41.webp'),
  ('Sunglasses', 'Modern sunglasses for stylish sun protection', '', 'Black', 'Men', 49.99, 6, 'https://i.ibb.co/fpX2n1r/42.webp'),
  ('Silk Scarf', 'Elegant silk scarf for a touch of luxury', '', 'White/Blue', 'Women', 39.99, 6, 'https://i.ibb.co/QYWTrkf/43.webp');