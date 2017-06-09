package upm.daw.easyfilm.rest;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "movie_results"
})
public class InfoResult {

    @JsonProperty("movie_results")
    private List<MovieResult> movieResults = null;

    public InfoResult() {
    }

    public InfoResult(List<MovieResult> movieResults) {
        super();
        this.movieResults = movieResults;
    }

    @JsonProperty("movie_results")
    public List<MovieResult> getMovieResults() {
        return movieResults;
    }

    @JsonProperty("movie_results")
    public void setMovieResults(List<MovieResult> movieResults) {
        this.movieResults = movieResults;
    }

    public InfoResult withMovieResults(List<MovieResult> movieResults) {
        this.movieResults = movieResults;
        return this;
    }

}