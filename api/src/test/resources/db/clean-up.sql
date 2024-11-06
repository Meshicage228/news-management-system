delete from comments;

delete from news;

alter sequence comments_id_seq restart with 2;

alter sequence news_id_seq restart with 2;

