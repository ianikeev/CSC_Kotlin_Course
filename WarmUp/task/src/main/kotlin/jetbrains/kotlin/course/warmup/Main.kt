package jetbrains.kotlin.course.warmup

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String) =
    "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), " +
            "the other guesses it. In this version, the computer chooses the word: " +
            "a sequence of $wordLength letters (for example, $secretExample). " +
            "The user has several attempts to guess it (the max number is $maxAttemptsCount). " +
            "For each attempt, the number of complete matches (letter and position) " +
            "and partial matches (letter only) is reported. $newLineSymbol" +
            newLineSymbol +
            "For example, with $secretExample as the hidden word, the BCDF guess will " +
            "give 1 full match (C) and 1 partial match (B)."

fun generateSecret(): String = "ABCD"

fun countAllMatches(secret: String, guess: String): Int {
    val guessInSecret = secret.filter { it in guess }.length
    val secretInGuess = guess.filter { it in secret }.length
    return minOf(guessInSecret, secretInGuess)
}

fun countPartialMatches(secret: String, guess: String): Int =
    countAllMatches(secret, guess) - countExactMatches(secret, guess)

fun countExactMatches(secret: String, guess: String): Int =
    secret.filterIndexed { index, symbol -> guess[index] == symbol }.length

fun isComplete(secret: String, guess: String): Boolean = secret == guess

fun printRoundResults(secret: String, guess: String): Unit {
    println(
        "Your guess has ${countExactMatches(secret, guess)} full matches and ${
            countPartialMatches(
                secret,
                guess
            )
        } partial matches."
    )
}

fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean =
    complete && attempts <= maxAttemptsCount

fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean =
    !complete && attempts > maxAttemptsCount

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int) {
    var complete: Boolean
    var attempts: Int = 0
    do {
        println("Please input your guess. It should be of length $wordLength.")
        val guess = safeReadLine()
        printRoundResults(secret, guess)
        complete = isComplete(secret, guess)
        attempts += 1
        if (isLost(complete, attempts, maxAttemptsCount)) {
            println("Sorry, you lost! :( My word is $secret")
            break
        } else if (isWon(complete, attempts, maxAttemptsCount)) {
            println("Congratulations! You guessed it!")
            break
        }
    } while (!complete)
}

fun main() {
    val wordLength = 4
    val maxAttemptsCount = 3
    val secretExample = "ACEB"
    println(getGameRules(wordLength, maxAttemptsCount, secretExample))

    playGame(generateSecret(), wordLength, maxAttemptsCount)
}
