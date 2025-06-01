#include <iostream>
using namespace std;
int max(const int& a, const int& b)  //非模板函数，求两个int类型数据的最大者
{
	return a>b ? a : b;
}
template<typename T>   //定义求两个任意类型数据的最大值
T max(const T& t1, const T& t2)
{
	return t1 > t2 ? t1 : t2;
}
template<typename T>  //定义求三个任意类型数据的最大值
T max(const T& t1, const T& t2, const T&t3)
{
	return max(max(t1, t2), t3);
}
int main()
{
	cout << max(1, 2) << endl;  //调用非模板函数
	cout << max(1, 2, 3) << endl; //调用三个参数的函数模板
	cout << max('a', 'e') << endl; //调用两个参数的函数模板
	cout << max(6, 3.2) << endl;  //调用非模板函数

	return 0;
}
