package com.adi.myapplication

object MonthsOfYear {
    fun listOfMonths() = listOf<String>(
        "Ianuarie",
        "Februarie",
        "Martie",
        "Aprilie",
        "Mai",
        "Iunie",
        "Iulie",
        "August",
        "Septembrie",
        "Octombrie",
        "Noiembrie",
        "Decembrie"
    )
}

fun List<String>.nameOfMonthsToIndex(month: String): Int {
    return this.indexOf(month) + 1
}