def calculate_Sn(a, n):
    result = 0
    num_str = str(a)

    for i in range(1, n + 1):
        term = num_str * i
        result += int(term)

    return result


if __name__ == "__main__":
    a = 2 
    n = int(input("Enter the value of n: "))

    Sn = calculate_Sn(a, n)
    print("Sn =", Sn)
