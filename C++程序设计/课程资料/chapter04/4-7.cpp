#include <iostream>
using namespace std;

class Array{
private:
	int size;
	char* buf;
public:
	Array(int n);
	Array(char* src);
	~Array(){
		delete[]buf;
	}
	char& operator[](int n);
	void show(){
		for (int i = 0; i < size; i++)
			cout << buf[i];
		cout << endl;
	}
};

Array::Array(int n){
	size = n;
	buf = new char[size + 1];
	*(buf + size) = '\0';
}

Array::Array(char* src){
	buf = new char[strlen(src) + 1];
	strcpy(buf, src);
	size = strlen(buf);
}

char& Array::operator[](int n){
	static char ch = 0;
	if( n > size || n < 0 ) return ch;
	return *(buf+n);
}
// One thing to notice, we define the return type as char& because we need to assign a value to this particular position if there ever exist such a place in the memory.
// 

int main(){
	Array arr1(20);
	for (int i = 0; i < 20; i++)
		arr1[i] = 65 + i;
	arr1.show();

	Array arr2("chuan zhi bo ke!");
	cout << arr2[6] << endl;
	arr2[6] = 'A';
	arr2.show();
	
	return 0;
}