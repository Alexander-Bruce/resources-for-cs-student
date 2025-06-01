s =[9,7,8,3,2,1,55,6]
length = len(s)
min = min(s)
max = max(s)
print("The minimum value is", min) 
print("The maximum value is", max)
sum = 0
for i in s:
    sum += i
print("The sum of the list is", sum)
average = sum / length
print("The average of the list is", average)