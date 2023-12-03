package main

import (
	"fmt"
	"unicode"

	"github.com/DiogoP98/advent-of-code/2023/utils"
)

type Point struct {
	row    int
	column int
}

func (p Point) neighbors() []Point {
	return []Point{
		{row: p.row - 1, column: p.column},
		{row: p.row + 1, column: p.column},
		{row: p.row, column: p.column - 1},
		{row: p.row, column: p.column + 1},
		{row: p.row - 1, column: p.column - 1},
		{row: p.row + 1, column: p.column - 1},
		{row: p.row - 1, column: p.column + 1},
		{row: p.row + 1, column: p.column + 1},
	}
}

func part1(engineSchematic [][]rune) {
	height := len(engineSchematic)
	width := len(engineSchematic[0])

	sum := 0

	for row, line := range engineSchematic {
		currentNumber := 0
		partNumber := false

		for column, currentPosition := range line {
			if !unicode.IsDigit(currentPosition) {
				if partNumber {
					sum += currentNumber
				}

				currentNumber = 0
				partNumber = false
				continue
			}

			currentDigit := int(currentPosition - '0')
			currentNumber = currentNumber*10 + currentDigit

			if partNumber {
				continue
			}

			if isPartNumber(engineSchematic, Point{row, column}, height, width) {
				partNumber = true
			}
		}

		if partNumber {
			sum += currentNumber
		}
	}

	fmt.Printf("Part 1: %d\n", sum)
}

func part2(engineSchematic [][]rune) {
	height := len(engineSchematic)
	width := len(engineSchematic[0])

	starMap := make(map[Point][]int)

	for row, line := range engineSchematic {
		currentNumber := 0
		currentStarsAround := utils.NewSet[Point]()
		partNumber := false

		for column, currentPosition := range line {
			if !unicode.IsDigit(currentPosition) {
				if partNumber {
					starMap = addStarsToMap(starMap, currentStarsAround, currentNumber)
				}

				currentNumber = 0
				currentStarsAround.Clear()
				partNumber = false
				continue
			}

			currentDigit := int(currentPosition - '0')
			currentNumber = currentNumber*10 + currentDigit
			currentPoint := Point{row, column}

			currentStarsAround.AddAll(starsAround(engineSchematic, currentPoint, height, width)...)

			if partNumber {
				continue
			}

			if isPartNumber(engineSchematic, currentPoint, height, width) {
				partNumber = true
			}
		}

		if partNumber {
			starMap = addStarsToMap(starMap, currentStarsAround, currentNumber)
		}
	}

	gearRatio := calculateGearRatios(starMap)

	fmt.Printf("Part 2: %d\n", gearRatio)
}

func isPartNumber(engineSchematic [][]rune, point Point, height int, width int) bool {
	for _, neighbor := range point.neighbors() {
		if isIndexOutOfRange(neighbor, height, width) {
			continue
		}

		element := engineSchematic[neighbor.row][neighbor.column]

		if !unicode.IsDigit(element) && element != '.' {
			return true
		}
	}

	return false
}

func starsAround(engineSchematic [][]rune, point Point, height int, width int) []Point {
	var starsAround []Point

	for _, neighbor := range point.neighbors() {
		if isIndexOutOfRange(neighbor, height, width) {
			continue
		}

		element := engineSchematic[neighbor.row][neighbor.column]

		if element == '*' {
			starsAround = append(starsAround, neighbor)
		}
	}

	return starsAround
}

func isIndexOutOfRange(point Point, height int, width int) bool {
	return point.row < 0 || point.row >= height || point.column < 0 || point.column >= width
}

func addStarsToMap(starsMap map[Point][]int, starsToAdd utils.Set[Point], number int) map[Point][]int {
	for star := range starsToAdd {
		starsMap[star] = append(starsMap[star], number)
	}

	return starsMap
}

func calculateGearRatios(starsMap map[Point][]int) int {
	totalGearRatio := 0

	for _, numbers := range starsMap {
		if len(numbers) != 2 {
			continue
		}

		totalGearRatio += numbers[0] * numbers[1]
	}

	return totalGearRatio
}

func main() {
	input, _ := utils.ReadInput("day03", "Day03.txt")
	engineSchematic := utils.BuildMatrixFromInput(input)

	part1(engineSchematic)
	part2(engineSchematic)
}
