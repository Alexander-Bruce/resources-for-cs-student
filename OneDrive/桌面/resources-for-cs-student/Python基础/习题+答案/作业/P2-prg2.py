capital = float(input("Enter the capital: "))
interest = float(input("Enter the interest rate(percent): "))
years = int(input("Enter the number of years: "))
for i in range(1, years + 1):
    capital = capital * (1 + interest / 100)
print('%.2f' % capital, 'money after', years, 'years.')