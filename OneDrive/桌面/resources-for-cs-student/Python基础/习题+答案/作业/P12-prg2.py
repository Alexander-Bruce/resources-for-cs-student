import matplotlib.pyplot as plt

# 图书名称
books = ['Amazon', 'Dangdang', 'China Book', 'JD', 'Tmall']

# 最低价格
prices = [39.5, 39.9, 45.4, 38.9, 33.34]

# 创建水平条形图
plt.barh(books, prices, color='skyblue')

# 设置 x 轴范围
plt.xlim(32, 47)

# 添加标题和标签
plt.xlabel('Price')
plt.ylabel('Bookstore')
plt.title('Book Prices Comparison')

# 在柱状图顶端显示数值
for i in range(len(books)):
    plt.text(prices[i] + 0.5, i, str(prices[i]), va='center')

# 显示图形
plt.show()
