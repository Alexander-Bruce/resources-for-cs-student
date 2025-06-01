Array = []
Array.append(100)
for i in range(1, 11):
    Array.append(Array[i-1]/2)
sum = 0
for i in range(1, 11):
    sum = sum + Array[i]
print("No.10's meters are: ", Array[10])
print("The sum of the series is: ", sum)