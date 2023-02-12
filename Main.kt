package parking

import java.util.*

data class Car(var color: String, var numberPlate: String, var spot: Int)

var size = 0
var parkingLot = Array(size) { false }
var cars = Array(size) { Car("", "", 0) }

fun String.capitalize(): String {
    return this[0].uppercaseChar() + substring(1).lowercase()
}

fun parkingSpot(input: String) {
    val spot: Int
    if (input.split(' ').first() == "leave") {
        if (parkingLot[input.split(' ')[1].toInt() - 1]) {
            println("Spot ${input.split(' ')[1]} is free.")
            parkingLot[input.split(' ')[1].toInt() - 1] = false
            cars[input.split(' ')[1].toInt() - 1] = Car("", "", 0)
        } else {
            println("There is no car in spot ${input.split(' ')[1].toInt()}.")
        }
    } else {
        for (i in parkingLot.indices) {
            if (!parkingLot[i]) {
                spot = i
                parkingLot[i] = true
                val carS = input.split(' ')
                val colour = carS[2].capitalize()
                val plate = carS[1]
                val car = Car(colour, plate, spot + 1)
                println("${car.color} car parked in spot ${spot + 1}.")
                cars[i] = car
                return
            }
        }
        println("Sorry, the parking lot is full.")
    }
}

fun Array<Boolean>.changeSize(newSize: Int): Array<Boolean> =
    Array(newSize) { false }


fun Array<Car>.changeSize(newSize: Int): Array<Car> =
    Array(newSize) { Car("", "", 0) }


fun printLot() {
    val tmpCar = cars.filter { it != Car("", "", 0) }
    if (tmpCar.isEmpty()) {
        println("Parking lot is empty.")
        return
    }
    for (i in tmpCar) {
        println("${i.spot} ${i.numberPlate} ${i.color}")
    }
}

fun regByColor(input: String) {
    val cars =
        cars.filter { it.color == input.capitalize() }
    if (cars.isNotEmpty()) {
        for (i in 0 until cars.size - 1) {
            print("${cars[i].numberPlate}, ")
        }
        print("${cars.last().numberPlate}\n")
    } else
        println("No cars with color $input were found.")
}

fun spotByColor(input: String) {
    val cars =
        cars.filter { it.color == input.capitalize() }
    if (cars.isNotEmpty()) {
        for (i in 0 until cars.size - 1) {
            print("${cars[i].spot}, ")
        }
        print("${cars.last().spot}\n")
    } else
        println("No cars with color $input were found.")
}

fun spotByReg(input: String) {
    val cars = cars.filter { it.numberPlate == input }
    if (cars.isNotEmpty())
        println(cars.first().spot)
    else
        println("No cars with registration number $input were found.")
}

fun main() {
    var input = readln()
    while (true) {
        if (input.matches(Regex("create \\d"))) {
            size = input.split(' ')[1].toInt()
            parkingLot = parkingLot.changeSize(size)
            cars = cars.changeSize(size)
            println("Created a parking lot with $size spots.")
            break
        } else if (input == "exit") {
            return
        } else {
            println("Sorry, a parking lot has not been created.")
            input = readln()
        }
    }
    while (true) {
        input = readln()
        if (input == "exit")
            break
        else if (input == "status")
            printLot()
        else if (input.matches(Regex("create \\d"))) {
            size = input.split(' ')[1].toInt()
            parkingLot = parkingLot.changeSize(size)
            cars = cars.changeSize(size)
            println("Created a parking lot with $size spots.")
        } else if (input.split(' ')[0] == "reg_by_color")
            regByColor(input.split(' ')[1])
        else if (input.split(' ')[0] == "spot_by_reg")
            spotByReg(input.split(' ')[1])
        else if (input.split(' ')[0] == "spot_by_color")
            spotByColor(input.split(' ')[1])
        else
            parkingSpot(input)
    }
}
