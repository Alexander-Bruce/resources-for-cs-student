#include <iostream>
using namespace std;
//定义函数模板
template<typename T>
bool IsEqual(T t1, T t2)
{
	return t1 == t2;
}
//函数模板的特化
template<>
bool IsEqual(char* s1, char* s2)
{
	return strcmp(s1, s2) == 0;
}
//类模板
template<typename T>
class Compare
{
public:
	bool IsEqual(T t1, T t2)
	{
		return t1 == t2;
	}
};
//类模板特化
template<>
class Compare<char*>
{
public:
	bool IsEqual(char* s1, char* s2)
	{
		return strcmp(s1, s2) == 0;
	}
};
int main()
{
	char str1[] = "abc";
	char str2[] = "abc";
	cout << "函数模板和函数模板特化" << endl;
	cout << IsEqual(1, 3) << endl;  //调用函数模板
	cout << IsEqual(str1, str2) << endl;  //调用特化的函数模板
	Compare<int> c1;
	Compare<char*> c2;
	cout << "类模板和类模板特化" << endl;
	cout << c1.IsEqual(1.2, 3.4) << endl; //调用类模板
	cout << c2.IsEqual(str1, str2) << endl; //调用特化的类模板
	system("pause");
	return 0;
}
