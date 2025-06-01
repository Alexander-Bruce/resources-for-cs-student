#include <iostream>
using namespace std;

class A{
private:
	int x;
	int y;
public:
	A(int x1 = 0, int y1 = 0) :x(x1), y(y1){}
	void show() const;
	friend A operator+(const A& a1, const A& a2);
	friend A operator-(const A& a1, const A& a2);
};

// The keyword friend denotes that we can access private variables through these functions modified by friend.

void A::show() const{
	cout << "(x,y) = " << "(" << x << "," << y << ")" << endl;
}

A operator+(const A& a1, const A& a2){
	return A(a1.x+a2.x, a1.y+a2.y);
}

A operator-(const A& a1, const A& a2){
	return A(a1.x - a2.x, a1.y - a2.y);
}

int main(){
	A a1(1, 2);
	A a2(4, 5);
	A a;
	
	cout << "a1: ";
	a1.show();
	cout << "a2: ";
	a2.show();
	a = a1 + a2;
	cout << "a: "; a.show();

	a = a1 - a2;
	cout << "a: "; a.show();

	return 0;
}


// Notice one major difference from the previous example where we take in only one argument had the function been defined as a member function in the class
// And the reasoning is quite simple: since addition is a binary operator, 2 operands are required for addition. In the case of member function, the argument it receives is one of the two argument, and the other, no doubt, is itself, namely, the object that calls the function.
// Say a = a1+a2; In the former case, a2 is the argument to be passed in, a1 calls the overloading function.
// In this case, we have defined the function to be a friend function, so that it demands for 2 explicit written argument, which, from the essance of addition, is a lot easier to understand and is recommended as a coding norm.