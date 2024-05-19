package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryGraph {
  private Map<Country, List<Country>> adjNodes;

  public CountryGraph() {
    this.adjNodes = new HashMap<>();
  }

}
