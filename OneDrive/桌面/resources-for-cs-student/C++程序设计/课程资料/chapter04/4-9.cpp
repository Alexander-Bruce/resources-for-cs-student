#include <iostream>
using namespace std;
class Swap
{
private:
	int a, b;
public:
	Swap(int m, int n) :a(m), b(n){}
	Swap(double c)  //转换构造函数
	{
		cout << "swap constructor is calling" << endl;
		a = static_cast<int>(c);
		b = 0;
	}
	void show()
	{
		cout << a << " " << b << endl;
	}
};
int main()
{
	Swap s1(1, 2); //调用普通构造函数创建对象
	Swap s2(3.2); //调用转换构造函数
	cout << "s1: ";
	s1.show();
	cout << "s2: ";
	s2.show();

	system("pause");
	return 0;
}
