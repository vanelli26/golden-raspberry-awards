package br.com.gra.movie;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gra.producer.ProducerRepository;
import br.com.gra.studio.StudioRepository;

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

    public void save(MovieModel movie) {

        movie.setProducers(
                movie.getProducers().stream()
                        .map(producer -> producerRepository.findByName(producer.getName())
                                .orElseGet(() -> producerRepository.save(producer)))
                        .toList());

        movie.setStudios(
                movie.getStudios().stream()
                        .map(studio -> studioRepository.findByName(studio.getName())
                                .orElseGet(() -> studioRepository.save(studio)))
                        .toList());

        movieRepository.save(movie);
    }

    public List<MovieModel> findAllWinners() {

        return movieRepository.findByWinnerTrue();
    }
}
