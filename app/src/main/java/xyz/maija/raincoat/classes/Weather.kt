package xyz.maija.raincoat.classes

import xyz.maija.raincoat.R
import xyz.maija.raincoat.utils.Temperature
import kotlin.math.round

class Weather {
    var current: Int = 0
    var feelsLike: Int = 0
    var min: Int = 0
    var max: Int = 0
    var humidity: Int = 0
    var rainChance: Int = 0
    var message: Message = Message()

    var errorMessage: String = ""

    // initializer blocks -> run code during main constructor
    init {
    }

    // TODO: takes in weather data
    fun checkIfNeedsAdjusting() {
        // processes weather data
        // swift code
//        let tempmin = Int(round(weatherData.main.temp_min))
//        let tempmax = Int(round(weatherData.main.temp_max))
//        let temphumidity = Int(weatherData.main.humidity)
//        let temprainChance = Int(round(weatherData.pop * 100))
//
//        if tempmin < min {
//            min = tempmin
//        }
//        if tempmax > max {
//            max = tempmax
//        }
//        if temphumidity > humidity {
//            humidity = temphumidity
//        }
//        if temprainChance > rainChance {
//            rainChance = temprainChance
//        }
    } // checkIfNeedsAdjusting

    fun resetTempMessage(user: User) {
        message = getTempMessage(user)
    }

    fun getTempMessage(user: User) : Message {

        // prepare for the return so we can edit it throughout the function
        var message = Message()
        var dressForTemp: TempRange = TempRange.SCORCHING

        // factor in the hot/cold
        var factored = factorTemp(user.hotcold)
        var changed = false

        // we do all calcs in celsius

        // if warm outside, dress for warm, prepare for cold
        // do this first because you can always add layers but can't take off your bases
        if (factored.max > TempRange.getMax(TempRange.COOL)) {
            changed = true;
            // loop through all the cases to find which ones match the min and max
            enumValues<TempRange>().forEach { range ->
                if (TempRange.getMax(range) > factored.max && TempRange.getMin(range) <= factored.max) {
                    // TODO: message.image = "\(range).\(user.hair)" - how to get image
                    dressForTemp = range
                } // dress for hot

                if (TempRange.getMax(range) > factored.min && TempRange.getMin(range) <= factored.min) {
                    message.middle = TempRange.getGarment(range)
                } // prepare for cold

            } // foreach temp range
        } // if warm outside

        // if it's cold outside, dress for the cold, prepare for the warm
        else if (factored.min < TempRange.getMin(TempRange.COOL)) {
            changed = true;

            message.beginning = "Wear a"
            message.end = "for the afternoon"

            // loop through all the cases to find which ones match the min and max
            enumValues<TempRange>().forEach { range ->

                if (TempRange.getMax(range) > factored.max && TempRange.getMin(range) <= factored.max) {
                    message.middle = TempRange.getGarment(range)
                } // prepare for hot

                if (TempRange.getMax(range) > factored.min && TempRange.getMin(range) <= factored.min) {
                    // TODO: message.image = "\(range).\(user.hair)" - how to get image
                    dressForTemp = range
                } // prepare for cold

            } // for each temp range
        } // if cold outside


        // weird cool section
        if (!changed) {
            // TODO: message.image = "cool.\(user.hair)" - how to get image
            if (humidity > 70) {
                message.beginning = "Wear a"
                message.middle = "t-shirt"
                message.end = "for the hummidity"
            } else {
                message.beginning = "Dress in"
                message.middle = "layers"
                message.end = "because it may be warm in the sun and cool in the shade"
            } // else humidity < 70
        } // if not changed

        // check for rain
        if (rainChance > 20) {
            message.middle += " and a " + TempRange.getRainGarment(dressForTemp)
        }

        return message
    } //getTempMessage

    private fun factorTemp(hotcold: Double) : Temperature {
        // TODO: This formula is v1 and could totally be improved
        var hotColdShift = getHotColdShift(hotcold)
        var humidityShift = feelsLike - current // get api's feels like shift

        // hotcold only effects when it's not 50
        return Temperature(min + hotColdShift + humidityShift, max + hotColdShift + humidityShift)

    } // factorTemp

    /// Calculate the effect of feeling hot or cold on the temperature
    ///  will be negaive for feeling hot, and positive for feeling cold
    ///    SHIFT IS IN CELSIUS
    private fun getHotColdShift(hotcold: Double) : Int {
        // I'm using x because this is a verrryyyy long equation
        // i'm shifting hotcold so that no change (50) is at x=0 instead of x=50
        var x = hotcold - 50

        // this formula is very long and particular to shift the max of 7 degrees C and exponentially less than that for the middle
        var part1 = -0.00002 * x * x * x
        var part2 = 0.00000000000000001 * x * x
        var part3 = 0.09 * x
        return round(part1 + part2 - part3).toInt() * (-1) // flip the sign of the number because the result ended up being backwards without it
    }

    fun willSnow(user: User) : Boolean {
        if (user.useCelsius) {
            return max < 0
        } else {
            return max < 32
        }
    } // willSnow

    companion object {
        fun fahrenheitToCelsius(temp: Int) : Int {
            return ((temp - 32) * (5/9)).toInt()
        }

        fun celsiusToFahrenheit(temp: Int) : Int {
            return ((9/5) * (temp + 32)).toInt()
        }

    } // companion object

} // Weather