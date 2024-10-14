package br.com.gra.movie.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gra.movie.model.MovieModel;
import br.com.gra.movie.repository.MovieRepository;
import br.com.gra.producer.repository.ProducerRepository;
import br.com.gra.studio.repository.StudioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;
    private final StudioRepository studioRepository;

    /**
     * Save all movies and its producers and studios
     *
     * @param movies list of movies
     */
    public void saveAll(List<MovieModel> movies) {

        movies.forEach(movie -> {
            movie.setProducers(
                    movie.getProducers().stream()
                            .filter(producer -> Objects.isNull(producer.getId()))
                            .map(producer -> producerRepository.findByName(producer.getName())
                                    .orElseGet(() -> producerRepository.save(producer)))
                            .toList());

            movie.setStudios(
                    movie.getStudios().stream()
                            .filter(studio -> Objects.isNull(studio.getId()))
                            .map(studio -> studioRepository.findByName(studio.getName())
                                    .orElseGet(() -> studioRepository.save(studio)))
                            .toList());
        });

        movieRepository.saveAll(movies);
    }

    /**
     * Find all winners
     *
     * @return list of winners
     */
    public Page<MovieModel> findAllWinners(Pageable pageable) {

        return movieRepository.findByWinnerTrue(pageable);
    }
}
