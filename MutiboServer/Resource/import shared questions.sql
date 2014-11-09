load data local infile 'E:/Repos/Mutibo/MutiboServer/Resource/Mutibo.csv'
into table import character set utf8 columns terminated by ',' lines terminated by '\n'
ignore 1 lines (movie1, movie2, movie3, movie4, odd_movie, explanation)