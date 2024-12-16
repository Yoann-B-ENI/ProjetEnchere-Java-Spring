


-- Insert Users
INSERT INTO users (userName, password, name, firstName, email, phoneNumber, roadName, zipCode, townName, credits, admin)
VALUES 
('john_doe', 'securePass123', 'Doe', 'John', 'john.doe@example.com', '123-456-7890', 'Main St', '12345', 'Springfield', 100, TRUE),
('jane_smith', 'password456', 'Smith', 'Jane', 'jane.smith@example.com', '987-654-3210', 'Oak Rd', '54321', 'Greenville', 50, FALSE),
('bob_jones', 'mySecret789', 'Jones', 'Bob', 'bob.jones@example.com', '555-123-4567', 'Pine Ave', '11223', 'Riverside', 200, FALSE);

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
INSERT INTO articles (name, description, auctionStartDate, auctionEndDate, startingPrice, salePrice, idVendor, idCategory, idRemovalPoint)
VALUES 
('Laptop', 'A high-performance laptop with 16GB RAM and 512GB SSD', '2024-12-01', '2024-12-10', 500, NULL, 1, 1, 1),  -- John Doe is the vendor (idVendor 1)
('Sofa', 'Comfortable 3-seater sofa with fabric upholstery', '2024-12-05', '2024-12-12', 300, NULL, 2, 2, 2),  -- Jane Smith is the vendor (idVendor 2)
('Novel - "The Great Adventure"', 'A thrilling novel about an epic journey', '2024-12-02', '2024-12-09', 20, NULL, 3, 3, 3);  -- Bob Jones is the vendor (idVendor 3)

-- Insert Bids
INSERT INTO bids (idUser, idArticle, bidDate, bidPrice)
VALUES 
(2, 1, '2024-12-03', 550),  -- Jane Smith bidding on the Laptop
(3, 2, '2024-12-06', 350),  -- Bob Jones bidding on the Sofa
(1, 3, '2024-12-04', 25);   -- John Doe bidding on the Novel



