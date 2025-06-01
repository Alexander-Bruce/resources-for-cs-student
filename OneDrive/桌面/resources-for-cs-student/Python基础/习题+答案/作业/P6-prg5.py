def ackermann(m, n):
    if m == 0:
        return n + 1
    elif n == 0:
        return ackermann(m - 1, 1)
    else:
        return ackermann(m - 1, ackermann(m, n - 1))

# 举例测试
number1 = int(input("请输入m: "))
number2 = int(input("请输入n: "))
result = ackermann(number1, number2)
print("Ack(3, 4) =", result)
