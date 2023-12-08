package utils

import (
	"bufio"
	"os"
	"path/filepath"
)

func ReadInput(folder string, fileName string) ([]string, error) {
	resourcesPath := filepath.Join(folder, "resources")
	filePath := filepath.Join(resourcesPath, fileName)

	file, err := os.Open(filePath)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	var lines []string
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return lines, nil
}

func BuildMatrixFromInput(input []string) [][]rune {
	height := len(input)

	if height == 0 {
		return make([][]rune, 0)
	}

	matrix := make([][]rune, height)

	width := len(input[0])

	for i := 0; i < height; i++ {
		matrix[i] = make([]rune, width)

		for j, char := range input[i] {
			matrix[i][j] = char
		}
	}

	return matrix
}

func LeastCommonMultiple(n1 int, n2 int) int {
	var larger int

	if n1 > n2 {
		larger = n1
	} else {
		larger = n2
	}

	maxLCM := n1 * n2
	lcm := larger

	for lcm <= maxLCM {
		if lcm%n1 == 0 && lcm%n2 == 0 {
			return lcm
		}
		lcm += larger
	}

	return maxLCM
}
