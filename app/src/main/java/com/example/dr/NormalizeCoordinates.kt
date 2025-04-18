package com.example.dr

import android.util.Log
import kotlin.math.sqrt
import com.example.dr.screen.DrawingView
import kotlin.math.round
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.math.round
import kotlin.math.roundToInt

// Function to compute Center of Gravity (CG) of points
fun computeCG(points: List<Pair<Double, Double>>): Pair<Double, Double> {
    if (points.isEmpty()) return Pair(0.0, 0.0)
    val sumX = points.sumOf { it.first }
    val sumY = points.sumOf { it.second }
    val count = points.size.toDouble()
    return Pair(round(sumX / count * 100) / 100, round(sumY / count * 100) / 100)
}

// Function to calculate the distance between two points
fun calculateDistance(p1: Pair<Double, Double>, p2: Pair<Double, Double>): Double {
    return round(sqrt((p2.first - p1.first) * (p2.first - p1.first) + (p2.second - p1.second) * (p2.second - p1.second)) * 100) / 100
}

// Function to process a single list of points and calculate features
fun processSingleList(points: List<Pair<Int, Int>>): List<Double> {
    val filteredPoints = points.map { Pair(it.first.toDouble(), it.second.toDouble()) }
    val mainCG = computeCG(filteredPoints)

    val (CGx, CGy) = mainCG
    val quadrants = mutableMapOf("Q1" to mutableListOf<Pair<Double, Double>>(),
        "Q2" to mutableListOf(),
        "Q3" to mutableListOf(),
        "Q4" to mutableListOf())

    // Categorize points into quadrants based on CG
    for ((x, y) in filteredPoints) {
        when {
            x >= CGx && y >= CGy -> quadrants["Q1"]?.add(Pair(x, y))
            x < CGx && y >= CGy -> quadrants["Q2"]?.add(Pair(x, y))
            x < CGx && y < CGy -> quadrants["Q3"]?.add(Pair(x, y))
            else -> quadrants["Q4"]?.add(Pair(x, y))
        }
    }

    // Compute Center of Gravity for each quadrant and calculate distances
    val quadrantCGs = quadrants.mapValues { if (it.value.isNotEmpty()) computeCG(it.value) else mainCG }
    val quadrantCounts = quadrants.mapValues { it.value.size }

    val distancesMainToQuadrants = listOf("Q1", "Q2", "Q3", "Q4").map { calculateDistance(mainCG, quadrantCGs[it]!!) }
    val distancesMainToPoints = filteredPoints.map { calculateDistance(mainCG, it) }
    val firstPoint = filteredPoints.firstOrNull() ?: Pair(0.0, 0.0)
    val distancesFirstToAll = filteredPoints.map { calculateDistance(firstPoint, it) }

    return listOf(
        mainCG.first, mainCG.second,
        quadrantCGs["Q1"]!!.first, quadrantCGs["Q1"]!!.second,
        quadrantCGs["Q2"]!!.first, quadrantCGs["Q2"]!!.second,
        quadrantCGs["Q3"]!!.first, quadrantCGs["Q3"]!!.second,
        quadrantCGs["Q4"]!!.first, quadrantCGs["Q4"]!!.second,
        quadrantCounts["Q1"]!!.toDouble(), quadrantCounts["Q2"]!!.toDouble(),
        quadrantCounts["Q3"]!!.toDouble(), quadrantCounts["Q4"]!!.toDouble(),
        *distancesMainToQuadrants.toTypedArray(),
        *distancesMainToPoints.toTypedArray(),
        *distancesFirstToAll.toTypedArray()
    )
}

// Function to normalize list to exactly 100 points using linear interpolation
fun normalizeTo100Points(points: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val n = points.size
    val normalizedList = mutableListOf<Pair<Int, Int>>()

    for (i in 0 until 100) {
        val index = i * (n - 1).toDouble() / 99 // Compute proportional index
        val lowerIndex = index.toInt()
        val upperIndex = (lowerIndex + 1).coerceAtMost(n - 1)
        val fraction = index - lowerIndex

        val x = ((1 - fraction) * points[lowerIndex].first + fraction * points[upperIndex].first).roundToInt()
        val y = ((1 - fraction) * points[lowerIndex].second + fraction * points[upperIndex].second).roundToInt()

        normalizedList.add(Pair(x, y))
    }

    return normalizedList
}

// Function to process list and ensure points are at most 100
fun processList(nestedList: List<List<Triple<Int, Int, Int>>>): List<Pair<Int, Int>> {
    val flatList = nestedList.flatten()
    val pairList = convertToPairList(flatList)

    return if (pairList.size > 100) {
        normalizeTo100Points(pairList) // Normalize if greater than 100
    } else {
        pairList // Keep as is if points are <= 100
    }
}

// Function to convert List<Triple<Int, Int, Int>> to List<Pair<Int, Int>>
fun convertToPairList(tripleList: List<Triple<Int, Int, Int>>): List<Pair<Int, Int>> {
    return tripleList.map { Pair(it.first, it.second) }
}

// Function to fetch data from DrawingView and process it
fun getProcessedFeatures(drawingView: DrawingView): List<Double> {
    val allCoordinates = drawingView.getAllCoordinates()


    val cleanCoordinates = processList(allCoordinates)

    System.out.println("↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓")
    System.out.println("Actual Input List : $cleanCoordinates")
    Log.d("Normalize Coordinates", "Length of the Input List : ${cleanCoordinates.size}")
    System.out.println("↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑")
    val listOutput : List<Double> = processSingleList(cleanCoordinates)
    Log.d("Normalize Coordinates","Length of the List : ${listOutput.size} = 18 + (2 * ${cleanCoordinates.size})" )
    return listOutput
}
