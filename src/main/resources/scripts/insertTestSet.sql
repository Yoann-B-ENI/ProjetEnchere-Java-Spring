

-- Insert Members
INSERT INTO members (userName, password, name, firstName, email, phoneNumber, 
roadNumber, roadName, zipCode, townName, credits, admin)
VALUES 
('john_doe', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Doe', 'John', 'john.doe@example.com', 
'0234567890', 5, 'Main St', '12345', 'Springfield', 100, TRUE),
('jane_smith', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Smith', 'Jane', 'jane.smith@example.com', 
'0876543210', 190, 'Oak Rd', '54321', 'Greenville', 5000, FALSE),
('smith_smith', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Smith', 'Smith', 'smith.smith@example.com', 
'0876543210', 190, 'Oak Rd', '54321', 'Greenville', 0, FALSE),
('bob_jones', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Jones', 'Bob', 'bob.jones@example.com', 
'0551234567', 34, 'Pine Ave', '11223', 'Riverside', 200, FALSE);

-- Insert Categories
INSERT INTO categories (name)
VALUES 
('Electronics'),
('Furniture'),
('Books'),
('Clothing'),
('Toys');

-- Insert Removal Points
INSERT INTO removalPoints (roadNumber, roadName, zipCode, townName, idMember, pointName)
VALUES 
(5, 'Main St', '12345', 'Springfield', 1, 'Home JD'),
(190, 'Oak Rd', '54321', 'Greenville', 2, 'My House'),
(190, 'Oak Rd', '54321', 'Greenville', 3, 'my wife_s house <3'),
(34, 'Pine Ave', '11223', 'Riverside', 4, 'pine house'), 
(45, 'Rue de lassociation', '44880', 'Nantes', 1, 'Association'),
(45, 'Rue de lassociation', '44880', 'Nantes', 2, 'Auction House Warehouse'),
(45, 'Rue de lassociation', '44880', 'Nantes', 3, 'assoc'),
(45, 'Rue de lassociation', '44880', 'Nantes', 4, 'auction storage');

-- Insert Articles
INSERT INTO articles (name, description, auctionStartDate, auctionEndDate, 
startingPrice, salePrice, status, idVendor, idBuyer, idCategory, idRemovalPoint)
VALUES 
('Laptop', 'A high-performance laptop with 16GB RAM and 512GB SSD', '2025-01-20', '2025-01-30', 
500, 500, 'Created', 1, null, 1, 1),  -- John Doe is the vendor (idVendor 1)
('Sofa', 'Comfortable 3-seater sofa with fabric upholstery', '2024-12-05', '2025-01-19', 
300, 581, 'AuctionStarted', 2, 4, 2, 2),  -- Jane Smith is the vendor (idVendor 2)
('Novel - "The Great Adventure"', 'A thrilling novel about an epic journey', '2024-12-02', '2024-12-09', 
20, 36, 'AuctionEnded', 4, 4, 3, 3),  -- Bob Jones is the vendor 

('Smartphone', 'Latest model smartphone with a 6.5-inch OLED screen', '2025-02-01', '2025-02-10', 
 200, 200, 'Created', 1, null, 1, 1),  -- John Doe is the vendor
('Dining Table', 'Stylish wooden dining table for 6 people', '2025-02-05', '2025-02-15', 
 150, 150, 'Created', 2, null, 2, 2),  -- Jane Smith is the vendor
('Python Programming Book', 'Comprehensive guide to Python programming', '2025-01-01', '2025-01-04', 
 25, 35, 'AuctionEnded', 3, 2, 3, 3),  -- Bob Jones is the vendor
('Bicycle', 'Mountain bike with 21-speed gear system', '2025-02-10', '2025-02-20', 
 100, 100, 'Created', 4, null, 1, 4),  -- Bob Jones is the vendor
('Coffee Machine', 'High-end espresso machine with built-in grinder', '2025-01-06', '2025-03-10', 
 350, 350, 'AuctionStarted', 1, null, 1, 1),  -- John Doe is the vendor
('Washing Machine', 'Energy-efficient washing machine with quick wash cycle', '2025-01-01', '2025-03-25', 
 400, 400, 'AuctionStarted', 2, null, 2, 2),  -- Jane Smith is the vendor
('Action Figure', 'Limited edition superhero action figure', '2025-01-03', '2025-01-20', 
 50, 60, 'AuctionStarted', 3, 2, 4, 3),  -- Bob Jones is the vendor
('Leather Jacket', 'Genuine leather jacket in black', '2025-01-02', '2025-01-05', 
 90, 130, 'AuctionEnded', 4, 3, 2, 4),  -- Bob Jones is the vendor
('Drone', 'Drone with 4K camera and long battery life', '2025-01-25', '2025-02-05', 
 250, 250, 'Created', 1, null, 1, 1)  -- John Doe is the vendor
 ;

-- Insert Bids
INSERT INTO bids (idMember, idArticle, bidDate, bidPrice)
VALUES 
(2, 2, '2024-12-06', 350),  -- Jane Smith bidding on the Laptop
(4, 2, '2024-12-07', 351),  -- Bob Jones bidding on the Laptop
(2, 2, '2024-12-08', 380),  -- Jane Smith bidding on the Laptop
(4, 2, '2024-12-09', 381),  -- Bob Jones bidding on the Laptop
(1, 3, '2024-12-04', 25),   -- John Doe bidding on the Novel
(4, 3, '2024-12-06', 36),   -- Bob Jones bidding on the Novel

(4, 6, '2025-01-01', 26),   -- Bob Jones bidding on Python Programming Book (AuctionEnded)
(2, 6, '2025-01-02', 35),   -- Jane Smith bidding on Python Programming Book (AuctionEnded)
(4, 10, '2025-01-04', 55),   -- Bob Jones bidding on Action Figure (AuctionStarted)
(2, 10, '2025-01-05', 60),   -- Jane Smith bidding on Action Figure (AuctionStarted)
(2, 11, '2025-01-03', 120),  -- Jane Smith bidding on Leather Jacket (AuctionEnded)
(3, 11, '2025-01-04', 130)  -- Smith Smith bidding on Leather Jacket (AuctionEnded)
;


