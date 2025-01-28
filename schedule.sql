use schedule;

CREATE TABLE User (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIMEF
);

CREATE TABLE Schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    todo VARCHAR(200),
    author_id VARCHAR(50),
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (author_id) REFERENCES User(id)
);