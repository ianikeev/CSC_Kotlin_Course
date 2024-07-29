package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int): String = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

fun isComplete(secret: String, currentGuess: String): Boolean {
    val guess = currentGuess.replace(separator, "")
    return secret == guess
}

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String): String {
    val newUserWord: StringBuilder = StringBuilder()
    for (i in secret.indices) {
        if (secret[i] == guess) {
            newUserWord.append(secret[i])
        } else {
            newUserWord.append(currentUserWord[i * 2])
        }
        newUserWord.append(separator)
    }
    return newUserWord.toString().removeSuffix(separator)
}

fun generateSecret(): String = words.random()

fun isCorrectInput(userInput: String): Boolean {
    if (userInput.length != 1) {
        println("The length of your guess should be 1! Try again!")
        return false
    }
    if ((userInput[0] in ('A'..'Z')) || (userInput[0] in ('a'..'z'))) {
        return true
    }
    println("You should input only English letters! Try again!")
    return false
}

fun safeUserInput(): Char {
    var isCorrect: Boolean
    var input: String
    do {
        println("Please input your guess.")
        input = safeReadLine()
        isCorrect = isCorrectInput(input)
    } while (!isCorrect)
    return input[0].uppercaseChar()
}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String): String {
    if (guess !in secret) {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
        return currentUserWord
    }
    val newWord = generateNewUserWord(secret, guess, currentUserWord)
    println("Great! This letter is in the word! The current word is $newWord>")
    return newWord
}

fun getHiddenSecret(wordLength: Int): String = List(wordLength) { underscore }.joinToString(separator)

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean = !complete && attempts > maxAttemptsCount

fun playGame(secret: String, maxAttemptsCount: Int): Unit {
    var attempts = 0
    var input: Char
    var currentWord: String = getHiddenSecret(secret.length)
    println("I guessed a word: $currentWord")
    do {
        input = safeUserInput()
        currentWord = getRoundResults(secret, input, currentWord)
        attempts += 1
        val complete = isComplete(secret, currentWord)
        if (isWon(complete, attempts, maxAttemptsCount)) {
            println("Congratulations! You guessed it!")
            break
        }
        if (isLost(complete, attempts, maxAttemptsCount)) {
            println("Sorry, you lost! My word is $secret")
            break
        }
    } while (attempts <= maxAttemptsCount)
}

fun main() {
    // Uncomment this code on the last step of the game

    println(getGameRules(wordLength, maxAttemptsCount))
    playGame(generateSecret(), maxAttemptsCount)
}
