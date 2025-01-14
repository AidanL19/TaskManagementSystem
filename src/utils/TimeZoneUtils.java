package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class TimeZoneUtils {
    // Base URL for the WorldTimeAPI.
    private static final String API_URL = "http://worldtimeapi.org/api/timezone/";

    // Validates if a given timezone exists using the WorldTimeAPI.
    public static boolean isValidTimeZone(String timeZone) {
        try {
            // Build the URI and convert it to a URL.
            URI uri = new URI(API_URL + timeZone);
            URL url = uri.toURL();

            // Open a connection to the API.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Use HTTP GET method.
            connection.setRequestMethod("GET"); 

            // Get the response code from the API.
            int responseCode = connection.getResponseCode();

            // Return true if the response code is 200 (successful request).
            return responseCode == 200;
        } 
        catch (Exception e) {
            // Print an error message if the request fails.
            System.err.println("Error validating timezone: " + e.getMessage());
            return false;
        }
    }

    // Fetches the UTC offset for a given timezone using the WorldTimeAPI.
    public static String getUTCOffset(String timeZone) {
        try {
            // Build the URI and convert it to a URL.
            URI uri = new URI(API_URL + timeZone);
            URL url = uri.toURL();

            // Open a connection to the API.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Use HTTP GET method.
            connection.setRequestMethod("GET"); 

            // Read the API response into a string.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                // Append each line of the response to the StringBuilder.
                response.append(line); 
            }
            // Close the reader after reading all lines.
            reader.close(); 

            // Parse the JSON response to extract the UTC offset.
            String jsonResponse = response.toString();
            // Find the start of the utc_offset value.
            int offsetStart = jsonResponse.indexOf("\"utc_offset\":\"") + 14; 
            // Find the end of the utc_offset value.
            int offsetEnd = jsonResponse.indexOf("\"", offsetStart); 
            // Extract and return the utc_offset value.
            return jsonResponse.substring(offsetStart, offsetEnd); 
        } 
        catch (Exception e) {
            // Print an error message if the request fails.
            System.err.println("Error fetching UTC offset: " + e.getMessage());
            return null;
        }
    }
}
