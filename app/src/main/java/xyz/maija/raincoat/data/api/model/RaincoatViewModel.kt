package xyz.maija.raincoat.data.api.model

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xyz.maija.apihomework.data.api.model.GeoLocationData
import xyz.maija.raincoat.classes.Hairstyle
import xyz.maija.raincoat.classes.Location
import xyz.maija.raincoat.data.entities.User
import xyz.maija.raincoat.classes.Weather
import xyz.maija.raincoat.data.api.APIServiceLocation
import xyz.maija.raincoat.data.api.APIServiceWeather
import xyz.maija.raincoat.data.repositories.UserRepository
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.Globals

// can only have one ViewModel per app
/*
    Keeps track of data the whole app needs, methods and properties are given out to the views on an as needed basis. Stores a local user variable as well as api data, navigation information, and database user data.
 */
class RaincoatViewModel(appObj: Application): AndroidViewModel(appObj) {

    // properties
    private var _previousScreen: Screen by mutableStateOf(Screen.HomePage)
    val previousScreen: Screen
        get() = _previousScreen

    private var _hasUserLocation: Boolean by mutableStateOf(false)
    val hasUserLocation: Boolean
        get() = _hasUserLocation

    private var _user: User by mutableStateOf(User())
    val user: User
        get() = _user

    private var _gotWeather by mutableStateOf(false)
    val gotWeather: Boolean
        get() = _gotWeather

    // Weather
    private var _weatherData: List<Weather> by mutableStateOf(listOf())
    val weatherData: Weather?
        get() = if(_weatherData.isEmpty()) null else _weatherData[0]
    var weatherErrorMessage: String by mutableStateOf("")
    var weatherLoading: Boolean by mutableStateOf(false)

    // GeoLocation
    private var _geoLocationData: List<GeoLocationData> by mutableStateOf(listOf())
    val geoLocationData: GeoLocationData?
        get() = if(_geoLocationData.isEmpty()) null else _geoLocationData[0]
    var geoLocationErrorMessage: String by mutableStateOf("")
    var geoLocationLoading: Boolean by mutableStateOf(false)

    // room - local storage
    private val userRepository: UserRepository = UserRepository(appObj)
    private var _userList = fetchAllUser()
    val userList: Flow<List<User>>
        get() = _userList




    // methods
    fun setPreviousScreen(newScreen: Screen) {
        _previousScreen = newScreen
    }

    fun setHasUserLocation(value: Boolean) {
        _hasUserLocation = value
    }

    fun setHair(hair: Hairstyle) {
        _user.hair = hair
    }
    fun setHotCold(hotcold: Double) {
        _user.hotcold = hotcold
    }
    fun setSkinColor(skinColor: Color) {
        _user.skincolor = skinColor
    }
    fun setLocation(location: Location) {
        _user.location = location
    }
    fun setUseCelsius(useCelsius: Boolean) {
        _user.useCelsius = useCelsius
    }
    // used when getting user from db so it doesn't add it to db until it is changed
    fun setUser(newUser: User) { _user = newUser }

    fun setGotWeather(newGotWeather: Boolean) { _gotWeather = newGotWeather }

    fun getWeatherData() {
        viewModelScope.launch {
            weatherLoading = true

            // get the weather info with the lat, long, and useCelsius from user
            try {
                val apiServiceWeather = APIServiceWeather.getInstance()
                val weatherDataMessy = apiServiceWeather.getWeatherData(
                    lat = "${user.location?.latitude ?: 0}",
                    lon = "${user.location?.longitude ?: 0}",
                    units = "metric",
                    cnt = "8",
                    appid = Globals.API_KEY
                )
                val weatherDataClean = Weather(user, weatherDataMessy)
                _weatherData = listOf(weatherDataClean)
                weatherLoading = false

            } catch (e: Exception) {
                weatherErrorMessage = e.message.toString()
                weatherLoading = false
            } // catch

        } // launch coroutine
    } // getWeatherData

    fun getLocationData(postalCode: String, countryCode: String) {
        viewModelScope.launch {
            geoLocationLoading = true

            // get the geolocation data so that we have the lat, lon
            try {
                val apiServiceLocation = APIServiceLocation.getInstance()
                val locationData = apiServiceLocation.getGeolocationData(
                    zip = "$postalCode,$countryCode",
                    appid = Globals.API_KEY)
                _geoLocationData = listOf(locationData)
                val location: Location = Location(
                    locationName = "${locationData.name}, ${locationData.country}",
                    shortname = "${locationData.name}, ${locationData.country}",
                    latitude = locationData.lat,
                    longitude = locationData.lon
                )
                user.location = location
                geoLocationLoading = false

            } catch (e: Exception) {
                geoLocationErrorMessage = e.message.toString()
                geoLocationLoading = false
                Log.d("MEP", "getLocationData: GeoLocationError")
            } // catch


        } // launch coroutine
    } // getLocationData


    // room - local storage
    fun fetchAllUser(): Flow<List<User>> = userRepository.readAllCustomers()

    fun updateCustomerInDB() {
        deleteCustomer()
        insertCustomer(_user)
    }
    private fun insertCustomer(user: User) {
        viewModelScope.launch {
            userRepository.insertCustomer(user)
        }
    } // insertCustomer

    private fun deleteCustomer() {
        viewModelScope.launch {
            userRepository.deleteCustomer()
        }
    } // deleteCustomer

} // RaincoatViewModel
