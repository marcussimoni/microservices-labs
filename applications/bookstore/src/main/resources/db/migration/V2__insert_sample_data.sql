-- Set search path to use the bookstore schema
SET search_path TO bookstore;

-- Insert sample books
INSERT INTO bookstore.books (title, author, description, price, cover_image, stock)
VALUES 
('The Great Gatsby', 'F. Scott Fitzgerald', 'A story of decadence and excess, Gatsby explores the darker aspects of the American Dream.', 12.99, '', 10),
('1984', 'George Orwell', 'A dystopian novel set in a totalitarian society ruled by the Party, which has total control over every aspect of people''s lives.', 9.99, '', 8),
('The Hobbit', 'J.R.R. Tolkien', 'A fantasy novel about the adventures of Bilbo Baggins, a hobbit who embarks on an unexpected journey.', 14.99, '', 7),
('The Lord of the Rings', 'J.R.R. Tolkien', 'An epic high-fantasy novel that follows the quest to destroy the One Ring.', 24.99, '', 6),
('Atomic Habits', 'James Clear', 'A guide to building good habits and breaking bad ones with proven strategies.', 16.99, '', 25),
('Dune', 'Frank Herbert', 'A science fiction masterpiece set in a distant future amidst a feudal interstellar society.', 12.99, '', 10);