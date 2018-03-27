drop table T_PLAYER if exists;
create table T_PLAYER (id bigint identity primary key, heart_count integer,
                        next_heart_time date,
                        start_date date,
                        time_played bigint,
                        admin_status boolean,
                        ads_viewed integer,
                        user_name varchar(64) not null);

ALTER TABLE T_PLAYER ALTER COLUMN heart_count SET DEFAULT 0;
ALTER TABLE T_PLAYER ALTER COLUMN admin_status SET DEFAULT false;
ALTER TABLE T_PLAYER ALTER COLUMN time_played SET DEFAULT 0;
ALTER TABLE T_PLAYER ALTER COLUMN ads_viewed SET DEFAULT 0;
