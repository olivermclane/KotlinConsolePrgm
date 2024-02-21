/**
 * This program allows users to query current weather conditions using the OpenWeatherMap API
 * based on the state code, country code, and city.
 *
 * @author Oliver McLane
 * @date February 20, 2024
 *
 * @cite https://square.github.io/okhttp/#get-a-url Documentation on okHttp: used to make and form the requests to OpenWeatherMap API.
 */

fun main() {
    var continueloop = true
    println("Welcome to the Kotlin OpenWeatherMap API viewer that allows you to query current conditions provided the state code, country code, and city.")
    while (continueloop) {

        println("")
        println("Instructions: ")
        println("1: Query Current conditions")
        println("Q or Quit: Quits the program")
        println("")
        val isQuit = readln()
        when (isQuit.capitalize()) {
            "1" -> {
                val userInput = getUserInput()
                val weatherData = getWeatherData(userInput)
                displayWeatherData(weatherData)
            }

            "Q" -> continueloop = false
            "Quit" -> continueloop = false
            else -> {
                println("Please provide a valid option.")
                continue
            }
        }
    }
}

/**
 * Displays weather data obtained from the OpenWeatherMap API.
 *
 * @param weatherData The weather data to be displayed.
 */
fun displayWeatherData(weatherData: WeatherData) {
    print("System Querying.")
    for (i in 1..5) {
        print(".")
        Thread.sleep(1000)
    }
    println("")
    println("")
    println("The current conditions in ${weatherData.city}, ${weatherData.countryCode} is ${weatherData.weatherDescription}.")
    println("The current temp is ${weatherData.temperature} degrees fahrenheit, with a high of ${weatherData.highTemp} degrees fahrenheit and a low of ${weatherData.lowTemp} degrees fahrenheit, but it feels ${weatherData.feltTemp} degrees fahrenheit.")
    println("The humidity is ${weatherData.humidity}% with wind speeds of ${weatherData.windSpeed}/mph.")
}
