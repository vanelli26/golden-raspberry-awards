package br.com.gra.movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, Long> {

    List<MovieModel> findByWinnerTrue();
}
