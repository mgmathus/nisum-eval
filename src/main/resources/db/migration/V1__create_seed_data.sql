CREATE TABLE public.users (
	id bigserial NOT NULL,
	name varchar(255) NOT NULL,
	"PASSWORD" varchar NOT NULL,
	email varchar NOT NULL,
	created timestamp,
	modified timestamp,
	last_login timestamp,
	is_active boolean default false,

	CONSTRAINT uq_email UNIQUE (email),
	CONSTRAINT users_pk PRIMARY KEY (id)
);

--create default user to allow login

insert into public.users(name, "PASSWORD", email, created, modified, is_active)
values ('admin', '$2a$12$E9P5qZG9TgrEFVNe.kjLLubbcLFWEnFfnuhSg2OymnmF63ZYiiYzS', 'admin@dominio.cl', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

CREATE TABLE public.phones (
	id bigserial NOT NULL,
	number varchar(255) NOT NULL,
	citycode varchar NOT NULL,
	countrycode varchar NOT NULL,
	user_id varchar NOT NULL,
	CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);