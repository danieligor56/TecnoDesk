-- SQL Script to create OS_HISTORICO table
-- Run this in your PostgreSQL database

CREATE TABLE IF NOT EXISTS os_historico (
    id BIGSERIAL PRIMARY KEY,
    os_id BIGINT NOT NULL,
    descricao VARCHAR(1000) NOT NULL,
    dt_alteracao VARCHAR(255) NOT NULL,
    responsavel VARCHAR(255) NOT NULL,
    empresa_id BIGINT NOT NULL,
    CONSTRAINT fk_historico_os FOREIGN KEY (os_id) REFERENCES os_abertura(cod_os),
    CONSTRAINT fk_historico_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

-- Create index for better query performance
CREATE INDEX IF NOT EXISTS idx_historico_os_id ON os_historico(os_id);
CREATE INDEX IF NOT EXISTS idx_historico_empresa_id ON os_historico(empresa_id);
