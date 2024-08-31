-- EXERCÍCIOS

-- 1
select * from produto
where UNIDADE = 'UN';

-- 2
select * from produto
where CODIGO_CLASSIFICACAO = '020'
and QUANTIDADE > 20;

-- 3
select * from produto
where DESCRICAO like 'bala%'
and CODIGO_CLASSIFICACAO = '003'
and QUANTIDADE > 6;

-- 4
select * from produto p 
inner join classificacao c 
on p.CODIGO_CLASSIFICACAO = c.CODIGO 
where p.DESCRICAO like '%martelo%'
and c.DESCRICAO <> 'Materiais de Construção';

-- 5
select * from produto p 
inner join classificacao c 
on p.CODIGO_CLASSIFICACAO = c.CODIGO 
where p.QUANTIDADE < 5
and p.UNIDADE = 'CX'
and c.DESCRICAO = 'EPI/EPC';

-- 6
select * from produto p 
inner join classificacao c 
on p.CODIGO_CLASSIFICACAO = c.CODIGO 
where p.QUANTIDADE >= 10 and P.QUANTIDADE <= 50
and p.UNIDADE != 'CX'
and c.DESCRICAO = 'EPI/EPC';

-- 7
select * from produto p 
inner join classificacao c 
on p.CODIGO_CLASSIFICACAO = c.CODIGO 
where C.DESCRICAO = 'UNIFORMES'
and p.DESCRICAO like '%CAMISETA%'
or c.DESCRICAO = 'Materiais esportivos'
and P.DESCRICAO like '%BOLA%';

-- 8
select count(*) from produto p 
inner join classificacao c 
on p.CODIGO_CLASSIFICACAO = c.CODIGO 
where c.DESCRICAO = 'Produtos Alimentícios'
and p.UNIDADE = 'PCT';

-- 9
select distinct p.UNIDADE
from produto p 
inner join classificacao c 
on p.CODIGO_CLASSIFICACAO = c.CODIGO 
where c.DESCRICAO = 'Veterinária';

-- 10
select count(*) from produto
where QUANTIDADE > 6 and QUANTIDADE < 10;

-- 11
select count(*) from produto
where UNIDADE = 'L'
and DESCRICAO like 'oleo%';

-- 12
select *, (QUANTIDADE * VALOR) Total
from produto
where CODIGO_CLASSIFICACAO = '015'
and UNIDADE = 'CX';











