package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  CountryGraph graph;

  public MapEngine() {
    // add other code here if you want
    graph = new CountryGraph();
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constructing the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures
    
    String[] countryField;
    String[] adjacencyField;
    for (int c = 0; c < countries.size(); c++) {
      countryField = countries.get(c).split(",");

      String countryName = countryField[0];
      String countryContinent = countryField[1];
      int countryFee = Integer.valueOf(countryField[2]);
      graph.addNode(new Country(countryName, countryContinent, countryFee));

      adjacencyField = adjacencies.get(c).split(",");
    }

  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // Prompt user for Country name until a valid country is inputted.
    Country country = null;
    while (country == null) {
      try {
        country = promptCountryName();
      } catch(InvalidCountryException e) {
        System.out.println(e.getMessage());
      }
    }

    // Print the Country's information.
    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(),
        country.getContinent(),
        String.valueOf(country.getTaxFee())
    );
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}

  /**
   * This method prompts the user for a country name.
   *
   * @return the Country instance in the graph whose name matches the input.
   * @throws InvalidCountryException
   */
  public Country promptCountryName() throws InvalidCountryException {
    // Get the user to input a country name, and capitalise the first letter of each word.
    MessageCli.INSERT_COUNTRY.printMessage();
    String input = Utils.capitalizeFirstLetterOfEachWord(
        Utils.scanner.nextLine()
    );

    // Find the country in the graph. If it couldn't be found, throw a new invalid country exception.
    Country country = graph.getCountryByName(input);
    if (country == null) {
      throw new InvalidCountryException(input);
    }

    return country;
  }
}
