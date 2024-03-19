package xyz.maija.raincoat.classes

enum class TempRange {
    SCORCHING,
    HOT,
    WARM,
    COOL,
    COLD,
    FRIGID,
    FREEZING,
    FROZEN;

    companion object {

        // inclusive
        fun getMin(tempRange: TempRange): Int {
            return when (tempRange) {
                SCORCHING -> 32
                HOT -> 27
                WARM -> 18
                COOL -> 10
                COLD -> 4
                FRIGID -> 0
                FREEZING -> -9
                FROZEN -> -100
            } // return when
        } // getMin

        // not inclusive
        fun getMax(tempRange: TempRange): Int {
            return when (tempRange) {
                SCORCHING -> 100
                HOT -> 32
                WARM -> 27
                COOL -> 18
                COLD -> 10
                FRIGID -> 4
                FREEZING -> 0
                FROZEN -> -9
            } // return when
        } // getMax

        // the garment to prepare for this weather
        fun getGarment(tempRange: TempRange): String {
            return when (tempRange) {
                SCORCHING -> "tanktop"
                HOT -> "light t-shirt"
                WARM -> "t-shirt"
                COOL -> "hoodie"
                COLD -> "coat"
                FRIGID -> "coat and a hat"
                FREEZING -> "warm coat and a hat"
                FROZEN -> "parka"
            } // return when
        } // getGarment

        // rain garment appropriate for this weather
        fun getRainGarment(tempRange: TempRange): String {
            return when (tempRange) {
                SCORCHING, HOT, WARM, COOL, COLD, FRIGID -> "raincoat"
                FREEZING, FROZEN -> "snowcoat"
            } // return when
        } // getRainGarment
    } // companion object
} // TempRanges