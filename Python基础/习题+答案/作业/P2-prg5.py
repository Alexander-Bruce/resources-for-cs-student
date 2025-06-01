Quadratic = 1
Linear = -10
Constant = 16
Delta = Linear * Linear - 4 * Quadratic * Constant
if Delta < 0:
    print("The equation has no real roots.")
elif Delta == 0:
    root = -Linear / (2 * Quadratic)
    print("The equation has one root: ", root)
else:
    root1 = (-Linear + Delta ** 0.5) / (2 * Quadratic)
    root2 = (-Linear - Delta ** 0.5) / (2 * Quadratic)
    print("The equation has two roots: ", root1, " and ", root2)