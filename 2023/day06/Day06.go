package main

import (
	"fmt"
	"math"
	"strconv"
	"strings"

	"github.com/DiogoP98/advent-of-code/2023/utils"
)

func part1(input []string) {
	var times []int
	var recordDistances []int

	timesLine := input[0]
	times = retrieveInputValues(timesLine, times)

	recordDistancesLine := input[1]
	recordDistances = retrieveInputValues(recordDistancesLine, recordDistances)

	numberOfWaysToWin := 1

	for index := range times {
		currentRaceTime := times[index]
		currentRaceRecord := recordDistances[index]
		currentRaceNumberOfWaysToWin := numberOfWayToWinBruteForce(currentRaceTime, currentRaceRecord)

		numberOfWaysToWin *= currentRaceNumberOfWaysToWin
	}

	fmt.Printf("Part 1: %d\n", numberOfWaysToWin)
}

func part2(input []string) {
	timesLine := input[0]
	time := retrieveInputValue(timesLine)

	recordDistancesLine := input[1]
	recordDistance := retrieveInputValue(recordDistancesLine)

	numberOfWaysToWin := numberOfWayToWinMath(time, recordDistance)

	fmt.Printf("Part 2: %d\n", numberOfWaysToWin)
}

func numberOfWayToWinBruteForce(time int, recordDistance int) int {
	numberOfWaysToWin := 0

	for chargeTime := 0; chargeTime <= time; chargeTime++ {
		timeRemaining := time - chargeTime
		travelDistance := chargeTime * timeRemaining

		if travelDistance > recordDistance {
			numberOfWaysToWin += 1
			continue
		}

		if numberOfWaysToWin != 0 {
			break
		}
	}

	return numberOfWaysToWin
}

/*
hold * (time - hold) > distance
-hold^2 + time * hold > distance
-hold^2 + time * hold - distance > 0
-hold^2 + time * hold - distance > 0

(-time ±sqrt(time^2 - 4 * -1 * -distance)) / 2 * -1
*/
func numberOfWayToWinMath(time int, recordDistance int) int {
	discriminant := math.Pow(float64(time), 2) - float64(4*-1*-recordDistance)

	lowerBound := (float64(-time)+math.Sqrt(discriminant))/-2 + 1
	upperBound := (float64(-time) - math.Sqrt(discriminant)) / -2

	if upperBound == math.Trunc(upperBound) {
		upperBound -= 1
	}

	return int(upperBound) - int(lowerBound) + 1
}

func retrieveInputValues(line string, values []int) []int {
	for _, stringValue := range strings.Fields(strings.Split(line, ":")[1]) {
		value, err := strconv.Atoi(stringValue)

		if err != nil {
			fmt.Println("Error converting number to integer:", err)
			continue
		}

		values = append(values, value)
	}

	return values
}

func retrieveInputValue(line string) int {
	values := strings.Fields(strings.Split(line, ":")[1])
	value, err := strconv.Atoi(strings.Join(values[:], ""))

	if err != nil {
		fmt.Println("Error converting number to integer:", err)
	}

	return value
}

func main() {
	input, _ := utils.ReadInput("day06", "Day06_test.txt")

	part1(input)
	part2(input)
}
