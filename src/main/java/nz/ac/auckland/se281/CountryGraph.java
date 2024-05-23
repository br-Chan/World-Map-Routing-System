package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CountryGraph {
  private Map<Country, List<Country>> adjacencyMap;

  public CountryGraph() {
    this.adjacencyMap = new HashMap<>();

  }

  public Country getCountryByName(String name) {
    for (Country country : adjacencyMap.keySet()) {
      if (country.getName().equals(name)) {
        return country;
      }
    }
    
    return null;
  }

  public void addNode(Country country) {
    adjacencyMap.putIfAbsent(country, new ArrayList<>());

  }

  public void removeNode(Country country) {
    adjacencyMap.remove(country);

    for (Country key : adjacencyMap.keySet()) {
      adjacencyMap.get(key).remove(country);
    }

  }

  public void addEdge(Country country1, Country country2) {
    addNode(country1);
    addNode(country2);

    adjacencyMap.get(country1).add(country2);
    adjacencyMap.get(country2).add(country1);
  }

  public void removeEdge(Country country1, Country country2) {
    if (adjacencyMap.containsKey(country1) && adjacencyMap.containsKey(country2) ){
      adjacencyMap.get(country1).remove(country2);
      adjacencyMap.get(country2).remove(country1);
    }
    
  }

  public String getFastestRoute(Country sourceCountry, Country destinationCountry) {
    if (sourceCountry.equals(destinationCountry)) {
      return MessageCli.NO_CROSSBORDER_TRAVEL.getMessage();
    }

    Set<Country> visitedSet = new HashSet<>(); // To track visited countries
    Map<Country, Country> parentMap = new HashMap<>(); // To track parents of countries
    Queue<Country> queue = new LinkedList<>(); // For the BFS traversal

    // Add the source country to the visited  set, parent map (with null parent) and queue.
    visitedSet.add(sourceCountry);
    parentMap.put(sourceCountry, null);
    queue.add(sourceCountry);

    // For every country we add to the queue...
    while (!queue.isEmpty()) {
        Country current = queue.poll();

        // Iterate through each of the current country's neighbour.
        for (Country neighbour : adjacencyMap.get(current)) {
          // Add the neighbour to the 3 data structures if it hasn't been visited yet.
          if (!visitedSet.contains(neighbour)) {
              visitedSet.add(neighbour);
              parentMap.put(neighbour, current);
              queue.add(neighbour);
          }
          else if (neighbour.equals(destinationCountry)) {
            // We have reached the destination country.

            // Create a list to construct the fastest route, and add the destination country.
            List<Country> fastestRoute = new ArrayList<>();
            fastestRoute.add(destinationCountry);

            // Starting from the neighbour country before the destiantion,
            // backtrack up through each country's parents until
            // the source country is reached at the start of the route.
            Country parentNode = neighbour;
            while (parentNode != null) {
              fastestRoute.add(parentNode);
              parentNode = parentMap.get(parentNode);
            }

            Collections.reverse(fastestRoute);
            return(
                MessageCli.ROUTE_INFO.getMessage(fastestRoute.toString()) +
                "\n" + MessageCli.CONTINENT_INFO.getMessage() +
                "\n" + MessageCli.TAX_INFO.getMessage()
            );

          }
        }
    }

    return null;
  }

 

}
