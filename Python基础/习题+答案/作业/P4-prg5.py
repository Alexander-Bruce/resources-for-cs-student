Array = []
for i in range(1, 1001):
    Array.append(i)
for i in range(1, 1001):
    if (i % 3 == 2 and i % 5 == 3 and i % 7 == 2):
        print(i)