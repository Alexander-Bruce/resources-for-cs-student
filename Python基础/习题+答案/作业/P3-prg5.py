Quadratic = float(input("Enter the value of a: "))
Linear = float(input("Enter the value of b: "))
Constant = float(input("Enter the value of c: "))
Delta = Linear * Linear - 4 * Quadratic * Constant
if Quadratic == 0 and Linear ==0:
    print("The equation is not quadratic.")
elif Quadratic == 0 and Linear != 0:
    print("The equation is linear.")
    root = -Constant / Linear
    print("The equation has one root: ", root)
elif Delta < 0:
    real_part = -Linear / (2 * Quadratic)
    imaginary_part = (-Delta) ** 0.5 / (2 * Quadratic)
    print("The equation has two complex roots: ", real_part, "+", imaginary_part, "i", " and ", real_part, "-", imaginary_part, "i")
elif Delta == 0:
    print("The equation has two equal root.")
    root = -Linear / (2 * Quadratic)
    print("The equation has one root: ", root)
elif Delta < 0:
    print("The equation has two complex roots.")
    root1 = (-Linear + Delta ** 0.5) / (2 * Quadratic)
    root2 = (-Linear - Delta ** 0.5) / (2 * Quadratic)
    print("The equation has two roots: ", root1, " and ", root2)
else:
    print("The equation has two distinct roots.")
    root1 = (-Linear + Delta ** 0.5) / (2 * Quadratic)
    root2 = (-Linear - Delta ** 0.5) / (2 * Quadratic)
    print("The equation has two roots: ", root1, " and ", root2)