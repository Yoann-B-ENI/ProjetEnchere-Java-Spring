

-- Insert Members
INSERT INTO members (userName, password, name, firstName, email, phoneNumber, 
roadNumber, roadName, zipCode, townName, credits, admin)
VALUES 
('john_doe', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Doe', 'John', 'john.doe@example.com', 
'0234567890', 5, 'Main St', '12345', 'Springfield', 100, TRUE),
('jane_smith', '$2a$10$VzBJ.BwtXdnPOacBUMcm4.FAh4jhggmQCgPv5hEZrfbZ5S5xw.sHG', 'Smith', 'Jane', 'jane.smith@example.com', 
'0876543210', 190, 'Oak Rd', '54321', 'Greenville', 50, FALSE),
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
('Laptop', 'A high-performance laptop with 16GB RAM and 512GB SSD', '2025-01-02', '2025-01-30', 
500, 500, 'Created', 1, null, 1, 1),  -- John Doe is the vendor (idVendor 1)
('Sofa', 'Comfortable 3-seater sofa with fabric upholstery', '2024-12-05', '2024-12-29', 
300, 581, 'AuctionStarted', 2, null, 2, 2),  -- Jane Smith is the vendor (idVendor 2)
('Novel - "The Great Adventure"', 'A thrilling novel about an epic journey', '2024-12-02', '2024-12-09', 
20, 36, 'AuctionEnded', 4, 2, 3, 3);  -- Bob Jones is the vendor (idVendor 3)

-- Insert Bids
INSERT INTO bids (idMember, idArticle, bidDate, bidPrice)
VALUES 
(2, 2, '2024-12-06', 350),  -- Jane Smith bidding on the Laptop
(4, 2, '2024-12-07', 351),  -- Bob Jones bidding on the Laptop
(2, 2, '2024-12-08', 380),  -- Jane Smith bidding on the Laptop
(4, 2, '2024-12-09', 381),  -- Bob Jones bidding on the Laptop
(1, 3, '2024-12-04', 25);   -- John Doe bidding on the Novel
(4, 3, '2024-12-06', 36),   -- Bob Jones bidding on the Novel



