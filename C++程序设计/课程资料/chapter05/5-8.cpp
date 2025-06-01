#include <iostream>
using namespace std;
template<typename T>
class A
{
private:
	T item;
public:
	A(const T& t) :item(t){}
	template<typename U, typename V> //在类内部声明函数模板
	friend void show(U& u, V& v);
};
template<typename U, typename V>
void show(U& u, V& v)
{
	cout << u.item << "," << v.item << endl;
}
int main()
{
	A<int> a(10);
	A<int> b(20);
	A<double> c(1.2);
	cout << "a,b: ";
	show(a, b);
	cout << "a,c: ";
	show(a, c);
	system("pause");
	return 0;
}
