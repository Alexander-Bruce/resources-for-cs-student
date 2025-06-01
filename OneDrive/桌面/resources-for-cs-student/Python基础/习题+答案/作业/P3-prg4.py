edge = []
for i in range(3):
    edge.append(float(input("Enter the length of the edge: ")))
edge.sort()
if edge[0] + edge[1] > edge[2]:
    print("The triangle is valid.")
    if edge[0] == edge[1] == edge[2]:
        print("The triangle is equilateral.")
    elif (edge[0] == edge[1] or edge[1] == edge[2]) and (edge[0] * edge[0] + edge[1] * edge[1] == edge[2] * edge[2]):
        print("The triangle is isosceles and right-angled.")
    elif edge[0] == edge[1] or edge[1] == edge[2]:
        print("The triangle is isosceles.")
    elif edge[0]**2 + edge[1]**2 == edge[2]**2:
        print("The triangle is right-angled.")
    else:
        print("The triangle is scalene.")