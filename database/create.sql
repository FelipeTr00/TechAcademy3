-- DROP TABLE users;
-- DROP TABLE progress;
-- DROP TABLE scenes;
-- DROP TABLE items;
-- DROP TABLE scene_items;
-- DROP TABLE inventory;

CREATE TABLE scenes (
    scene_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    descr TEXT NOT NULL,
    scene TEXT NOT NULL,
    error TEXT NOT NULL,
    command TEXT NOT NULL,
    target_scene INTEGER,
    FOREIGN KEY (target_scene) REFERENCES scenes(scene_id)
);

CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome TEXT NOT NULL,
    passwd TEXT NOT NULL
);

CREATE TABLE items (
    item_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    item_name TEXT NOT NULL,
    item_descr TEXT NOT NULL
);

CREATE TABLE progress (
    prog_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER,
    scene_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (scene_id) REFERENCES scenes(scene_id)
);

CREATE TABLE scene_items (
    scene_id INTEGER,
    item_id INTEGER,
    PRIMARY KEY (scene_id, item_id),
    FOREIGN KEY (scene_id) REFERENCES scenes(scene_id),
    FOREIGN KEY (item_id) REFERENCES items(item_id)
);

CREATE TABLE inventory (
    scene_id INTEGER,
    item_id INTEGER,
    PRIMARY KEY (scene_id, item_id),
    FOREIGN KEY (scene_id) REFERENCES scenes(scene_id),
    FOREIGN KEY (item_id) REFERENCES items(item_id)
);
