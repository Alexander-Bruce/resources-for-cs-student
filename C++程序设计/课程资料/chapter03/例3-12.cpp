#include <iostream>
using namespace std;

class Bird{
public:
	Bird(int fh){
		cout << "Bird constructor!" << endl;
		m_nFlightAltitude = fh;
	}
	void fly_in_sky(){ cout << "bird fly in sky!" << endl; }
	void breath() { cout << "bird breath!" << endl; }
	int get_flightaltitude() { return m_nFlightAltitude; }
private:
	int m_nFlightAltitude;
};

class Fish{
public:
	Fish(int speed){
		cout << "Fish constructor!" << endl;
		m_nSwimSpeed = speed;
	}
	void swim_in_water()	{ cout << "fish swim in water!" << endl; }
	void breath() { cout << "fish breath!" << endl; }
	int get_swimspeed() { return m_nSwimSpeed; }
private:
	int m_nSwimSpeed;
};

class WaterBird :public Bird, public Fish{
public:
	WaterBird(int fh, int speed) :Bird(fh), Fish(speed){
		cout << "WaterBird constructor!" << endl;
	}
	void fly_swim() { cout << "waterbird cat fly and swim!" << endl; }
	void breath(){ cout << "waterbird breath!" << endl; }
};

int main(){
	WaterBird waterbird(20, 30);
	cout << "waterbird flight altitude: " << waterbird.get_flightaltitude();
	cout << ", swimming speed:" << waterbird.get_swimspeed() << endl;
	waterbird.fly_swim();
	waterbird.breath();
	waterbird.Fish::breath();
	waterbird.Bird::breath();
	
	return 0;
}
