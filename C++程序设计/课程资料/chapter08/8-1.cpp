#include <iostream>
#include <vector>
using namespace std;
int main()
{
	vector<int> v; //创建一个空的vector对象
	for (int i = 0; i < 10; i++)
		v.push_back(i + 1);  //将元素添加到v中
	for (int i = 0; i < 10; i++)
		cout << v[i] << "  ";  //输出元素
	cout << endl;
	v.pop_back();  //移除末尾的元素
	for (int i = 0; i < 9; i++) //此时元素个数为9
		cout << v[i] << "  ";  //输出元素
	cout << endl;
	system("pause");
	return 0;
}
