package main

import (
	"fmt"
	"strconv"
	"strings"

	"github.com/DiogoP98/advent-of-code/2023/utils"
)

func convertValuesToSlice(values []string) []int {
	result := make([]int, len(values))
	for index, value := range values {
		intValue, err := strconv.Atoi(strings.Fields(value)[0])
		if err != nil {
			fmt.Println("Error converting value to integer:", err)
		}
		result[index] = intValue
	}
	return result
}

func calculateLevels(currentLevel []int) [][]int {
	levels := make([][]int, 0)
	levels = append(levels, currentLevel)

	for !areAllElementsZero(currentLevel) {
		nextLevel := make([]int, len(currentLevel)-1)
		for i := 1; i < len(currentLevel); i++ {
			nextLevel[i-1] = currentLevel[i] - currentLevel[i-1]
		}
		currentLevel = nextLevel
		levels = append(levels, currentLevel)
	}

	return levels
}

func areAllElementsZero(arr []int) bool {
	for _, element := range arr {
		if element != 0 {
			return false
		}
	}
	return true
}

func calculatePart1Sum(levels [][]int) int {
	previousValue := 0

	for i := len(levels) - 2; i >= 0; i-- {
		currentLevelSize := len(levels[i])
		previousValue = levels[i][currentLevelSize-1] + previousValue
	}

	return previousValue
}

func calculatePart2Sum(levels [][]int) int {
	previousValue := 0

	for i := len(levels) - 2; i >= 0; i-- {
		previousValue = levels[i][0] - previousValue
	}

	return previousValue
}

func part1(input []string) {
	extrapolatedNodesSum := 0

	for _, line := range input {
		values := convertValuesToSlice(strings.Fields(line))
		levels := calculateLevels(values)
		extrapolatedNodesSum += calculatePart1Sum(levels)
	}

	fmt.Printf("Part 1: %d\n", extrapolatedNodesSum)
}

func part2(input []string) {
	extrapolatedNodesSum := 0

	for _, line := range input {
		values := convertValuesToSlice(strings.Fields(line))
		levels := calculateLevels(values)
		extrapolatedNodesSum += calculatePart2Sum(levels)
	}

	fmt.Printf("Part 2: %d\n", extrapolatedNodesSum)
}

func main() {
	input, _ := utils.ReadInput("day09", "Day09.txt")
	part1(input)
	part2(input)
}
