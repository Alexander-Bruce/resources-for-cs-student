import random
RamdomNumber1 = random.randint(1, 101)
RamdomNumber2 = random.randint(1, 101)
print("The random number is: ", RamdomNumber1, " and ", RamdomNumber2)
# find the maximum common divisor of RamdomNumber1 and RamdomNumber2
if RamdomNumber1 > RamdomNumber2:
    max = RamdomNumber1
else:
    max = RamdomNumber2
for i in range(max, 1, -1):
    if RamdomNumber1 % i == 0 and RamdomNumber2 % i == 0:
        print("The maximum common divisor of ", RamdomNumber1, " and ", RamdomNumber2, " is: ", i)
        break
else:
    print("The maximum common divisor of ", RamdomNumber1, " and ", RamdomNumber2, " is: 1")
# find the minimum common multiple of RamdomNumber1 and RamdomNumber2
if RamdomNumber1 > RamdomNumber2:
    min = RamdomNumber1
else:
    min = RamdomNumber2
while True:
    if min % RamdomNumber1 == 0 and min % RamdomNumber2 == 0:
        print("The minimum common multiple of ", RamdomNumber1, " and ", RamdomNumber2, " is: ", min)
        break
    min += 1

