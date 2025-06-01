import requests
from bs4 import BeautifulSoup

# 发送请求
url = 'https://so.gushiwen.org/gushi/tangshi.aspx'
response = requests.get(url)

# 检查请求是否成功
if response.status_code == 200:
    # 使用 BeautifulSoup 解析 HTML
    soup = BeautifulSoup(response.text, 'html.parser')

    # 找到所有 class 为 "bookMI" 的 div 标签
    bookMI_tags = soup.find_all(class_='bookMl')

    # 遍历每个 bookMI 标签
    for bookMI_tag in bookMI_tags:
        # 找到当前 bookMI 标签内的内容
        content = bookMI_tag.get_text(strip=True)
        
        # 统计当前 bookMI 标签后面直到下一个 bookMI 标签的 span 标签个数
        next_sibling = bookMI_tag.find_next_sibling()
        count = 0
        while next_sibling and 'bookMl' not in next_sibling.get('class', []):
            if next_sibling.name == 'span':
                count += 1
            next_sibling = next_sibling.find_next_sibling()

        # 打印统计结果
        print(f'The content equals : {content}, having : {count}')
else:
    print('Failed to fetch page:', response.status_code)
