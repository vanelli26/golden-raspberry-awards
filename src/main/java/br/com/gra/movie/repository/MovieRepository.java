package br.com.gra.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gra.movie.model.MovieModel;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, Long> {

    Page<MovieModel> findByWinnerTrue(Pageable pageable);
}
