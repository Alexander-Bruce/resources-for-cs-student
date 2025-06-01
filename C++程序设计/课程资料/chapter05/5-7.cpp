#include <iostream>
using namespace std;
//函数模板声明
template<typename T>
void func();
template<typename T>
void show(T& t);
//类模板定义
template<typename U>
class A
{
private:
	U item;
	static int count;
public:
	A(const U& u) :item(u){ count++; }
	~A(){ count--; }
	friend void func<U>(); //友元函数模板
	friend void show<>(A<U>& a); //友元函数模板
};
template<typename T>
int A<T>::count = 0;
//友元函数模板的定义
template<typename T>
void func()
{
	cout << "template size: " << sizeof(A<T>) << ";";
	cout << " template func(): " << A<T>::count << endl;
}
template<typename T>
void show(T& t)
{
	cout << t.item << endl;
}

int main()
{
	func<int>();  //调用int类型的函数模板实例，int类型，其大小为4字节
	A<int> a(10);  //定义类对象
	A<int> b(20);
	A<double> c(1.2);
	show(a); //调用show()函数，输出类对象的数据成员值
	show(b);
	show(c);
	cout << "func<int> output:\n";
	func<int>();  //运行到此，已经创建了两个int类型对象
	cout << "func<double>() output:\n";
	func<double>();

	system("pause");
	return 0;
}
