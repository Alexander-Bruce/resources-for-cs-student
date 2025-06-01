#include <iostream>
#include <string>
using namespace std;

struct IBase                                               //接口IBase
{
	virtual void base() = 0;                           //声明纯虚函数base()
};

struct IDerive1 :public IBase                          //接口IDerive1
{
	virtual void derive1() = 0;                      //声明纯虚函数derive1()
	virtual void base() = 0;                          //声明纯虚函数base()
};

struct IDerive2 :public IBase                          //接口IDerive2
{
	virtual void derive2() = 0;                      //声明纯虚函数derive2()
	virtual void base() = 0;                          //声明纯虚函数base()
};

void callderive1(IDerive1 &p)                         //定义函数callderive1()
{
	p.derive1();
	p.base();
}

void callderive2(IDerive2 &p)                         //定义函数callderive2()
{
	p.derive2();
	p.base();
}

class COuter :public IDerive1{                        //定义外部类继承自IDerive1
private:
	//外部类的私有变量
	string outer_private;                            //定义外部类私有变量

	class CInner :public IDerive2{                  //定义内部类，继承自IDerive2
	private:
		//定义外部类指针作为内部类成员，用于操作外部类
		COuter *parent;
	public:
		CInner(COuter *p) :parent(p)
		{
		}
		//重写基类IDerive2的虚函数,访问了外部类的私有成员 
		void derive2()
		{
			cout << "derive2 called for inner of " << parent->outer_private << endl;
		}
		//重写基类IDerive2的虚函数，虽然与外部类成员函数同名，但无歧义
		void base()
		{
			cout << "base called for inner" << endl;
		}
	};
	CInner inner;
public:
	//定义外部类构造函数
	COuter(const string &str) :outer_private(str), inner(this){}
	//重写基类IDerive1中的虚函数derive1()
	void derive1()
	{
		cout << "derive1 called for outer" << endl;
	}
	//重写基类IDerive1中的虚函数base()
	void base()
	{
		cout << "base called for outer" << endl;
	}
	//重载IDerive2&引用
	operator IDerive2&()
	{
		return inner;
	}
};
int main()
{
	COuter obj("outer");
	callderive1(obj);
	callderive2(obj);
	system("pause");
	return 0;
}
