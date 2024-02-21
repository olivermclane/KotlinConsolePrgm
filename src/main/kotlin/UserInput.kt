/**
 * Represents user input for city, state code, and country code.
 *
 * @property city The name of the city.
 * @property state The state code.
 * @property country The country code.
 */
data class UserInput(
    val city: String,
    val state: String,
    val country: String
)

/**
 * Retrieves user input for city, state code, and country code, with validation.
 *
 * @return UserInput containing the validated user input.
 */
fun getUserInput(): UserInput {
    var country: String
    var state: String
    var city: String

    // Validate country input
    do {
        print("Please provide a country code (US, UK, DE, etc.): ")
        country = readln() ?: ""
        when {
            country.isBlank() -> println("Country cannot be empty.")
            country.length != 2 -> println("Valid country codes are 2 characters.")
            country.toDoubleOrNull() != null -> println("Country cannot be numeric.")
        }
    } while (country.isBlank() || country.toDoubleOrNull() != null || country.length != 2)

    // Validate state input
    do {
        print("Please provide a state code (MT, UT, WA, etc.): ")
        state = readln() ?: ""
        when {
            state.isBlank() -> println("State cannot be empty.")
            state.length != 2 -> println("Valid state codes are 2 characters.")
            state.toDoubleOrNull() != null -> println("State cannot be numeric.")
        }
    } while (state.isBlank() || state.toDoubleOrNull() != null || state.length != 2)

    // Validate city input
    do {
        print("Please provide a city: ")
        city = readln() ?: ""
        when {
            city.isBlank() -> println("City cannot be empty.")
            city.toDoubleOrNull() != null -> println("City cannot be numeric.")
        }
    } while (city.isBlank() || city.toDoubleOrNull() != null)

    println()
    // Return the UserInput data store
    return UserInput(city, state, country)
}
