def is_prime(n):
    if n < 2:
        return False
    for i in range(2, n):
        if n % i == 0:
            return False
    return True

def prime_sum_between(n, m):
    array = []
    for i in range(n, m + 1):
        if is_prime(i):
            array.append(i)
    return sum(array)
    
if __name__ == '__main__':
    n = int(input("请输入整数n："))
    m = int(input("请输入整数m："))
    print(f"{n}到{m}之间所有素数的和为{prime_sum_between(n, m)}。")