package mutibo.moviesets.controller;

import mutibo.moviesets.repository.MovieSetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MovieSetSvc {

	@Autowired
	private MovieSetRepository movieSets;
	
	public MovieSetSvc() {
		
	}

}
