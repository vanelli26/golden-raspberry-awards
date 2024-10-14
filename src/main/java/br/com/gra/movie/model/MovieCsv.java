package br.com.gra.movie.model;

import lombok.Getter;

@Getter
public enum MovieCsv {

    YEAR(0),
    TITLE(1),
    STUDIOS(2),
    PRODUCERS(3),
    WINNER(4);

    private final int index;

    MovieCsv(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Object parse(String[] fields) {

        return 0;
    }
}
