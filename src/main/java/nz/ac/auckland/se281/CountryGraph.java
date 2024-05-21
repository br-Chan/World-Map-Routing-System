package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryGraph {
  private Map<Country, List<Country>> adjNodes;

  public CountryGraph() {
    this.adjNodes = new HashMap<>();

  }

  public Country getCountryByName(String name) {
    for (Country country : adjNodes.keySet()) {
      if (country.getName().equals(name)) {
        return country;
      }
    }
    
    return null;
  }

  public void addNode(Country country) {
    adjNodes.putIfAbsent(country, new ArrayList<>());

  }

  public void removeNode(Country country) {
    adjNodes.remove(country);

  }

}
