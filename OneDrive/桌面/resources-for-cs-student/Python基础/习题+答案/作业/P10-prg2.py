import numpy as np

def generator():
    matrix = np.random.uniform(1, 9999999999, [10, 10])
    return matrix

def get_max_min(matrix):
    max_number = matrix[0][0]
    min_number = matrix[0][0]
    for row in matrix:
        for num in row:
            max_number = max(num, max_number)
            min_number = min(num, min_number)
    return 'Max number is: ' + str(max_number), 'Min number is: ' + str(min_number)

if __name__ == '__main__':
    matrix = generator()
    print(get_max_min(matrix))