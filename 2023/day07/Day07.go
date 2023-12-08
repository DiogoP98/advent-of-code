package main

import (
	"container/heap"
	"fmt"
	"strconv"
	"strings"

	"github.com/DiogoP98/advent-of-code/2023/utils"
)

const HAND_CARDS = 5

const (
	FIVE_OF_A_KIND  = 7
	FOUR_OF_A_KIND  = 6
	FULL_HOUSE      = 5
	THREE_OF_A_KIND = 4
	TWO_PAIR        = 3
	ONE_PAIR        = 2
	HIGH_CARD       = 1
)

type HandWithPower struct {
	Hand            string
	Bid             int
	Power           int
	CardStrengthMap map[rune]int
}

func getHandPower(hand string, includeJokers bool) int {
	cardCount := make(map[rune]int)
	maxCardCount := 0
	jokerCount := 0

	for _, card := range hand {
		if includeJokers && card == 'J' {
			jokerCount++
			continue
		}

		cardCount[card]++

		if cardCount[card] > maxCardCount {
			maxCardCount = cardCount[card]
		}
	}

	if includeJokers {
		maxCardCount += jokerCount
	}

	switch len(cardCount) {
	case 1:
		return FIVE_OF_A_KIND
	case 2:
		if maxCardCount == 4 {
			return FOUR_OF_A_KIND
		}
		return FULL_HOUSE
	case 3:
		if maxCardCount == 3 {
			return THREE_OF_A_KIND
		}
		return TWO_PAIR
	case 4:
		return ONE_PAIR
	default:
		if includeJokers && jokerCount == 5 {
			return FIVE_OF_A_KIND
		}
		return HIGH_CARD
	}
}

func part1(input []string) {
	cardStrength := map[rune]int{
		'A': 13, 'K': 12, 'Q': 11, 'J': 10, 'T': 9,
		'9': 8, '8': 7, '7': 6, '6': 5, '5': 4,
		'4': 3, '3': 2, '2': 1,
	}

	fmt.Printf("Part 1: %d\n", processHands(input, getHandPower, false, cardStrength))
}

func part2(input []string) {
	cardStrength := map[rune]int{
		'A': 13, 'K': 12, 'Q': 11, 'T': 10, '9': 9,
		'8': 8, '7': 7, '6': 6, '5': 5, '4': 4,
		'3': 3, '2': 2, 'J': 1,
	}

	fmt.Printf("Part 2: %d\n", processHands(input, getHandPower, true, cardStrength))
}

func processHands(input []string, getHandPowerFunc func(string, bool) int, withJokers bool, cardStrengthMap map[rune]int) int {
	h := &HandsHeap{}
	heap.Init(h)

	for _, handBid := range input {
		split := strings.Split(handBid, " ")

		hand, bid := split[0], split[1]
		bidValue, err := strconv.Atoi(strings.Fields(bid)[0])

		if err != nil {
			fmt.Println("Error converting bid to integer:", err)
		}

		heap.Push(
			h,
			HandWithPower{Hand: hand, Bid: bidValue, Power: getHandPowerFunc(hand, withJokers), CardStrengthMap: cardStrengthMap},
		)
	}

	inputSize := len(input)
	total := 0

	for i := 0; i < inputSize; i++ {
		currentHand := heap.Pop(h).(HandWithPower)

		total += (inputSize - i) * currentHand.Bid
	}

	return total
}

func main() {
	input, _ := utils.ReadInput("day07", "Day07.txt")

	part1(input)
	part2(input)
}
