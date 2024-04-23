package xyz.maija.raincoat.classes

/*
    Keep track of hairstyle options
 */
enum class Hairstyle(val value: Int) {
    BALD(0),
    SHORT(1),
    LONG(2);

    companion object {
        fun fromInt(value: Int) = Hairstyle.values().first { it.value == value }
    }
}