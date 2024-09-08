# use bootcamp;
#
# 1) retornar os produtos da Classificação 003 e que a unidade de medida não seja 'UN'
# R: 139 registros;
#
select * from produto where CODIGO_CLASSIFICACAO = '003' and UNIDADE != 'UN';

# 2) Retornar os produtos da Classificação 003, com a unidade de medida 'UN' em que a quantidade
# seja entre 5 e 7 com o valor menor que 10;
# R: 27 registros;
#
select * from produto where CODIGO_CLASSIFICACAO = '003' and UNIDADE = 'UN'
                        and QUANTIDADE >= 5 AND QUANTIDADE <= 7 and VALOR < 10;

# 3) Valor total dos 'biscoito' da base de dados;
# R: 3021;
#
select sum(p.QUANTIDADE * p.VALOR) as Valor_Total from produto p
left join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
where p.DESCRICAO like '%biscoito%';

# 4) Validar se existe algum 'martelo' que não pertença a classificação material de Construção;
#
select * from produto p left join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
        where p.DESCRICAO like '%martelo%' and c.CODIGO != '001';

# 5) Retornar os produtos da classificação EPI que estejam em menos de 5 caixas;
# R: 2 registros;
#
select * from produto p inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
            where c.DESCRICAO like '%EPI%' and UNIDADE = 'CX' and QUANTIDADE < 5;

# 6) Retornar os produtos da Classificação EPI que NÃO ESTEJA em caixas e sua quantidade esteja em 10 e 50;
# R:9 registros;
#
select * from produto p inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
            where c.DESCRICAO like '%EPI%' and UNIDADE != 'CX' and QUANTIDADE >= 10 and QUANTIDADE <= 50;

# 7) Retornar todos registros da classificação UNIFORMES com o nome 'camiseta e todos os produtos da
# classificação MATERIAL ESPORTIVO e com nome 'bola'
# R: 11 registros;
#
select * from produto p inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
            where c.DESCRICAO like '%ESPORTIVO%' and p.DESCRICAO like '%bola%'
            or c.DESCRICAO like '%UNIFORME%' and p.DESCRICAO like '%camiseta%';

# 8) Retornar a média do valor dos produtos que a quantidade esteja entre 2 e 4, com valor inferior a 50,
# que não seja material de construção e que não seja um 'copo';
# R: 18.8688
#
select avg(p.VALOR) as Media_Valor from produto p inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
            where p.QUANTIDADE >= 2 and p.QUANTIDADE <= 4 and p.VALOR < 50
            and p.TIPO = 'P'
            and c.DESCRICAO not like '%CONSTRU%'
            and p.DESCRICAO not like '%copo%';

# 9) Retornar o quantidade total de pacotes ( PCT) dos produtos alimenticios
# R: 1165;
#
select sum(p.QUANTIDADE) as qtd from produto p inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
            where p.UNIDADE = 'PCT' and c.DESCRICAO like '%aliment%';


# 10) Retornar apenas o numero total de produtos cadastrados com unidade pacote e que seja da classificação de alimentos
# R: 23 produtos;
#
select count(p.QUANTIDADE) as qtd from produto p inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
            where p.UNIDADE = 'PCT' and c.DESCRICAO like '%aliment%';


# 11) Retornar qual é o maior valor de um produto do estoque, este deve ser o produto que sua quantidade * valor seja o maior
# R: 1134870;
#
select max(vl_total) as max_vl
from (
    select (p.QUANTIDADE * p.VALOR) as vl_total, p.*
    from produto p inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
    where p.TIPO = 'P'
     ) as sub_select;


# 12) Retornar o menor valor de um produto que a quantidade seja maior que 0 e que a unidade seja ‘UN’ e
# classificação alimentos
# R: 1;
#
select min(sub_select.VALOR) as valor_prod
from (
select p.VALOR from produto p inner join classificacao c
    on p.CODIGO_CLASSIFICACAO = c.CODIGO
    where p.QUANTIDADE > 0 and p.UNIDADE = 'UN' and c.DESCRICAO like '%aliment%') as sub_select;

#
# 13) Retornar é o valor total dos produtos da categoria ‘Material Hospitalares’
# R: 406355;
#
select sum(p.VALOR * p.QUANTIDADE) as total from produto p
    inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
            where c.DESCRICAO like '%hospital%'


# 14) Retornar TODOS os valores totais por categoria e ordenar por categoria
#
select c.DESCRICAO, sum(p.QUANTIDADE * p.VALOR) as valor_total from produto p
    inner join classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
group by c.DESCRICAO
order by c.DESCRICAO;


# 15) Retornar todos os tipos de ‘UNIDADE’ da classificação Veterinária
# R: 12;
#
select distinct p.UNIDADE from produto p
inner join  classificacao c on p.CODIGO_CLASSIFICACAO = c.CODIGO
where c.DESCRICAO like '%veterin%'

# 16) Contar Quantos produtos são da categoria de Aviamentos por unidade.
# EX: (20 produtos - UN; 2 PRODUTOS - PCT)

select p.UNIDADE, sum(p.QUANTIDADE) as qtd from produto p inner join classificacao c
on p.CODIGO_CLASSIFICACAO = c.CODIGO
where c.DESCRICAO = 'Aviamentos'
group by p.UNIDADE;
