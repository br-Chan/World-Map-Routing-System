package nz.ac.auckland.se281;

/**
 * An exception thrown when the user has inputted a country name that does
 * not exist in the Graph. This may be because of spelling issues, too.
 */
public class InvalidCountryException extends Exception {
  
  public InvalidCountryException(String input) {
    super(MessageCli.INVALID_COUNTRY.getMessage(input));
  }

}
