package xyz.maija.raincoat.classes

data class Location(
    var locationName: String = "Sunnyvale, CA",
    var shortname: String = "Sunnyvale, CA",
    var latitude: Double = 37.371428,
    var longitude: Double = -122.038679,
) {

    companion object {
        fun fromString(str: String) : Location {
            var name = ""
            var shortname = ""
            var lat = 0.0
            var lon = 0.0

            val data = str.split("|")
            if (data.isNotEmpty()) {
                name = data[0].trim()
                shortname = data[1].trim()
                lat = data[2].toDouble()
                lon = data[3].toDouble()
            }

            return Location(name, shortname, lat, lon)

        } // fromString

    } // companion object

    override fun toString(): String {
        return "${this.locationName} | ${this.shortname} | ${this.latitude} | ${this.longitude}"
    } // toString

} // Location

// 43.1566° N, 77.6088° W - Rochester