DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);