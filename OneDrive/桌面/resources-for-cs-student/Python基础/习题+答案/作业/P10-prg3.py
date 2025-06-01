# 导入所需库
import numpy as np
from sklearn.datasets import load_iris

# 加载数据集
iris = load_iris()

# 查看数据集的形状
print("数据集的形状:", iris.data.shape)

# 查看数据集的数据类型
print("数据集的数据类型:", iris.data.dtype)

# 计算每列的最大值
max_values = np.max(iris.data, axis=0)

# 计算每列的最小值
min_values = np.min(iris.data, axis=0)

# 计算每列的平均值
mean_values = np.mean(iris.data, axis=0)

# 打印结果
print("每列的最大值:", max_values)
print("每列的最小值:", min_values)
print("每列的平均值:", mean_values)
