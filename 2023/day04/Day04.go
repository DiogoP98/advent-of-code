package main

import (
	"fmt"
	"strconv"
	"strings"

	"github.com/DiogoP98/advent-of-code/2023/utils"
)

func part1(cards []string) {
	sum := 0

	for _, card := range cards {
		winningNumbersSet, ownedNumbersList := getCardContent(card)

		sum += calculateCardScore(winningNumbersSet, ownedNumbersList)
	}

	fmt.Printf("Part 1: %d\n", sum)
}

func part2(cards []string) {
	cardsInstances := make(map[int]int)

	for cardIndex, card := range cards {
		cardsInstances[cardIndex]++

		winningNumbersSet, ownedNumbersList := getCardContent(card)

		ownedWinningNumbers := getCardOwnedWinningNumbers(winningNumbersSet, ownedNumbersList)

		for i := 1; i <= ownedWinningNumbers; i++ {
			cardsInstances[cardIndex+i] += cardsInstances[cardIndex]
		}
	}

	numberOfCards := 0

	for _, cardInstances := range cardsInstances {
		numberOfCards += cardInstances
	}

	fmt.Printf("Part 2: %d\n", numberOfCards)
}

func getCardContent(card string) (utils.Set[int], []int) {
	cardContent := strings.Split(card, ":")[1]
	lists := strings.Split(cardContent, "|")
	winningNumbersSet := getCardWinningNumbers(lists[0])
	ownedNumbersList := getCardOwnedNumbers(lists[1])

	return winningNumbersSet, ownedNumbersList
}

func getCardWinningNumbers(winningNumbersList string) utils.Set[int] {
	winningNumbersSet := utils.NewSet[int]()

	winningNumbers := strings.Fields(winningNumbersList)

	for _, number := range winningNumbers {
		winningNumber, err := strconv.Atoi(number)

		if err != nil {
			fmt.Println("Error converting number to integer:", err)
			continue
		}

		winningNumbersSet.Add(winningNumber)
	}

	return winningNumbersSet
}

func getCardOwnedNumbers(ownedNumbersList string) []int {
	var cardOwnedNumbers []int

	ownedNumbers := strings.Fields(ownedNumbersList)

	for _, number := range ownedNumbers {
		ownedNumber, err := strconv.Atoi(number)

		if err != nil {
			fmt.Println("Error converting number to integer:", err)
			continue
		}

		cardOwnedNumbers = append(cardOwnedNumbers, ownedNumber)
	}

	return cardOwnedNumbers
}

func calculateCardScore(winningNumbersSet utils.Set[int], ownedNumbersList []int) int {
	cardPoints := 0

	for _, ownedNumber := range ownedNumbersList {
		if !winningNumbersSet.Contains(ownedNumber) {
			continue
		}

		if cardPoints == 0 {
			cardPoints = 1
		} else {
			cardPoints *= 2
		}
	}

	return cardPoints
}

func getCardOwnedWinningNumbers(winningNumbersSet utils.Set[int], ownedNumbersList []int) int {
	ownedWinningNumbers := 0

	for _, ownedNumber := range ownedNumbersList {
		if !winningNumbersSet.Contains(ownedNumber) {
			continue
		}

		ownedWinningNumbers += 1
	}

	return ownedWinningNumbers
}

func main() {
	input, _ := utils.ReadInput("day04", "Day04.txt")

	part1(input)
	part2(input)
}
