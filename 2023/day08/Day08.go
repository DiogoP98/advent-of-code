package main

import (
	"fmt"
	"strings"

	"github.com/DiogoP98/advent-of-code/2023/utils"
)

var instructionsMap = map[rune]int{
	'L': 0,
	'R': 1,
}

func part1(input []string) {
	instructions := input[0]
	nodesMap := buildNodesMap(input)

	endCondition := func(currentStep string) bool {
		return currentStep != "ZZZ"
	}

	fmt.Printf("Part 1: %d\n", stepCount(instructions, "AAA", endCondition, nodesMap))
}

func part2(input []string) {
	instructions := input[0]
	nodesMap := buildNodesMap(input)

	fmt.Printf("Part 2: %d\n", stepCount2(instructions, nodesMap))
}

func buildNodesMap(input []string) map[string][]string {
	nodesMap := make(map[string][]string)

	for i := 2; i < len(input); i++ {
		line := input[i]

		parts := strings.Split(line, "=")
		if len(parts) != 2 {
			fmt.Println("Invalid line:", line)
			continue
		}

		key := strings.TrimSpace(parts[0])
		value := strings.TrimSpace(parts[1])

		nodesList := extractNodes(value)
		nodesMap[key] = nodesList
	}

	return nodesMap
}

func extractNodes(s string) []string {
	s = strings.Trim(s, "()")
	nodes := strings.Split(s, ",")

	for i, node := range nodes {
		nodes[i] = strings.TrimSpace(node)
	}

	return nodes
}

func stepCount(instructions string, currentStep string, endCondition func(currentStep string) bool, nodesMap map[string][]string) int {
	stepCount := 0
	currentInstructionIndex := 0

	for endCondition(currentStep) {
		stepCount += 1

		currentInstruction := rune(instructions[currentInstructionIndex])
		instructionIndex := instructionsMap[currentInstruction]

		currentStep = nodesMap[currentStep][instructionIndex]

		currentInstructionIndex = (currentInstructionIndex + 1) % len(instructions)
	}

	return stepCount
}

func stepCount2(instructions string, nodesMap map[string][]string) int {
	startingNodes := make([]string, 0)

	for node := range nodesMap {
		if strings.HasSuffix(node, "A") {
			startingNodes = append(startingNodes, node)
		}
	}

	numberOfStepsPerNode := retrieveNumberOfStepsPerNode(instructions, nodesMap, startingNodes)

	if len(numberOfStepsPerNode) == 1 {
		return numberOfStepsPerNode[0]
	}

	lcm := utils.LeastCommonMultiple(numberOfStepsPerNode[0], numberOfStepsPerNode[1])

	for i := 2; i < len(numberOfStepsPerNode); i++ {
		lcm = utils.LeastCommonMultiple(lcm, numberOfStepsPerNode[i])
	}

	return lcm
}

func retrieveNumberOfStepsPerNode(instructions string, nodesMap map[string][]string, startingNodes []string) []int {
	numberOfStepsPerNode := make([]int, len(startingNodes))
	endCondition := func(currentStep string) bool {
		return !strings.HasSuffix(currentStep, "Z")
	}

	for index, node := range startingNodes {
		nodeStepCount := stepCount(instructions, node, endCondition, nodesMap)
		numberOfStepsPerNode[index] = nodeStepCount
	}

	return numberOfStepsPerNode
}

func main() {
	input1, _ := utils.ReadInput("day08", "Day08_part1_test.txt")
	input2, _ := utils.ReadInput("day08", "Day08.txt")

	part1(input1)
	part2(input2)
}
