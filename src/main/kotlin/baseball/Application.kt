package baseball

import camp.nextstep.edu.missionutils.Randoms


fun main() {
    println("숫자 야구 게임을 시작합니다.")
    playBaseball()
}

fun createRandomNumber(): MutableList<Int> {
    val computer: MutableList<Int> = mutableListOf()
    while (computer.size < 3) {
        val randomNumber = Randoms.pickNumberInRange(1, 9)
        if (!computer.contains((randomNumber))) {
            computer.add(randomNumber)
        }
    }
    return computer
}

fun inputUserNumber(): String {
    print("숫자를 입력해주세요 : ")
    return readLine().toString()
}

fun checkNumber(user: String): List<Int> {
    val userNumber = user.map { it.code - '0'.code }

    return if (userNumber.size != 3) {
        throw IllegalArgumentException()
    } else if (userNumber.contains(0)) {
        throw IllegalArgumentException()
    } else if (userNumber.size != userNumber.distinct().count()) {
        throw IllegalArgumentException()
    } else {
        userNumber
    }
}

fun isNumber(user: String): Boolean {
    return try {
        user.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun calculationSocre(computerNumber: List<Int>, userNumber: List<Int>) {
    var resultList: MutableList<Int> = mutableListOf(0, 0)
    var countResult: MutableList<Int>
    for (number in userNumber) {
        if (computerNumber.contains(number)) {
            countResult = countBallStrike(userNumber.indexOf(number), number, computerNumber)
            resultList = resultList.zip(countResult) { a, b -> a + b } as MutableList<Int>
        }
    }
    printResult(resultList)
    if (resultList[1] == 3) {
        chooseReplay()
    } else {
        againinput(computerNumber)
    }
}

fun countBallStrike(location: Int, number: Int, computerNumber: List<Int>): MutableList<Int> {
    val countResult: MutableList<Int> = mutableListOf(0, 0)
    if (computerNumber.indexOf(number) == location) {
        countResult[1] += 1
    } else {
        countResult[0] += 1
    }
    return countResult
}

fun printResult(countResult: MutableList<Int>) {
    when {
        countResult[0] == 0 && countResult[1] == 0 -> println("낫싱")
        countResult[0] == 0 && countResult[1] != 0 -> println(countResult[1].toString() + "스트라이크")
        countResult[0] != 0 && countResult[1] == 0 -> println(countResult[0].toString() + "볼")
        else -> println(countResult[0].toString() + "볼 " + countResult[1].toString() + "스트라이크")
    }
}

fun checkUserNumber(): List<Int> {
    val user = inputUserNumber()
    return if (isNumber(user)) {
        checkNumber(user)
    } else {
        throw IllegalArgumentException()
    }
}

fun playBaseball() {
    val computerNumber = createRandomNumber()
    val userNumber = checkUserNumber()
    calculationSocre(computerNumber, userNumber)
}

fun againinput(computerNumber: List<Int>) {
    val userNumber = checkUserNumber()
    calculationSocre(computerNumber, userNumber)
}

fun chooseReplay() {
    println("3개의 숫자를 모두 맞히셨습니다! 게임 종료")
    println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.")
    when (readLine().toString()) {
        "1" -> playBaseball()
    }
}
