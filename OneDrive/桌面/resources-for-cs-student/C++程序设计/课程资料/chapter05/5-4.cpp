#include <iostream>
#include <string>
using namespace std;
template<typename T>
class Compare
{
private:
	T t1, t2;
public:
	Compare(T a, T b) :t1(a), t2(b){}
	T max(){ return t1 > t2 ? t1 : t2; }
	T min(){ return t1 < t2 ? t1 : t2; }
};
int main()
{
	Compare<int> c1(1, 2); //定义int类型的类对象
	cout << "int max: " << c1.max() << endl;
	Compare<double> c2(1.2, 3.4); //定义double类型的对象
	cout << "double min: " << c2.min() << endl;
	Compare<char> c3('a', 'b');// 定义char类型的对象
	cout << "char max: " << c3.max() << endl;
	system("pause");
	return 0;
}
