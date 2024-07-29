package jetbrains.kotlin.course.almost.done

fun trimPicture(picture: String): String = picture.trimIndent()

fun applyBordersFilter(picture: String): String {
    val result: StringBuilder = StringBuilder()
    val pictureWidth = getPictureWidth(trimPicture(picture)) + 4
    val horizontalBorder = borderSymbol.toString().repeat(pictureWidth)
    val picLines = trimPicture(picture).lines()

    result.append(horizontalBorder).append(newLineSymbol)

    for (line in picLines) {
        result.append(borderSymbol).append(separator).append(line)
        if (line.length < getPictureWidth(picture)) {
            val extras = getPictureWidth(picture) - line.length
            result.append(separator.toString().repeat(extras))
        }
        result.append(separator).append(borderSymbol).append(newLineSymbol)
    }

    result.append(horizontalBorder).append(newLineSymbol)
    return result.toString()
}

fun applySquaredFilter(picture: String): String {
    val result: StringBuilder = StringBuilder()
    val doublePicture: StringBuilder = StringBuilder()
    val totalWidth = getPictureWidth(picture) * 2 + 8
    val horizontalBorder: String = borderSymbol.toString().repeat(totalWidth)

    result.append(horizontalBorder).append(newLineSymbol)

    val framedPicture = applyBordersFilter(picture).lines()
    val withoutTopOrBottom = framedPicture.drop(1).dropLast(2)

    for (line in withoutTopOrBottom) {
        doublePicture.append(line.trim()).append(line).append(newLineSymbol)
    }

    result.append(doublePicture)
    result.append(horizontalBorder).append(newLineSymbol)
    result.append(doublePicture)
    result.append(horizontalBorder).append(newLineSymbol)

    return result.toString()
}


fun applyFilter(picture: String, filter: String): String {
    return when (filter) {
        "borders" -> applyBordersFilter(trimPicture(picture))
        "squared" -> applySquaredFilter(trimPicture(picture))
        else -> error("Wrong filter")
    }
}

fun safeReadLine(): String = readlnOrNull() ?: error("Wrong line")

fun chooseFilter(): String {
    var filter: String
    do {
        println("Please input 'borders' or 'squared'.")
        filter = safeReadLine()
    } while (filter !in listOf("borders", "squared"))
    return filter
}

fun choosePicture(): String {
    val availablePictures = allPictures()
    var picture: String
    do {
        println("Please choose a picture. The possible options are: $availablePictures")
        picture = safeReadLine()
    } while (picture !in availablePictures)
    return getPictureByName(picture) ?: error("Something went wrong")
}

fun getPicture(): String {
    var yesNo: String
    var picture = ""
    do {
        println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one")
        yesNo = safeReadLine()
        when (yesNo) {
            "yes" -> picture = choosePicture()
            "no" -> {
                println("Please input a custom picture")
                picture = safeReadLine()
            }
        }
    } while (yesNo !in listOf("yes", "no"))
    return picture
}

fun photoshop() {
    val picture: String = getPicture()
    val filter = chooseFilter()
    val result = applyFilter(picture, filter)
    println("The old image:")
    println(picture)
    println("The transformed picture:")
    println(result)
}

fun main() {
    // Uncomment this code on the last step of the game

    photoshop()
}
