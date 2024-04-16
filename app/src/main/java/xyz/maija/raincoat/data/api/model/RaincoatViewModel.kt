package xyz.maija.raincoat.data.api.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import xyz.maija.raincoat.classes.Hairstyle
import xyz.maija.raincoat.classes.Location
import xyz.maija.raincoat.classes.User
import xyz.maija.raincoat.navigation.Screen

// can only have one ViewModel per app
/*
    Best Practices
    - private variables with _
    - public computed that gets the private
    - methods that update private one
    - use as high up as possible
    - pass on data that they need + func, don't pass the view model

 */
class RaincoatViewModel: ViewModel() {

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


    // changer methods
    fun setPreviousScreen(newScreen: Screen) {
        _previousScreen = newScreen
    }

    fun setHasUserLocation(value: Boolean) {
        _hasUserLocation = value
    }

    fun setHair(hair: Hairstyle) { user.hair = hair }
    fun setHotCold(hotcold: Double) { _user.hotcold = hotcold }
    fun setSkinColor(skinColor: Color) { _user.skincolor = skinColor }
    fun setLocation(location: Location) { _user.location = location }
    fun setUseCelsius(useCelsius: Boolean) { _user.useCelsius = useCelsius }

} // RaincoatViewModel
