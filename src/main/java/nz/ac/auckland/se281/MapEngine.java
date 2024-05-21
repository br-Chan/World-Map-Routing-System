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
    MessageCli.INSERT_COUNTRY.printMessage();

    String input = Utils.scanner.nextLine();
    Country country = graph.getCountryByName(input);

    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(),
        country.getContinent(),
        String.valueOf(country.getTaxFee())
    );
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
