package main

import (
	"fmt"
	"math"
	"regexp"
	"strconv"
	"strings"

	"github.com/DiogoP98/advent-of-code/2023/utils"
)

func part1(input []string) {
	currentLineIndex := 0
	var source []int
	var destination []int

	mappingRegex := `(\w)+-to-(\w)+ map:`

	for currentLineIndex < len(input) {
		currentLine := input[currentLineIndex]
		mappingMatched, _ := regexp.MatchString(mappingRegex, currentLine)

		switch {
		case strings.HasPrefix(currentLine, "seeds"):
			source = parseSeeds(currentLine, currentLineIndex)
			currentLineIndex++
		case mappingMatched:
			var updatedIndex int
			destination, updatedIndex = parseSourceToDestinationMapping(input, currentLineIndex+1, source)
			source = destination
			currentLineIndex = updatedIndex
		default:
			currentLineIndex++
		}
	}

	lowestLocation := math.MaxInt

	for _, location := range destination {
		lowestLocation = min(lowestLocation, location)
	}

	fmt.Printf("Part 1: %d\n", lowestLocation)
}

func parseSeeds(currentLine string, currentLineIndex int) []int {
	var seeds []int
	seedsList := strings.Split(currentLine, ":")[1]

	for _, seedString := range strings.Fields(seedsList) {
		seed, err := strconv.Atoi(seedString)

		if err != nil {
			fmt.Println("Error converting number to integer when parsing seeds:", err)
			continue
		}

		seeds = append(seeds, seed)
	}

	return seeds
}

func parseSourceToDestinationMapping(input []string, currentLineIndex int, source []int) ([]int, int) {
	destination := make([]int, len(source))

	// set default as the source value
	for index, sourceValue := range source {
		destination[index] = sourceValue
	}

	for currentLineIndex < len(input) && input[currentLineIndex] != "" {
		currentLine := input[currentLineIndex]

		mapping := strings.Fields(currentLine)
		destinationCategoryRangeStart, _ := strconv.Atoi(mapping[0])
		sourceCategoryRangeStart, _ := strconv.Atoi(mapping[1])
		rangeLength, _ := strconv.Atoi(mapping[2])

		for index, sourceValue := range source {
			if sourceValue >= sourceCategoryRangeStart && sourceValue < sourceCategoryRangeStart+rangeLength {
				destination[index] = destinationCategoryRangeStart + sourceValue - sourceCategoryRangeStart
			}
		}

		currentLineIndex++
	}

	return destination, currentLineIndex
}

func main() {
	input, _ := utils.ReadInput("day05", "Day05.txt")

	part1(input)
}
