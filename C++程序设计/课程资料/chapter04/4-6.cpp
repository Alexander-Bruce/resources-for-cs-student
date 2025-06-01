#include <iostream>
using namespace std;

class Internet{
public:
	char* name;
	char* url;
public:
	Internet(char* name, char* url);
	Internet(Internet& temp);
	~Internet(){
		delete[]name;
		delete[]url;
	}
	Internet& operator= (Internet& temp);
};

Internet::Internet(char* name, char* url){
	this->name = new char[strlen(name) + 1];
	this->url = new char[strlen(url) + 1];
	if (name) strcpy(this->name, name);
	if (url) strcpy(this->url, url);
}

Internet::Internet(Internet& temp){
	this->name = new char[strlen(temp.name) + 1];
	this->url = new char[strlen(temp.url) + 1];
	if (name) strcpy(this->name, temp.name);
	if (url) strcpy(this->url, temp.url);
}

Internet& Internet:: operator= (Internet& temp){
	delete[]name;
	delete[]url;
	this->name = new char[strlen(temp.name) + 1];
	this->url = new char[strlen(temp.url) + 1];
	if (name) strcpy(this->name, temp.name); 
	// This if statement is written to judge if we have successfully applied for a new block of memory.
	if (url) strcpy(this->url, temp.url);

	return *this;
}

int main(){
	Internet a("传智播客", "http://net.itcast.cn/");
	cout << "a对象： " << a.name << " " << a.url << endl;

	Internet b(a);
	cout << "b对象： " << b.name << " " << b.url << endl;

	Internet c("黑马训练营", "http://www.itheima.com/");
	cout << "c对象： " << c.name << " " << c.url << endl;

	b = c;
	cout << "b对象： " << b.name << " " << b.url << endl;
	return 0;
}
