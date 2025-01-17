# Java Lotto Number Combinations Filter

## Project Overview
The program processes lotto numbers to generate and filter valid combinations. Specifically, it reads integers from a file, generates all possible combinations of six numbers (as in the Greek Lotto system), filters the combinations based on specific criteria, and writes the valid combinations to an output file. The entire program is implemented in a single Java file for simplicity.

## Features
- Reads integers (1 to 49) from an input file.
- Sorts the integers using `Arrays.sort()`.
- Generates all possible combinations of six numbers.
- Applies filtering criteria to ensure combinations meet the following rules:
  1. Contains at most 4 even numbers.
  2. Contains at most 4 odd numbers.
  3. Contains at most 2 consecutive numbers.
  4. Contains at most 3 numbers with the same last digit.
  5. Contains at most 3 numbers in the same decade (e.g., 10-19).
- Writes the valid combinations to an output `.txt` file.

## How It Works
1. **Input File**: The program expects an input file containing integers (more than 6 and up to 49). The integers should be between 1 and 49.
2. **Processing**:
   - The integers are read into an array.
   - The array is sorted.
   - All possible combinations of six numbers are generated.
   - Each combination is checked against the filtering criteria using methods like `isEven`, `isOdd`, `isContiguous`, `isSameEnding`, and `isSameTen`.
3. **Output File**: Valid combinations are written to a `.txt` file.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher.
- An input file with integers (one per line).

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Kiriweb/lotto-combination-generator.git
   ```
2. Navigate to the project directory:
   ```bash
   cd lotto-combination-generator\gr\coding\lotto
   ```
3. Compile the Java program:
   ```bash
   javac LottoCombinations.java
   ```

### Running the Program
1. Create an input file (e.g., `input.txt`) with integers (one per line). Make sure that input.txt is inside root directory lotto-combination-generator.
2. 2. Navigate to the project directory:
   ```bash
   cd gr\coding\lotto
   ```
3. Run the program:
   ```bash
   java LottoCombinations
   ```
   
## Example
### Input File (`input.txt`):
```
5
10
15
20
25
30
35
40
45
49
```

### Output File (`output.txt`):
```
5, 10, 15, 20, 25, 30
10, 15, 20, 25, 30, 35
...
```
(Note: Only valid combinations that meet the criteria will appear in the output.)

## Project Structure
```
lotto-combination-generator\gr\coding\lotto
├── LottoCombinations.java      # Main program file
└── README.md                   # Project documentation
```
```
lotto-combination-generator\
├── input.txt                   # Sample input file
```

## Author
[Kyriakos Koutsourelis](https://github.com/Kiriweb)
