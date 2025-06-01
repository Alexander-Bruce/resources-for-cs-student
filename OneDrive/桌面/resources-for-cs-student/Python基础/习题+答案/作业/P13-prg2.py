import math

class Circle:
    def __init__(self, x, y, radius, color):
        self.x = x
        self.y = y
        self.radius = radius
        self.color = color
    
    def circumference(self):
        return 2 * math.pi * self.radius
    
    def area(self):
        return math.pi * self.radius ** 2

# 创建一个 Circle 实例
circle1 = Circle(0, 0, 5, "red")

# 计算周长和面积
circumference = circle1.circumference()
area = circle1.area()

# 打印结果
print(f"Circle with radius {circle1.radius}, color {circle1.color}, circumference {circumference}, area {area}")
