capital = float(input("Enter the capital: "))
interest = float(input("Enter the interest rate(percent): "))
years = int(input("Enter the number of years: "))
def getValue(b, r, n):
    for i in range(1, n + 1):
        b = b * (1 + r / 100)
    return b
print("After year", years, "the capital is", '%.2f'%getValue(capital, interest, years))