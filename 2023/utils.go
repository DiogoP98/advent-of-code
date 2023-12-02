package main

import (
	"bufio"
	"os"
	"path/filepath"
)

func readInput(fileName string) ([]string, error) {
	resourcesPath := filepath.Join(".", "resources")
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
