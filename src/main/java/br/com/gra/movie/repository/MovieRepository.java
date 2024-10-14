package br.com.gra.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gra.movie.model.MovieModel;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, Long> {

    List<MovieModel> findByWinnerTrue();
}
