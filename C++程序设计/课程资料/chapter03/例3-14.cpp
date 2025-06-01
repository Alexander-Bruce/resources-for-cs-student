#include <iostream>
using namespace std;

class Bird{
public:
	void breath(double)   { cout << "bird breath!" << endl; }
};

class Fish{
public:
	void breath(int)   { cout << "fish breath!" << endl; }
};

class WaterBird :public Bird, public Fish{
public:
	void fly_swim() { cout << "waterbird cat fly and swim!" << endl; }
	
};

int main()
{
	WaterBird waterbird;
	waterbird.Bird::breath(4.5);
	
	return 0;
}
