class Coordinate(object):

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def getX(self):
        return self.x

    def getY(self):
        return self.y

    def __str__(self):
        return '<' + str(self.getX()) + ',' + str(self.getY()) + '>'

    def eq(self, other):
        return self.x == other.x and self.y == other.y

    def __repr__(self):
        return 'Coordinate({}, {})'.format(self.x, self.y)


# 创建一个坐标为 (1, 8) 的对象
coord1 = Coordinate(1, 8)

# 用 str() 方法显示该对象
print(str(coord1))

# 用 repr() 方法显示该对象
print(repr(coord1))

# 再创建一个坐标为 (1, 8) 的对象
coord2 = Coordinate(1, 8)

# 判断两个对象是否相等
print(coord1.eq(coord2))
