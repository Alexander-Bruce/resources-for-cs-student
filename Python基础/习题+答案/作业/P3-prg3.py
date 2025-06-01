n = int(input("Enter a number: "))
sum = 0
for i in range(1, n + 1):
    sum = sum + 1/i
print("The sum of the series is: ", sum)