
DROP TABLE IF EXISTS Employee;


CREATE TABLE Employee (
  employee_id VARCHAR(250) PRIMARY KEY,
  name VARCHAR(250),
  email VARCHAR(250),
  salary VARCHAR(250)
);

DROP TABLE IF EXISTS Transaction_logger;

CREATE TABLE Transaction_logger (
  id NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1) PRIMARY KEY,
  transaction_id VARCHAR(250) ,
  class_name VARCHAR(250) ,
  method_name VARCHAR(250),
  request_data CLOB,
  response_data CLOB,
  execution_time DATE 
);

INSERT INTO Employee (employee_id,name, email, salary) VALUES ('001','aashna', 'aashna@gmail.com', '500');
INSERT INTO Employee (employee_id,name, email, salary) VALUES  ('002','simran', 'simmran@gmail.com', '600');
INSERT INTO Employee (employee_id,name, email, salary) VALUES ('003','anusha', 'anusha@gmail.com', '700');


