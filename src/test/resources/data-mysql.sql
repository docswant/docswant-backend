INSERT INTO account(account_code, account_username, account_password, account_type, created_at, updated_at) values("DOCTOR001", "username", "{bcrypt}$2a$10$fMZSzFrq4nrj8QxAX6ISFOQ11vIOMExhyodCXtHvwyRCmUTZMBRmy", "ACCOUNT_DOCTOR", "2022-04-16 12:00:00.000000", "2022-04-16 12:00:00.000000");
INSERT INTO doctor(doctor_major, doctor_name, doctor_code) values("orthopedics", "zooneon", "DOCTOR001");

INSERT INTO doctor_code(code) values("DOCTOR001");

INSERT INTO account(account_code, account_username, account_password, account_type, created_at, updated_at) values("PATIENT001", "PATIENT001", "{bcrypt}$2a$10$fMZSzFrq4nrj8QxAX6ISFOQ11vIOMExhyodCXtHvwyRCmUTZMBRmy", "ACCOUNT_PATIENT", "2022-05-11 12:00:00.000000", "2022-05-11 12:00:00.000000");
INSERT INTO patient(patient_birth_date, patient_disease_name, patient_hospital_room, patient_name, patient_discharge_date, patient_hospitalization_date, patient_surgery_date, patient_code, doctor_code) values("1997-08-26", "COVID-19", 300, "zooneon", "2022-05-12", "2022-05-05", "2022-05-08", "PATIENT001", "DOCTOR001");

INSERT INTO account(account_code, account_username, account_password, account_type, created_at, updated_at) values("PATIENT002", "PATIENT002", "{bcrypt}$2a$10$fMZSzFrq4nrj8QxAX6ISFOQ11vIOMExhyodCXtHvwyRCmUTZMBRmy", "ACCOUNT_PATIENT", "2022-05-11 12:00:00.000000", "2022-05-11 12:00:00.000000");
INSERT INTO patient(patient_birth_date, patient_disease_name, patient_hospital_room, patient_name, patient_discharge_date, patient_hospitalization_date, patient_surgery_date, patient_code, doctor_code) values("1997-08-26", "COVID-19", 301, "zooneon", "2022-05-12", "2022-05-05", "2022-05-08", "PATIENT002", "DOCTOR001");

INSERT INTO account(account_code, account_username, account_password, account_type, created_at, updated_at) values("PATIENT003", "PATIENT003", "{bcrypt}$2a$10$fMZSzFrq4nrj8QxAX6ISFOQ11vIOMExhyodCXtHvwyRCmUTZMBRmy", "ACCOUNT_PATIENT", "2022-05-11 12:00:00.000000", "2022-05-11 12:00:00.000000");
INSERT INTO patient(patient_birth_date, patient_disease_name, patient_hospital_room, patient_name, patient_discharge_date, patient_hospitalization_date, patient_surgery_date, patient_code, doctor_code) values("1997-08-26", "COVID-19", 302, "zooneon", "2022-05-12", "2022-05-05", "2022-05-08", "PATIENT003", "DOCTOR001");