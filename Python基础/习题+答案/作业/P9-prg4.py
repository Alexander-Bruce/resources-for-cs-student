import requests
from bs4 import BeautifulSoup
import re

# 发送请求
url = 'https://so.gushiwen.org/gushi/tangshi.aspx'
response = requests.get(url)

# 检查请求是否成功
if response.status_code == 200:
    # 使用 BeautifulSoup 解析 HTML
    soup = BeautifulSoup(response.text, 'html.parser')

    # 找到 class 为 "sons" 的 div 元素
    sons_div = soup.find(class_='sons')

    # 初始化一个字典来存储作者名和对应的出现次数
    author_counts = {}

    # 找到所有的 span 标签
    if sons_div:
        span_tags = sons_div.find_all('span')
        for span_tag in span_tags:
            # 获取 span 标签内的文本值
            text = span_tag.get_text(strip=True)
            
            # 使用正则表达式匹配括号内的作者名
            match = re.search(r'\((.*?)\)', text)
            if match:
                author_name = match.group(1)
                # 更新字典中作者名出现的次数
                if author_name:
                    author_counts[author_name] = author_counts.get(author_name, 0) + 1

    # 对字典按值进行排序
    sorted_author_counts = sorted(author_counts.items(), key=lambda x: x[1], reverse=True)

    # 打印排序后的前十位结果
    for i, (author, count) in enumerate(sorted_author_counts[:10], 1):
        print(f'{i}. {author}: {count}')
else:
    print('Failed to fetch page:', response.status_code)
