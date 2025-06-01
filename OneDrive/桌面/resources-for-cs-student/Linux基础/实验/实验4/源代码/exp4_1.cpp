#include <stdio.h>

int is_prime(int n) {
    if (n <= 1) return 0; // 0 和 1 不是质数
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) return 0; // 存在因数则不是质数
    }
    return 1;
}

int main() {
    int num;
    printf("请输入一个整数: ");
    scanf("%d", &num);
    if (is_prime(num)) {
        printf("%d 是质数。\n", num);
    } else {
        printf("%d 不是质数。\n", num);
    }
    return 0;
}

