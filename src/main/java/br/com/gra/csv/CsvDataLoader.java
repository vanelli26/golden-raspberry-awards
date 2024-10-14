package br.com.gra.csv;

import static br.com.gra.movie.model.MovieCsv.PRODUCERS;
import static br.com.gra.movie.model.MovieCsv.STUDIOS;
import static br.com.gra.movie.model.MovieCsv.TITLE;
import static br.com.gra.movie.model.MovieCsv.WINNER;
import static br.com.gra.movie.model.MovieCsv.YEAR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.gra.movie.model.MovieModel;
import br.com.gra.movie.service.MovieService;
import br.com.gra.producer.model.ProducerModel;
import br.com.gra.studio.model.StudioModel;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CsvDataLoader {

    private static final String TITLE_SEPARATOR = ";";
    private static final String FIELD_SEPARATOR = ",";
    private static final int CHUNK_SIZE = 50;

    private final MovieService movieService;

    /**
     * Load CSV data into database
     */
    @PostConstruct
    public void loadCsvData() {
        log.info("Loading CSV data...");
        String csvFilePath = "src/main/resources/static/movielist.csv";

        try {
            InputStream inputStream = Files.newInputStream(Paths.get(csvFilePath));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            loadTitle(bufferedReader);

            loadData(bufferedReader);
        } catch (IOException e) {
            log.error("File not found or error reading file: {}", e.getMessage());
        }
    }

    /**
     * Load title from CSV file
     *
     * @param bufferedReader bufferedReader
     * @throws IOException if title not found or invalid format
     */
    private void loadTitle(BufferedReader bufferedReader) throws IOException {
        String title = bufferedReader.readLine();
        if (title == null) {
            throw new IOException("Title not found");
        }

        String[] columns = title.split(TITLE_SEPARATOR);
        if (columns.length != 5) {
            throw new IOException("Invalid title format");
        }
    }

    /**
     * Load data from CSV file
     *
     * @param bufferedReader bufferedReader
     * @throws IOException if error reading file
     */
    private void loadData(BufferedReader bufferedReader) throws IOException {

        List<MovieModel> moviesChunk = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            moviesChunk.add(processLine(line));

            if (moviesChunk.size() == CHUNK_SIZE) {
                movieService.saveAll(moviesChunk);
                moviesChunk.clear();
            }
        }

        if (!moviesChunk.isEmpty()) {
            movieService.saveAll(moviesChunk);
        }
    }

    /**
     * Process line from CSV file
     *
     * @param line line
     * @return movie model
     */
    private MovieModel processLine(String line) {
        try {
            String[] fields = line.split(TITLE_SEPARATOR);
            if (fields.length < WINNER.getIndex()) {
                throw new IOException("Invalid line format");
            }

            return MovieModel.builder()
                    .releaseYear(Integer.parseInt(fields[YEAR.getIndex()]))
                    .title(fields[TITLE.getIndex()])
                    .studios(parseStudios(fields[STUDIOS.getIndex()]))
                    .producers(parseProducers(fields[PRODUCERS.getIndex()]))
                    .winner(fields.length == 5 &&
                            !fields[WINNER.getIndex()].isEmpty() &&
                            fields[WINNER.getIndex()].equalsIgnoreCase("yes"))
                    .build();
        } catch (Exception e) {
            log.error("ErroException processing line '{}': {}", line, e.getMessage());
            return null;
        }
    }

    /**
     * Parse producers
     *
     * @param field field
     * @return list of producers
     */
    private List<ProducerModel> parseProducers(String field) {

        if (Objects.isNull(field) || field.isEmpty()) {
            return Collections.emptyList();
        }
        field = field.replace(", and ", FIELD_SEPARATOR);
        field = field.replace(" and ", FIELD_SEPARATOR);
        return Arrays.stream(field.split(FIELD_SEPARATOR))
                .map(producer -> ProducerModel.builder().name(producer.trim()).build())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Parse studios
     *
     * @param field field
     * @return list of studios
     */
    private List<StudioModel> parseStudios(String field) {

        if (Objects.isNull(field) || field.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(field.split(FIELD_SEPARATOR))
                .map(studio -> StudioModel.builder().name(studio.trim()).build())
                .distinct()
                .collect(Collectors.toList());
    }
}
