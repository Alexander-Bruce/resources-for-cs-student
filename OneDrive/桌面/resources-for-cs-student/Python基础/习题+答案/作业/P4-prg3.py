Income = int(input("Enter the income: "))
Tax = 0
if(Income > 10000):
    Tax = Income * 0.02
if(Income <= 10000 and Income > 5000):
    Tax = Income * 0.015
if(Income <= 5000 and Income > 3000):
    Tax = Income * 0.01
if(Income <= 3000):
    Tax = Income * 0.005
print("The tax amount is: ", Tax)