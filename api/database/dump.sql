-- CREATE DATABASE game;
-- DROP DATABASE game;
 USE game;

CREATE TABLE scenes (
                        scene_id INTEGER PRIMARY KEY,
                        description TEXT NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        target INTEGER,
                        right_command VARCHAR(255) NOT NULL,
                        inventory VARCHAR(255) NOT NULL
);

-- DROP TABLE users;
CREATE TABLE users (
                       user_id INTEGER PRIMARY KEY,
                       name VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       current_scene  INTEGER DEFAULT 1,
                       FOREIGN KEY (current_scene) REFERENCES scenes(scene_id)
);

CREATE TABLE items (
                       item_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                       item VARCHAR(255) NOT NULL,
                       descr_item VARCHAR(255) NOT NULL,
                       scene_id INTEGER NOT NULL,
                       FOREIGN KEY (scene_id) REFERENCES scenes(scene_id)
);

INSERT INTO scenes (scene_id ,description, title, target, right_command, inventory)
VALUES

    -- Help
    (0 ,'help = Ajuda com os comandos do jogo, use = usar um item, get = adicionar item ao inventário, check = verificar ' ||
     'a descrição do item, save = salvar o jogo.'
      ,'help',1,'',''),

    -- Cena 01
    (1, 'Em um dia comum de trabalho, você encontra documentos que expõem uma trama criminosa envolvendo seus colegas.' ||
     ' Chocado, você decide não se envolver, mas sabe que está em perigo por ter descoberto demais. A tensão no' ||
     ' escritório aumenta, e pouco depois, você é convocado para uma reunião de emergência. Lá, armam contra você, te ' ||
        'acusando injustamente. Apesar de suas tentativas de se defender, você é demitido. O DINHEIRO do acerto está na ' ||
        'mesa junto dos PAPEIS da demissão.','O escritório da Firma!',2,'use dinheiro',''),

    -- Cena 02
    (2, 'Após a demissão, você passa a noite refletindo sobre o que fazer. Uma hora o dinheiro do acerto vai acabar,' ||
     ' você decide investir em um carro usado para poder iniciar uma nova fase da sua vida. No dia seguinte,' ||
     ' você entra na OLX e encontra dois carros que se encaixam no seu orçamento: um MONZA e um UNO. Cada carro tem' ||
        ' suas vantagens e desvantagens, e a escolha certa pode fazer a diferença na sua ' ||
        'jornada.','Você está de-mi-ti-do! E agora zé?',3,'use monza','dinheiro'),

    -- Cena 03
    (3, 'Já no carro, cuidadosamente preparado. Você precisa se decidir de algo muito importante, a trilha sonora! ' ||
     'Pega dois CDs para ouvir no RADIO do carro, SLAYER e PANTERA, qual ouvir nessa jornada?','Partiu Paraguay',4,
     'use pantera with radio','dinheiro, monza'),

    -- Cena 04
    (4, 'Com o carro preparado, você parte rumo ao Paraguai. As estradas são longas e esburacas, mas a adrenalina e a ' ||
     'determinação te mantém focado. Durante a viagem, você escuta Pantera no radio e se concentra em seus objetivos. ' ||
     'Ao chegar à fronteira, você pega seu MAPA e precisa decidir seu destino, CIUDAD_DEL_ESTE ou ' ||
     'PEDRO_JUAN_CABALLERO','As estradas no Brasil são uma m3Rd@!',5,'use pedro_juan_caballero','dinheiro, monza'),

    -- Cena 05
    (5, 'Em Pedro Juan Caballero, você se infiltra no mercado clandestino. As opções são variadas: WHISKY, CIGARRO,' ||
     ' e outros itens de alto valor estão disponíveis. Cada decisão que você toma aqui é crucial; qualquer erro ' ||
     'pode arruinar sua viagem de volta ao Brasil. Com o carro carregado, você precisa escolher como proceder para' ||
     ' garantir um lucro sem atrair atenção indesejada.','Dinheiro na mão!',6,'use derby','dinheiro, monza'),

    -- Cena 06
    (6, 'Final da história, agora é só continuar fazendo dinheiro pra comprar o MÉ, LEITE das crianças e o MODDESS' ||
     ' da muié.','Nunca é um final!',1 ,'use me','dinheiro, monza, derby');

INSERT INTO items (item, descr_item, scene_id)
VALUES
    ('dinheiro','Que é good e nóis não have...',1),
    ('papeis','Pelo menos é uma grana boa...',1),
    ('monza','O último carro de verdade feito pela GM.',2),
    ('uno','Um ótimo carro, mas não para ir pro PY.',2),
    ('slayer','Ótimo, mas pra viajar?',3),
    ('pantera','Melhor banda de todos os tempos.',3),
    ('radio','Instalei um RoadStar.',3),
    ('ciudad_del_este','Aquela ponte é muito vigiada rsrs...',4),
    ('pedro_juan_caballero','Fronteira seca... hummm tenho uma idéia!.',4),
    ('whisky','Não foi atrás de Red Label que vc veio aqui...',5),
    ('derby','Enche o carro de maços e maços de derby cabrón!',5),
    ('me','51, uma boa idéia.',6),
    ('leite','Leite das crianças.',6),
    ('moddess','Pra quem não sabe, absorvente.',6);


INSERT INTO users (user_id, name, password , current_scene)
VALUES
    (0 ,'help', '***', 0),
    (1 ,'1', '***', 1),
    (2 ,'2', '***', 2),
    (3 ,'3', '***', 3),
    (4 ,'4', '***', 4),
    (5 ,'5', '***', 5),
    (6 ,'6', '***', 6),
    (7 ,'root', '***', 1),
    (8 , 'user', 'user', 3);

