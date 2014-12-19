SELECT m1.movietitle as mt1, m2.movietitle as mt2, m3.movietitle as mt3, m4.movietitle as mt4, correct_answer, explanation, hint FROM mutibo.movieset
join mutibo.movie m1 on movieset.movie1_id = m1.id
join mutibo.movie m2 on movieset.movie2_id = m2.id
join mutibo.movie m3 on movieset.movie3_id = m3.id
join mutibo.movie m4 on movieset.movie4_id = m4.id
