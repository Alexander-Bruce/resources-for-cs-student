#include <iostream>
#include <vector>
using namespace std;
int main()
{
	vector<char> v;  //创建空的vector对象v
	v.insert(v.begin(), 'a');  //在头部位置插入元素
	v.insert(v.begin(), 'b');
	v.insert(v.begin(), 'c');
	v.insert(v.begin() + 1, 5, 't');  //在begin()+1位置插入5个t元素
	for (int i = 0; i < 8; i++)  //输出元素
		cout << v[i] << " ";
	cout << endl;
	cout << "erase elems:\n";
	v.erase(v.begin() + 1, v.begin() + 6);//删除begin()+1到begin()+6区间的5个元素
	for (int i = 0; i <3; i++)
		cout << v[i] << " ";
	cout << endl;
	system("pause");
	return 0;
}
