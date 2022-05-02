INSERT INTO account(account_code, account_username, account_password, account_type, created_at, updated_at) values("DOCTOR0001", "username", "{bcrypt}$2a$10$fMZSzFrq4nrj8QxAX6ISFOQ11vIOMExhyodCXtHvwyRCmUTZMBRmy", "ACCOUNT_DOCTOR", "2022-04-16 12:00:00.000000", "2022-04-16 12:00:00.000000");
INSERT INTO doctor(doctor_major, doctor_name, doctor_code) values("NONE", "Kim", "DOCTOR0001");

INSERT INTO doctor_code(code) values("DOCTOR001");