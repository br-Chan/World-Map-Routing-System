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

/**
 * The graph class representing the map, in which countries make up the graph's
 * nodes and there are routes between countries that make up the edges of the graph.
 */
public class Graph {
  private Map<Country, List<Country>> adjacencyMap;

  public Graph() {
    this.adjacencyMap = new HashMap<>();

  }

  /**
   * Returns the country in the graph with the name input.
   *
   * @param name name of the country to search for.
   * @return the Country with the name input or null if not found.
   */
  public Country getCountryByName(String name) {
    // Iterate through all the country keys in the adjacency map.
    for (Country country : adjacencyMap.keySet()) {
      if (country.getName().equals(name)) {
        return country;
      }
    }
    
    return null;
  }

  /**
   * Adds the country to the adjacency map of the graph.
   *
   * @param country the Country to add to the graph.
   */
  public void addNode(Country country) {
    // Add the country to adjacency map keys with a new arraylist as the value.
    adjacencyMap.putIfAbsent(country, new ArrayList<>());

  }

  /**
   * Removes the country from the adjacency map of the graph.
   * Also removes the country from all of the arraylists in the adjacency map.
   *
   * @param country the Country to remove from the graph.
   */
  public void removeNode(Country country) {
    adjacencyMap.remove(country);

    // Iterate through all keys and remove the country whenever it appears.
    for (Country key : adjacencyMap.keySet()) {
      adjacencyMap.get(key).remove(country);
    }

  }

  /**
   * Adds an edge between two countries.
   *
   * @param country1 the country that will contain the adjacency.
   * @param country2 the country that will be listed in the adjacency.
   */
  public void addEdge(Country country1, Country country2) {
    // Add the countries if they haven't already been added.
    addNode(country1);
    addNode(country2);

    // In country1's adjacency arraylist, add country2.
    adjacencyMap.get(country1).add(country2);
  }

  /**
   * Removes an edge between two countries.
   *
   * @param country1 the country that contains the adjacency.
   * @param country2 the country that is listed in the adjacency.
   */
  public void removeEdge(Country country1, Country country2) {
    // If country1 is a key in the adjacency map,
    // remove country2 from its adjacency arraylist.
    if (adjacencyMap.containsKey(country1)){
      adjacencyMap.get(country1).remove(country2);
    }
    
  }

  /**
   * Determines the fastest route from the input source country to the input destination country.
   *
   * @param sourceCountry country to start from.
   * @param destinationCountry country to end at.
   * @return String with relevant information about the fastest route.
   */
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

      // Iterate through each of the current country's neighbours.
      for (Country neighbour : adjacencyMap.get(current)) {

        // Add the neighbour to the 3 data structures if it hasn't been visited yet.
        if (!visitedSet.contains(neighbour)) {
            visitedSet.add(neighbour);
            parentMap.put(neighbour, current);
            queue.add(neighbour);
        }
        else if (neighbour.equals(destinationCountry)) {
          // We have reached the destination country.

          List<Country> fastestRoute = new ArrayList<>();
          List<String> continentsVisited = new ArrayList<>();
          int tax = 0;

          // Starting from the neighbour country before the destiantion,
          // backtrack up through each country's parents until
          // the source country is reached at the start of the route.
          Country parentNode = neighbour;
          while (parentNode != null) {
            fastestRoute.add(parentNode);

            String parentNodeContinent = parentNode.getContinent();
            if (!continentsVisited.contains(parentNodeContinent)) {
              continentsVisited.add(parentNode.getContinent());
            }

            if (!parentNode.equals(sourceCountry)) {
              tax += parentNode.getTaxFee();
            }
            
            parentNode = parentMap.get(parentNode);
          }

          // Return the required text to print to the user.
          Collections.reverse(fastestRoute);
          Collections.reverse(continentsVisited);
          return(
              MessageCli.ROUTE_INFO.getMessage(fastestRoute.toString()) +
              "\n" + MessageCli.CONTINENT_INFO.getMessage(continentsVisited.toString()) +
              "\n" + MessageCli.TAX_INFO.getMessage(Integer.toString(tax))
          );

        }
      }
    }

    return null;
  }

 

}
