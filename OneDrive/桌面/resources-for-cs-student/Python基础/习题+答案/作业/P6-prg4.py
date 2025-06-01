L= [(92,88), (79,99), (84,92), (66, 77)]
maxChinese = max(L, key=lambda L: L[0])
maxMath = max(L, key=lambda L: L[1])
print(f"语文最高分是{maxChinese[0]}，数学最高分是{maxMath[1]}。")