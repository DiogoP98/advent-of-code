package main

import (
	"fmt"

	"github.com/DiogoP98/advent-of-code/2023/utils"
)

type Point struct {
	x int
	y int
}

func (p Point) move(move Move) Point {
	return Point{x: p.x + move.x, y: p.y + move.y}
}

const (
	EAST  = "EAST"
	WEST  = "WEST"
	NORTH = "NORTH"
	SOUTH = "SOUTH"
)

type Move struct {
	x int
	y int
}

var movePerDirection = map[string]Move{
	EAST:  {x: 0, y: 1},
	WEST:  {x: 0, y: -1},
	NORTH: {x: -1, y: 0},
	SOUTH: {x: 1, y: 0},
}

var oppositeDirection = map[string]string{
	EAST:  WEST,
	WEST:  EAST,
	NORTH: SOUTH,
	SOUTH: NORTH,
}

const (
	VERTICAL_PIPE   = '|'
	HORIZONTAL_PIPE = '-'
	NORTH_EAST_90   = 'L'
	NORTH_WEST_90   = 'J'
	SOUTH_EAST_90   = 'F'
	SOUTH_WEST_90   = '7'
	GROUND          = '.'
	START           = 'S'
)

var possibleDirectionsPerPipe = map[rune][]string{
	VERTICAL_PIPE:   {NORTH, SOUTH},
	HORIZONTAL_PIPE: {EAST, WEST},
	NORTH_EAST_90:   {NORTH, EAST},
	NORTH_WEST_90:   {NORTH, WEST},
	SOUTH_EAST_90:   {SOUTH, EAST},
	SOUTH_WEST_90:   {SOUTH, WEST},
	GROUND:          {},
	START:           {},
}

type Tile struct {
	point         Point
	lastDirection string
}

func findStartingPoint(input []string) Point {
	for x, line := range input {
		for y, elemement := range line {
			if elemement == 'S' {
				return Point{x: x, y: y}
			}
		}
	}

	panic("No starting point")
}

func part1(input []string) {
	startPoint := findStartingPoint(input)
	var currentLevel []Tile

	tryMoveFromStartingPoint(input, startPoint, NORTH, &currentLevel)
	tryMoveFromStartingPoint(input, startPoint, SOUTH, &currentLevel)
	tryMoveFromStartingPoint(input, startPoint, WEST, &currentLevel)
	tryMoveFromStartingPoint(input, startPoint, EAST, &currentLevel)

	currentDistanceToStartingPoint := 0
	visitedPoints := utils.NewSet[Point]()

	for len(currentLevel) > 0 {
		currentDistanceToStartingPoint += 1
		var nextLevel []Tile

		for _, tile := range currentLevel {
			visitedPoints.Add(tile.point)
			move(input, tile, visitedPoints, &nextLevel)
		}
		currentLevel = nextLevel
	}

	fmt.Printf("Part 1: %d\n", currentDistanceToStartingPoint)
}

func tryMoveFromStartingPoint(input []string, currentPoint Point, direction string, currentLevel *[]Tile) {
	move := movePerDirection[direction]
	nextPoint := currentPoint.move(move)

	if nextPoint.x < 0 || nextPoint.x >= len(input) || nextPoint.y < 0 || nextPoint.y >= len(input[0]) {
		return
	}

	inputSymbol := rune(input[nextPoint.x][nextPoint.y])

	for _, possibleDirection := range possibleDirectionsPerPipe[inputSymbol] {
		if oppositeDirection[direction] == possibleDirection {
			*currentLevel = append(*currentLevel, Tile{point: nextPoint, lastDirection: oppositeDirection[direction]})
		}
	}
}

func move(input []string, tile Tile, visitedPoints utils.Set[Point], nextLevel *[]Tile) {
	currentPoint := tile.point
	possibleTileDirections := possibleDirectionsPerPipe[rune(input[currentPoint.x][currentPoint.y])]

	var directionToMove string

	for _, possibleDirection := range possibleTileDirections {
		if possibleDirection != tile.lastDirection {
			directionToMove = possibleDirection
		}
	}

	if len(directionToMove) == 0 {
		return
	}

	nextPoint := currentPoint.move(movePerDirection[directionToMove])

	if visitedPoints.Contains(nextPoint) {
		return
	}

	if nextPoint.x < 0 || nextPoint.x >= len(input) || nextPoint.y < 0 || nextPoint.y >= len(input[0]) {
		return
	}

	*nextLevel = append(*nextLevel, Tile{point: nextPoint, lastDirection: oppositeDirection[directionToMove]})
}

func main() {
	input, _ := utils.ReadInput("day10", "Day10.txt")

	part1(input)
}
