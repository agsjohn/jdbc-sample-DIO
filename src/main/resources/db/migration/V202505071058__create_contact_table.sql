CREATE TABLE contacts(
    id BIGINT not null auto_increment,
    description VARCHAR(50) not null,
    type VARCHAR(30) not null,
    employee_id BIGINT not null,
    CONSTRAINT fk_contacts_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8;
