package main

import (
	"fmt"
	"regexp"
	"strconv"
	"strings"
	"github.com/DiogoP98/advent-of-code/2023/utils"
)

var cubeCount = map[string]int{
	"red":   12,
	"green": 13,
	"blue":  14,
}

type colorPair struct {
	color string
	count int
}

func part1(games []string) {
	result := 0

	for gameNumber, game := range games {
		if possibleGame(game) {
			result += gameNumber + 1
		}
	}

	fmt.Println(result)
}

func part2(games []string) {
	totalPower := 0

	for _, game := range games {
		totalPower += gamePower(game)
	}

	fmt.Println(totalPower)
}

func possibleGame(game string) bool {
	sets := retrieveSets(game)

	for _, set := range sets {
		colorPairs := retriveSetColorCounts(set)

		for _, pair := range colorPairs {
			color, count := pair.color, pair.count

			if count > cubeCount[color] {
				return false
			}
		}
	}

	return true
}

func gamePower(game string) int {
	minColorCounts := make(map[string]int)

	sets := retrieveSets(game)

	for _, set := range sets {
		colorPairs := retriveSetColorCounts(set)

		for _, pair := range colorPairs {
			color, count := pair.color, pair.count

			minColorCount, exists := minColorCounts[color]

			if !exists || count > minColorCount {
				minColorCounts[color] = count
			}
		}
	}

	power := 1
	for _, count := range minColorCounts {
		power *= count
	}
	return power
}

func retrieveSets(game string) []string {
	return strings.Split(game, ";")
}

func retriveSetColorCounts(set string) []colorPair {
	var colorPairs []colorPair

	re := regexp.MustCompile(`(\d+) (\w+)`)
	matches := re.FindAllStringSubmatch(set, -1)

	for _, match := range matches {
		count, err := strconv.Atoi(match[1])
		if err != nil {
			fmt.Println("Error converting count to integer:", err)
			continue
		}

		color := match[2]
		colorPairs = append(colorPairs, colorPair{color, count})
	}

	return colorPairs
}

func main() {
	games, _ := utils.readInput("Day02.txt")
	part1(games)
	part2(games)
}
