#include <iostream>
using namespace std;

class A{
private:
	int x, y;
public:
	A(int x = 0, int y = 0) :x(x), y(y){};
	friend istream& operator>>(istream&, A&);
	friend ostream& operator<<(ostream&, const A&);
	friend A operator+(const A& a1, const A& a2);
	friend A operator-(const A& a1, const A& a2);
};

istream& operator>>(istream& in, A& a){
	in >> a.x >> a.y;
	return in;
}

ostream& operator<<(ostream& out, const A& a){
	out << "(" << a.x << ", " << a.y << ')';
	return out;
}

A operator+(const A& a1, const A& a2){
	return A(a1.x+a2.x, a1.y+a2.y);
}

A operator-(const A& a1, const A& a2){
	return A(a1.x-a2.x, a1.y-a2.y);
}

int main(){
	A a1(1, 3), a2(4, 5);
	cout << "a1: " << a1 << endl;
	cout << "a2: " << a2 << endl;
	cout << "a1+a2: " << a1+a2 << endl;
	cout << "a1-a2: " << a1-a2 << endl;
	
	A a;
	cin >> a;
	cout << "a: " << a;
	
	return 0;
	
}