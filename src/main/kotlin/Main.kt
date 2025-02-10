import java.io.File
import java.util.*

fun main() {
    println("Введите метод")
    println("1.По известному открытому тексту")
    println("2.По шифрованному тексту")
    println("3.По шифрованному тексту с словарем")
    val x = readln()
    when (x.toInt()) {
        1 -> {
            println("Введите незашифрованный текст")
            val openText = readln()

            println("Введите зашифрованный текст")
            val encryptedText = readln()

            val shift = open(openText, encryptedText)
            println("Ключ : $shift")
        }
        2 -> {
            println("Введите зашифрованный текст")
            val encryptedText = readln()
            close(encryptedText)
        }
        3 -> {
            println("Введите зашифрованный текст")
            val encryptedText = readln()
            book(encryptedText)
        }
    }
}

fun open(otext: String?, etext: String?): Int {
    var i = 0
    val alphabetLength = 26
    if (otext != null && etext != null) {
        for (char in otext ) {
            when {
                char.isLetter() -> {
                    val shift = if(etext[i] - char >= 0) etext[i] - char else etext[i] - char + alphabetLength
                    return shift
                }
            }
            i++
        }
        }
    return 0
}

fun close(enctext: String?) {
    for (key in 0..25) {
        val result = StringBuilder()
        val alphabetLength = 26
        if (enctext != null) {
            for (char in enctext) {
                when {
                    char.isLetter() -> {
                        val base = if (char.isUpperCase()) 'A' else 'a'
                        val shiftedChar = if (char.code - key < base.code) char.code - key + alphabetLength else char.code - key
                        result.append(shiftedChar.toChar())
                    }
                    else -> result.append(char)
                }
            }
        }
        println(result.toString())
    }
}

fun book(enctext: String?) {
    val words = loadDictionary("words.txt")
    for (key in 0..25) {
        val result = StringBuilder()
        val alphabetLength = 26
        if (enctext != null) {
            for (char in enctext) {
                when {
                    char.isLetter() -> {
                        val base = if (char.isUpperCase()) 'A' else 'a'
                        val shiftedChar = if (char.code - key < base.code) char.code - key + alphabetLength else char.code - key
                        result.append(shiftedChar.toChar())
                    }
                    else -> result.append(char)
                }
            }
        }
        if (isValidDecryption(result.toString(), words)) println("$result сдвиг $key")
    }
}

fun loadDictionary(filePath: String): Set<String> {
    return File(filePath).readLines().map { it.trim().lowercase(Locale.getDefault()) }.toSet()
}

fun isValidDecryption(decryptedText: String, dictionary: Set<String>): Boolean {
    return decryptedText.split(" ").filter{it.length >= 3}.any { it.lowercase(Locale.getDefault()) in dictionary }
}