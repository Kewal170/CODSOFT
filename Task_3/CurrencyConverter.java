import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    // Replace with your OpenExchangeRates API Key
    private static final String API_KEY = "ed9053aa398e43b4ab9b1d11dca7c0c2";  // Replace with your actual API key
    private static final String BASE_URL = "https://openexchangerates.org/api/latest.json?app_id=";

    // Method to fetch real-time exchange rate from OpenExchangeRates
    public static double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            // Construct the URL for API request
            String urlStr = BASE_URL + API_KEY + "&symbols=" + targetCurrency;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Print the raw JSON response for debugging
            String response = content.toString();

            // Extract target rate from the response
            double targetRate = extractRate(response, targetCurrency);
            double baseRate = 1.0;  // Base rate is always 1 for the selected base currency

            // Print the rates for debugging
            System.out.println("Base Rate (" + baseCurrency + "): " + baseRate);  // Debugging line
            System.out.println("Target Rate (" + targetCurrency + "): " + targetRate);  // Debugging line

            // Check if target rate is found
            if (targetRate == 0) {
                System.out.println("Target currency not found.");
                return 0;
            }

            // Return the conversion rate from base to target currency
            return targetRate;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Helper method to extract currency rates using simple string manipulation
    private static double extractRate(String json, String currency) {
        String key = "\"" + currency + "\":";
        int startIndex = json.indexOf(key);
        if (startIndex == -1) {
            return 0;  // Currency not found
        }
        startIndex += key.length();
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }
        String rateString = json.substring(startIndex, endIndex).trim();
        return Double.parseDouble(rateString);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Currency Converter");
        System.out.println("-------------------");

        // Step 1: Get base currency from the user
        System.out.print("Enter base currency (e.g., USD, EUR, GBP): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        // Step 2: Get target currency from the user
        System.out.print("Enter target currency (e.g., USD, EUR, GBP): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Step 3: Get amount to convert
        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        // Step 4: Fetch exchange rate and perform conversion
        double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
        if (exchangeRate != 0) {
            double convertedAmount = amount * exchangeRate;
            System.out.printf("%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
        } else {
            System.out.println("Unable to retrieve exchange rate for the selected currencies.");
        }

        scanner.close();
    }
}
