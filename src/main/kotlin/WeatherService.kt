/**
 * Manages the retrieval of weather data from the OpenWeatherMap API based on user input.
 */
import okhttp3.OkHttpClient
import okhttp3.HttpUrl
import okhttp3.Request
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.JsonObject

// Initialize Gson and OkHttpClient instances
private val gson = Gson()
private val client = OkHttpClient()

// API key for accessing the OpenWeatherMap API
private const val OPENWEATHER_API_KEY = "c6d815e64eccbf4798a3becfb920dfd4"

/**
 * Retrieves weather data from the OpenWeatherMap API based on the user input.
 *
 * @param userInput The user input containing city, state, and country information.
 * @return WeatherData containing the weather information.
 */
fun getWeatherData(userInput: UserInput): WeatherData {
    // Build URL for the API request
    val url = buildUrl(userInput)

    // Build HTTP request
    val request = Request.Builder()
        .url(url)
        .build()

    try {
        // Execute the HTTP request
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        // Handle response based on HTTP status code
        return when (response.code) {
            200 -> parseWeatherData(responseBody)
            404 -> {
                println("Invalid query: City (${userInput.city}), state (${userInput.state}), or country (${userInput.country}) is incorrect. Please try again.")
                getWeatherData(getUserInput()) // Restart the process via Recursion
            }
            401 -> {
                println("Invalid API Key: Check config files. Please try again.")
                getWeatherData(getUserInput()) // Restart the process via Recursion
            }
            else -> {
                println("Query issue: Contact Provider. Please try again.")
                getWeatherData(getUserInput()) // Restart the process via Recursion
            }
        }
    } catch (e: IOException) {
        // Handle IO exception
        println("Error fetching weather data: ${e.message}. Please try again.")
        return getWeatherData(getUserInput()) // Restart the process via Recursion
    }
}

/**
 * Builds the URL for the OpenWeatherMap API request based on the user input.
 *
 * @param userInput The user input containing city, state, and country information.
 * @return The built HttpUrl object representing the API request URL.
 */
private fun buildUrl(userInput: UserInput): HttpUrl {
    return HttpUrl.Builder()
        .scheme("https")
        .host("api.openweathermap.org")
        .addPathSegments("data/2.5/weather")
        .addQueryParameter("q", "${userInput.city},${userInput.state},${userInput.country}")
        .addQueryParameter("appid", OPENWEATHER_API_KEY)
        .addQueryParameter("units", "imperial")
        .build()
}

/**
 * Parses the weather data obtained from the OpenWeatherMap API response.
 *
 * @param responseBody The JSON string containing the weather data.
 * @return WeatherData containing the parsed weather information.
 */
private fun parseWeatherData(responseBody: String?): WeatherData {
    val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)
    val main = jsonObject.getAsJsonObject("main")
    val weather = jsonObject.getAsJsonArray("weather").first().asJsonObject
    val wind = jsonObject.getAsJsonObject("wind")
    val sys = jsonObject.getAsJsonObject("sys")

    return WeatherData(
        temperature = main.getAsJsonPrimitive("temp").asDouble,
        highTemp = main.getAsJsonPrimitive("temp_max").asDouble,
        lowTemp = main.getAsJsonPrimitive("temp_min").asDouble,
        feltTemp = main.getAsJsonPrimitive("feels_like").asDouble,
        humidity = main.getAsJsonPrimitive("humidity").asInt,
        weatherDescription = weather.getAsJsonPrimitive("description").asString,
        windSpeed = wind.getAsJsonPrimitive("speed").asDouble,
        city = jsonObject.getAsJsonPrimitive("name").asString,
        countryCode = sys.getAsJsonPrimitive("country").asString
    )
}
