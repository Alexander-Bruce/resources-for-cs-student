if __name__ == "__main__":
    lines = []
    num_lines = int(input("Enter the number of lines (not exceeding 100): "))

    if num_lines <= 100:
        for i in range(num_lines):
            line = input("Enter line {}: ".format(i+1))
            lines.append(len(line))
        print("Length of each line:")
        for i, length in enumerate(lines):
            print("Line {}: {}".format(i+1, length))
    else:
        print("Number of lines exceeds 100. Please enter a number less than or equal to 100.")
