use game.db;

-- users

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    nickname TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL
);

INSERT INTO users (nickname, senha) VALUES ('admin', 'admin');

SELECT * FROM users;

-- itens

DROP TABLE IF EXISTS itens;

CREATE TABLE itens (
    item_id INTEGER PRIMARY KEY AUTOINCREMENT,
    item TEXT NOT NULL,
    type TEXT NOT NULL,
    description TEXT
);

INSERT INTO itens (item, type, description)
VALUES ('Monza', 'Carro', 'GM Chevrolet Monza Tubarão 1991 2.0 Gasolina');

INSERT INTO itens (item, type, description) 
VALUES ('Gol', 'Carro','VW Goleta 1993 Apzão 1.8 Álcool');

DELETE FROM itens WHERE item_id = '3';

SELECT * FROM itens;

-- inventory

DROP TABLE IF EXISTS inventory;

CREATE TABLE inventory (
    inventory_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL,
    quantity INTEGER DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (item_id) REFERENCES items(item_id),
    UNIQUE(user_id, item_id)
);

-- game

DROP TABLE IF EXISTS game;

CREATE TABLE game (
    game_id INTEGER PRIMARY KEY AUTOINCREMENT,
    canon BOOLEAN NOT NULL DEFAULT 1,
    lore TEXT NOT NULL,
    events TEXT NOT NULL,
    requires TEXT NOT NULL
)

INSERT INTO game (canon, lore, events, requires)
            values ( '1', 'era uma vez', 'cena1', 'Carro');

SELECT * FROM game;
