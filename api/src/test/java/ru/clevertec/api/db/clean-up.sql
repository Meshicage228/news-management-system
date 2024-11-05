delete from comments;

delete from news;

alter sequence news_id_seq restart with 1;

alter sequence comments_id_seq restart with 1;