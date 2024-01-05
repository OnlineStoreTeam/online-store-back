delete from cart where id > 0;

alter sequence cart_id_seq restart with 1;