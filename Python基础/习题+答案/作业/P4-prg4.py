value1 = float(input("Enter the first number: "))
value2 = float(input("Enter the second number: "))
operation = input("Enter the operation (+, -, *, /, %): ")
if(operation == '+'):
    print("The result is: ", value1 + value2)
elif(operation == '-'):
    print("The result is: ", value1 - value2)
elif(operation == '*'):
    print("The result is: ", value1 * value2)
elif(operation == '/'):
    if(value2 == 0):
        print("The result is infinity")
    else:
        print("The result is: ", value1 / value2)
elif(operation == '%'):
    if(value2 == 0):
        print("The result is infinity")
    else:
        print("The result is: ", value1 % value2)