CREATE TABLE example(
  example_id VARCHAR(36) NOT NULL COMMENT 'UUID: 0e8400-e29b-41d4-a716-446655440000',
  description VARCHAR(255) NOT NULL COMMENT 'Descripción: Entidad nueva',
  enabled     BIT DEFAULT 1    NOT NULL COMMENT 'Define está eliminado logicamente: [0: eliminado, 1: activo]',
  status      bigint(10)       NOT NULL COMMENT 'Estado: [1: ACTIVO, 2: BLOQUEADO]',
  created_user VARCHAR(255) NOT NULL COMMENT 'Usuario de Creación del Registro',
  created_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Fecha de Creación del Registro',
  last_modified_user VARCHAR(255) NULL COMMENT 'Usuario de Última Modificación del Registro',
  last_modified_date DATETIME NULL COMMENT 'Fecha de Última Modificación del Registro',
  PRIMARY KEY (example_id)
)
