#include <iostream>
using namespace std;
class Swap
{
private:
	int a, b;
public:
	Swap(int m, int n) :a(m), b(n){}
	operator char()  //类型转换运算符重载函数
	{
		return static_cast<char>(a);
	}
	void show()
	{
		cout << a << " " << b << endl;
	}
};

int main()
{
	Swap s1(65, 2); //调用普通构造函数创建对象
	cout << "s1: ";
	s1.show();
	char ch = s1; //调用类型转换函数
	cout << ch << endl;

	system("pause");
	return 0;
}
