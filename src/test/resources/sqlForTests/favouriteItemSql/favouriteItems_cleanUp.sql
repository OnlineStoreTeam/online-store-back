delete from favourite_item where id > 0;

alter sequence favourite_item_id_seq restart with 1;