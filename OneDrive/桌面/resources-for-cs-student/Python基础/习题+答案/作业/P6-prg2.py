def find_maxnumber(numberA, numberB, numberC):
    if numberA > numberB and numberA > numberC:
        return numberA
    elif numberB > numberA and numberB > numberC:
        return numberB
    else:
        return numberC
if __name__ == '__main__':
    numberA = int(input("请输入第一个整数："))
    numberB = int(input("请输入第二个整数："))
    numberC = int(input("请输入第三个整数："))
    print(f"最大的整数是{find_maxnumber(numberA, numberB, numberC)}。")