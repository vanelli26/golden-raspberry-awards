package br.com.gra.award.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gra.award.dto.AwardDto;
import br.com.gra.award.dto.IntervalDto;
import br.com.gra.movie.model.MovieModel;
import br.com.gra.movie.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwardService {

    private final MovieService movieService;

    /**
     * Find the interval between two awards
     *
     * @return interval between two awards
     */
    public IntervalDto findIntervalAward() {

        List<AwardDto> minAwards = new ArrayList<>();
        List<AwardDto> maxAwards = new ArrayList<>();

        createAwards(minAwards, maxAwards);

        return IntervalDto.builder()
                .min(minAwards)
                .max(maxAwards)
                .build();
    }

    /**
     * Create awards
     *
     * @param minAwards list of minimum awards
     * @param maxAwards list of maximum awards
     */
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

                /*
                    * If is first award or interval is smaller than the smallest, clear the list and add the award
                    * If interval is equal to the smallest, add the award
                 */
                if (minAwards.isEmpty() || interval < minAwards.get(0).getInterval()) {
                    minAwards.clear();
                    minAwards.add(award);
                } else if (interval == minAwards.get(0).getInterval()) {
                    minAwards.add(award);
                }

                /*
                    * If is first award or interval is greater than the largest, clear the list and add the award
                    * If interval is equal to the largest, add the award
                 */
                if (maxAwards.isEmpty() || interval > maxAwards.get(0).getInterval()) {
                    maxAwards.clear();
                    maxAwards.add(award);
                } else if (interval == maxAwards.get(0).getInterval()) {
                    maxAwards.add(award);
                }
            }
        });
    }

    /**
     * Get producer awards
     *
     * @return producer awards
     */
    private Map<String, List<Integer>> getProducerAwards() {

        Map<String, List<Integer>> producerAwards = new HashMap<>();
        int page = 0;

        while (true) {
            Pageable pageable = PageRequest.of(page, 10);
            Page<MovieModel> moviePage = movieService.findAllWinners(pageable);

            List<MovieModel> movieList = moviePage.getContent();
            if (movieList.isEmpty()) {
                break;
            }

            // Create a map with producer and years
            movieList.forEach(movie ->
                    movie.getProducers().forEach(producer ->
                            producerAwards.computeIfAbsent(producer.getName(), k -> new ArrayList<>()).add(movie.getReleaseYear())
                    )
            );
            page++;
        }

        // Remove producers with only one award and sort years
        producerAwards.entrySet().removeIf(entry -> entry.getValue().size() < 2);
        producerAwards.forEach((producer, years) -> years.sort(Integer::compareTo));

        return producerAwards;
    }
}
