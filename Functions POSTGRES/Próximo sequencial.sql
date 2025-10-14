CREATE OR REPLACE FUNCTION fn_proximo_sequencial(
    p_codigo_empresa BIGINT,
    p_codigo_identificador_tabela BIGINT
)

RETURNS BIGINT AS
$$
DECLARE
    proximo_sequencial BIGINT;
BEGIN
    
    SELECT sequencial
    INTO proximo_sequencial
    FROM tabela_identificador
    WHERE codigo_empresa = p_codigo_empresa
      AND codigo_identificador_tabela = p_codigo_identificador_tabela
    FOR UPDATE;  

    
    IF NOT FOUND THEN
        proximo_sequencial := 1;
        INSERT INTO tabela_identificador (codigo_empresa, codigo_identificador_tabela, sequencial)
        VALUES (p_codigo_empresa, p_codigo_identificador_tabela, proximo_sequencial);
    ELSE
        
       proximo_sequencial := proximo_sequencial + 1;
        UPDATE tabela_identificador
        SET sequencial = proximo_sequencial
        WHERE codigo_empresa = p_codigo_empresa
          AND codigo_identificador_tabela = p_codigo_identificador_tabela;
    END IF;

    RETURN proximo_sequencial;
END;
$$ LANGUAGE plpgsql;


SELECT public.fn_proximo_sequencial(2, 8);

select * from referencia_tabela rt 
select * from tabela_identificador
SELECT sequencial FROM tabela_identificador WHERE codigo_empresa = 1 AND codigo_identificador_tabela = 8 


CREATE TABLE tabela_identificador (
    codigo_empresa BIGINT NOT NULL,
    codigo_identificador_tabela BIGINT NOT NULL,
    sequencial BIGINT NOT NULL,
    PRIMARY KEY (codigo_empresa, codigo_identificador_tabela)CREATE OR REPLACE FUNCTION fn_proximo_sequencial(
    p_codigo_empresa BIGINT,
    p_codigo_identificador_tabela BIGINT
)

RETURNS BIGINT AS
$$
DECLARE
    proximo_sequencial BIGINT;
BEGIN
    
    SELECT sequencial
    INTO proximo_sequencial
    FROM tabela_identificador
    WHERE codigo_empresa = p_codigo_empresa
      AND codigo_identificador_tabela = p_codigo_identificador_tabela
    FOR UPDATE;  

    
    IF NOT FOUND THEN
        proximo_sequencial := 1;
        INSERT INTO tabela_identificador (codigo_empresa, codigo_identificador_tabela, sequencial)
        VALUES (p_codigo_empresa, p_codigo_identificador_tabela, proximo_sequencial);
    ELSE
        
       proximo_sequencial := proximo_sequencial + 1;
        UPDATE tabela_identificador
        SET sequencial = proximo_sequencial
        WHERE codigo_empresa = p_codigo_empresa
          AND codigo_identificador_tabela = p_codigo_identificador_tabela;
    END IF;

    RETURN proximo_sequencial;
END;
$$ LANGUAGE plpgsql;


SELECT public.fn_proximo_sequencial(2, 8);

select * from referencia_tabela rt 
select * from tabela_identificador
SELECT sequencial FROM tabela_identificador WHERE codigo_empresa = 1 AND codigo_identificador_tabela = 8 


CREATE TABLE tabela_identificador (
    codigo_empresa BIGINT NOT NULL,
    codigo_identificador_tabela BIGINT NOT NULL,
    sequencial BIGINT NOT NULL,
    PRIMARY KEY (codigo_empresa, codigo_identificador_tabela)