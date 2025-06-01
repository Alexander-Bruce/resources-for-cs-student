import re

def validate_phone_number(phone_number):
    # 电话号码必须是8位号码，如果有区号，区号必须是3位
    phone_number_pattern = r'^\d{3}-?\d{8}$'
    if re.match(phone_number_pattern, phone_number):
        return True
    else:
        return False

def validate_postal_code(postal_code):
    # 邮政编码必须是6位数字
    postal_code_pattern = r'^\d{6}$'
    if re.match(postal_code_pattern, postal_code):
        return True
    else:
        return False

def validate_website(website):
    # 网站网址的正则表示
    website_pattern = r'^https?://\w+(?:\.[^\.]+)+(?:/.+)*$'
    if re.match(website_pattern, website):
        return True
    else:
        return False

# 输入3个字符串
phone_number = input("请输入电话号码：")
postal_code = input("请输入邮政编码：")
website = input("请输入网站网址：")

# 验证输入的字符串是否符合格式要求并输出结果
if validate_phone_number(phone_number):
    print("电话号码格式正确")
else:
    print("电话号码格式错误")

if validate_postal_code(postal_code):
    print("邮政编码格式正确")
else:
    print("邮政编码格式错误")

if validate_website(website):
    print("网站网址格式正确")
else:
    print("网站网址格式错误")
