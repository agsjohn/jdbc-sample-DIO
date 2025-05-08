CREATE TABLE accesses(
    id BIGINT not null auto_increment,
    employee_id BIGINT not null,
    module_id BIGINT not null,
    PRIMARY KEY(id),
    CONSTRAINT fk_accesses_employees FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_accesses_modules FOREIGN KEY (module_id) REFERENCES modules(id) ON DELETE CASCADE ON UPDATE CASCADE
)engine=InnoDB default charset=utf8;
