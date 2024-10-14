package br.com.gra.award;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.gra.movie.MovieModel;
import br.com.gra.movie.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwardService {

    private final MovieService movieService;

    public IntervalDto findIntervalAward() {

        List<AwardDto> minAwards = new ArrayList<>();
        List<AwardDto> maxAwards = new ArrayList<>();

        createAwards(minAwards, maxAwards);

        return IntervalDto.builder()
                .min(minAwards)
                .max(maxAwards)
                .build();
    }

    private void createAwards(List<AwardDto> minAwards, List<AwardDto> maxAwards) {

        Map<String, List<Integer>> producerAwards = getProducerAwards();

        producerAwards.forEach((producer, years) -> {
            for (int i = 0; i < years.size() - 1; i++) {
                int interval = years.get(i + 1) - years.get(i);
                AwardDto award = AwardDto.builder()
                        .producer(producer)
                        .interval(interval)
                        .previousWin(years.get(i))
                        .followingWin(years.get(i + 1))
                        .build();

                if (minAwards.isEmpty() || interval < minAwards.get(0).getInterval()) {
                    minAwards.clear();
                    minAwards.add(award);
                } else if (interval == minAwards.get(0).getInterval()) {
                    minAwards.add(award);
                }

                if (maxAwards.isEmpty() || interval > maxAwards.get(0).getInterval()) {
                    maxAwards.clear();
                    maxAwards.add(award);
                } else if (interval == maxAwards.get(0).getInterval()) {
                    maxAwards.add(award);
                }
            }
        });
    }

    private Map<String, List<Integer>> getProducerAwards() {

        List<MovieModel> movieList = movieService.findAllWinners();

        Map<String, List<Integer>> producerAwards = new HashMap<>();
        movieList.forEach(movie ->
            movie.getProducers().forEach(producer ->
                    producerAwards.computeIfAbsent(producer.getName(), k -> new ArrayList<>()).add(movie.getReleaseYear())
            )
        );

        producerAwards.entrySet().removeIf(entry -> entry.getValue().size() < 2);
        producerAwards.forEach((producer, years) -> years.sort(Integer::compareTo));

        return producerAwards;
    }
}
