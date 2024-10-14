package br.com.gra.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private static final int YEAR = 0;
    private static final int TITLE = 1;
    private static final int STUDIOS = 2;
    private static final int PRODUCERS = 3;
    private static final int WINNER = 4;

    private final MovieService movieService;

    @PostConstruct
    public void loadCsvData() {
        log.info("Loading CSV data...");
        String csvFilePath = "src/main/resources/static/movielist.csv";

        try {
            InputStream inputStream = Files.newInputStream(Paths.get(csvFilePath));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            loadTitle(bufferedReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            log.error("File not found or error reading file: {}", e.getMessage());
        }
    }

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

    private void processLine(String line) {
        try {
            String[] fields = line.split(TITLE_SEPARATOR);
            if (fields.length < WINNER) {
                throw new IOException("Invalid line format");
            }

            MovieModel movie = MovieModel.builder()
                    .releaseYear(Integer.parseInt(fields[YEAR]))
                    .title(fields[TITLE])
                    .studios(parseStudios(fields[STUDIOS]))
                    .producers(parseProducers(fields[PRODUCERS]))
                    .winner(fields.length == 5 && !fields[WINNER].isEmpty() && fields[WINNER].equalsIgnoreCase("yes"))
                    .build();

            movieService.save(movie);
        } catch (Exception e) {
            log.error("ErroException processing line '{}': {}", line, e.getMessage());
        }
    }

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
