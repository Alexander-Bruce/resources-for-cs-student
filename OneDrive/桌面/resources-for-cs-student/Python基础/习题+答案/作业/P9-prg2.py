import requests
from bs4 import BeautifulSoup

# 发送请求
url = 'https://so.gushiwen.org/gushi/tangshi.aspx'
response = requests.get(url)

# 检查请求是否成功
if response.status_code == 200:
    # 使用 BeautifulSoup 解析 HTML
    soup = BeautifulSoup(response.text, 'html.parser')

    # 找到 class 为 "sons" 的 div 元素
    sons_div = soup.find(class_='sons')

    # 统计 span 标签的个数
    if sons_div:
        span_tags = sons_div.find_all('span')
        print('Number of TangShi inside this website is:', len(span_tags))
    else:
        print('No any TangShi found inside this website')
else:
    print('Failed to fetch page:', response.status_code)
