import jieba
from wordcloud import WordCloud
import matplotlib.pyplot as plt

# 读取文本文件
with open("中国制造2025.txt", "r", encoding="utf-8") as f:
    text = f.read()

# 使用jieba进行中文分词
word_list = jieba.cut(text)

# 将分词结果拼接成字符串
seg_text = " ".join(word_list)

# 生成词云图
wordcloud = WordCloud(font_path="simsun.ttf", background_color="white").generate(seg_text)

# 显示词云图
plt.imshow(wordcloud, interpolation="bilinear")
plt.axis("off")
plt.show()
