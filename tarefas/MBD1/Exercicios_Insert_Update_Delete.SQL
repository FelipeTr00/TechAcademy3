-- INSERT

insert into cliente (id_cliente,
                     NOME,
                     TEL,
                     CPF,
                     RUA,
                     CEP,
                     CIDADE,
                     UF,
                     NR,
                     DT_CADASTRO,
                     DT_NASCIMENTO)
        values
                (10,
                'Peter Parker',
                 '44 999-888',
                 '000.010.111-10',
                 'Rua 5',
                 '98765-123',
                 'Cidade É Nois',
                 'DF',
                 100,
                 '2024-10-10',
                 '2000-01-01')

-- UPDATE

update cliente
set nome = 'José da Silva'
-- select * from cliente
where id_cliente = 1;

-- DELETE

delete from cliente
where id_cliente = 10;
