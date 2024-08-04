use game.db;



-- users

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    nickname TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL
);

INSERT INTO users (nickname, senha) VALUES ('admin', '123adm');

SELECT * FROM users;

-- game

DROP TABLE IF EXISTS game;

CREATE TABLE game (
    game_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    prog INT,
    inventory TEXT,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
)

INSERT INTO game (name, prog, inventory, user_id)
            values ('jogo1', 0, 'none', 1);

SELECT * FROM game;
