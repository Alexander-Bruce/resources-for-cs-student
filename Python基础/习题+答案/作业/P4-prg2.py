def generate_pascal_triangle(num_rows):
    triangle = []

    for row in range(num_rows):
        current_row = []

        for col in range(row + 1):
            if col == 0 or col == row:
                current_row.append(1)
            else:
                current_row.append(triangle[row - 1][col - 1] + triangle[row - 1][col])

        triangle.append(current_row)

    return triangle


def print_pascal_triangle(triangle):
    max_width = len(' '.join(map(str, triangle[-1])))

    for row in triangle:
        row_str = ' '.join(map(str, row)).center(max_width)
        print(row_str)


if __name__ == "__main__":
    num_rows = int(input("Enter the number of rows for Pascal's triangle: "))
    triangle = generate_pascal_triangle(num_rows)
    print("Pascal's triangle with {} rows:".format(num_rows))
    print_pascal_triangle(triangle)
