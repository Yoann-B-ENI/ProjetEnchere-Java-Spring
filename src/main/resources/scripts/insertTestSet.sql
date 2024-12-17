

-- Insert Members
INSERT INTO members (userName, password, name, firstName, email, phoneNumber, roadName, zipCode, townName, credits, admin)
VALUES 
('john_doe', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Doe', 'John', 'john.doe@example.com', '123-456-7890', 'Main St', '12345', 'Springfield', 100, TRUE),
('jane_smith', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Smith', 'Jane', 'jane.smith@example.com', '987-654-3210', 'Oak Rd', '54321', 'Greenville', 50, FALSE),
('bob_jones', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Jones', 'Bob', 'bob.jones@example.com', '555-123-4567', 'Pine Ave', '11223', 'Riverside', 200, FALSE);

-- Insert Categories
INSERT INTO categories (name)
VALUES 
('Electronics'),
('Furniture'),
('Books'),
('Clothing'),
('Toys');

-- Insert Removal Points
INSERT INTO removalPoints (roadName, zipCode, townName)
VALUES 
('Elm St', '65432', 'Centerville'),
('Maple Ave', '78901', 'Lakeview'),
('Broadway', '23456', 'Hilltop');

-- Insert Articles
INSERT INTO articles (name, description, auctionStartDate, auctionEndDate, 
startingPrice, salePrice, status, idVendor, idCategory, idRemovalPoint)
VALUES 
('Laptop', 'A high-performance laptop with 16GB RAM and 512GB SSD', '2025-01-02', '2025-01-30', 
500, NULL, 'Created', 1, 1, 1),  -- John Doe is the vendor (idVendor 1)
('Sofa', 'Comfortable 3-seater sofa with fabric upholstery', '2024-12-05', '2024-12-29', 
300, NULL, 'AuctionStarted', 2, 2, 2),  -- Jane Smith is the vendor (idVendor 2)
('Novel - "The Great Adventure"', 'A thrilling novel about an epic journey', '2024-12-02', '2024-12-09', 
20, NULL, 'AuctionEnded', 3, 3, 3);  -- Bob Jones is the vendor (idVendor 3)

-- Insert Bids
INSERT INTO bids (idMember, idArticle, bidDate, bidPrice)
VALUES 
(2, 1, '2024-12-03', 550),  -- Jane Smith bidding on the Laptop
(3, 2, '2024-12-06', 350),  -- Bob Jones bidding on the Sofa
(1, 3, '2024-12-04', 25);   -- John Doe bidding on the Novel



