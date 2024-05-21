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

    for (Country key : adjNodes.keySet()) {
      adjNodes.get(key).remove(country);
    }

  }

  public void addEdge(Country country1, Country country2) {
    addNode(country1);
    addNode(country2);

    adjNodes.get(country1).add(country2);
    adjNodes.get(country2).add(country1);
  }

  public void removeEdge(Country country1, Country country2) {
    if (adjNodes.containsKey(country1) && adjNodes.containsKey(country2) ){
      adjNodes.get(country1).remove(country2);
      adjNodes.get(country2).remove(country1);
    }
    
  }

}
