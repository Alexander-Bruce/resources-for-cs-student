s =[9,7,8,3,2,1,5,6]
for idx, val in enumerate(s):
    if val % 2 == 0:
        temp = val * val
        s[idx] = temp
print(s)