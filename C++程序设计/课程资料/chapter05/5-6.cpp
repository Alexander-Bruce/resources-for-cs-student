#include <iostream>
using namespace std;
template<typename T>
class A
{
	T item;
	static int count;  //静态变量
public:
	A(const T& t) :item(t){ count++; }
	~A(){ count--; }
	friend void func();  //无参友元函数
	friend void show(const A<T>& a); //有参友元函数
};
template<typename T>
int A<T>::count = 0;  //初始化静态变量

void func()  //func()函数实现
{
	cout << "int count: " << A<int>::count << ";";
	cout << "double count: " << A<double>::count << ";" << endl;
}
void show(const A<int>& a)  //模板形参为int类型
{
	cout << "int: " << a.item << endl;
}

void show(const A<double>& a)
{
	cout << "double: " << a.item << endl;
}
int main()
{
	func();  //调用无参友元函数
	A<int> a(10);  //创建int类型对象
	func();
	A<double> b(1.2);
	show(a);  //调用有参数的友元函数
	show(b);
	system("pause");
	return 0;
}
