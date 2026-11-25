package th.ac.cdti.rottenpotatoes.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import th.ac.cdti.rottenpotatoes.domain.Movie;
import th.ac.cdti.rottenpotatoes.domain.MovieCatalog;
import th.ac.cdti.rottenpotatoes.domain.Rating;

/** Step definitions for movie_filter.feature, run directly against the plain-Java domain. */
public class MovieSteps {

  private final List<Movie> movies = new ArrayList<>();
  private List<String> shown;

  /** Adds one movie to this scenario's catalog. */
  @Given("a movie {string} rated {string} released in {int} is in the catalog")
  public void addMovieToTheCatalog(String title, String label, int year) {
    movies.add(new Movie(title, Rating.fromLabel(label), "", LocalDate.of(year, 1, 1)));
  }

  /** Keeps only the titles, because the Then steps compare titles. */
  @When("I filter movies by rating {string}")
  public void filterMoviesByRating(String label) {
    shown = new MovieCatalog(movies).byRating(Rating.fromLabel(label)).stream()
        .map(Movie::title)
        .toList();
  }

  /** Checks for a movie that matches the selected rating. */
  @Then("I should see {string}")
  public void shouldSee(String title) {
    assertThat(shown).contains(title);
  }

  /** Checks that a movie with a different rating was filtered out. */
  @Then("I should not see {string}")
  public void shouldNotSee(String title) {
    assertThat(shown).doesNotContain(title);
  }

  /** Checks the result when no movie matches the rating. */
  @Then("I should see no movies")
  public void shouldSeeNoMovies() {
    assertThat(shown).isEmpty();
  }
}
