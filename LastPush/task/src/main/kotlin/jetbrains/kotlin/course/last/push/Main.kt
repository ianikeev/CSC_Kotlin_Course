package jetbrains.kotlin.course.last.push

// You will use this function later
fun getPattern(): String {
    println(
        "Do you want to use a pre-defined pattern or a custom one? " +
                "Please input 'yes' for a pre-defined pattern or 'no' for a custom one"
    )
    do {
        when (safeReadLine()) {
            "yes" -> {
                return choosePattern()
            }

            "no" -> {
                println("Please, input a custom picture")
                return safeReadLine()
            }

            else -> println("Please input 'yes' or 'no'")
        }
    } while (true)
}

// You will use this function later
fun choosePattern(): String {
    do {
        println("Please choose a pattern. The possible options: ${allPatterns().joinToString(", ")}")
        val name = safeReadLine()
        val pattern = getPatternByName(name)
        pattern?.let {
            return@choosePattern pattern
        }
    } while (true)
}

// You will use this function later
fun chooseGenerator(): String {
    var toContinue = true
    var generator = ""
    println("Please choose the generator: 'canvas' or 'canvasGaps'.")
    do {
        when (val input = safeReadLine()) {
            "canvas", "canvasGaps" -> {
                toContinue = false
                generator = input
            }

            else -> println("Please, input 'canvas' or 'canvasGaps'")
        }
    } while (toContinue)
    return generator
}

// You will use this function later
fun safeReadLine(): String = readlnOrNull() ?: error("Your input is incorrect, sorry")

fun getPatternHeight(pattern: String): Int = pattern.lines().size

fun fillPatternRow(patternRow: String, patternWidth: Int): String {
    if (patternRow.length > patternWidth) {
        throw IllegalStateException("Illegal pattern length")
    }
    val widthDifference = patternWidth - patternRow.length
    val diff = separator.toString().repeat(widthDifference)
    return patternRow + diff
}

fun repeatHorizontally(pattern: String, n: Int, patternWidth: Int): String {
    // trim() removes all whitespace, which is not what we want
    val patternRows = pattern.lines().map { it.replace(Regex("""\r?\n"""), "") }
    val repeatedPattern = StringBuilder()
    for (row in patternRows) {
        // Using \n instead of newLineSymbol, otherwise test fails on Windows
        repeatedPattern.append(fillPatternRow(row, patternWidth).repeat(n)).append("\n")
    }
    // And also doesn't like the final newline
    return repeatedPattern.toString().dropLast(1)
}

fun main() {

    println("Pattern:")
    val n = 2
    println(rhombus)
    println("n = $n")
    println("Result:")
    println(repeatHorizontally(rhombus, n, getPatternWidth(rhombus)))
    // Uncomment this code on the last step of the game

    // val pattern = getPattern()
    // val generatorName = chooseGenerator()
    // println("Please input the width of the resulting picture:")
    // val width = safeReadLine().toInt()
    // println("Please input the height of the resulting picture:")
    // val height = safeReadLine().toInt()

    // println("The pattern:$newLineSymbol${pattern.trimIndent()}")

    // println("The generated image:")
    // println(applyGenerator(pattern, generatorName, width, height))
}
