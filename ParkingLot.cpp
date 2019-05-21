/*
Parking lot design where people can park Cars, and get the cars based on certain properties.
*/

#include <iostream>
#include <bits/stdc++.h>
using namespace std;

class Car{
 private:    
    string registrationNumber;
    string color;
 public:   
    void setRegistrationNumber(string inputregistrationNumber){
        registrationNumber = inputregistrationNumber;
    }
    void setColor(string inputcolor){
        color = inputcolor;
    }
    string getRegistrationNumber(){
        return registrationNumber;
    }
    string getColor(){
        return color;
    }
};

class EntireParkingLot{    
class ParkingSlot{
private:
    int slotNumber;
    Car car;
    bool isFree = true;
public:
    bool isSlotAvailable(){
        return isFree;
    }
    void setSlotAvailable(){
        isFree= true;
    }
    void setSlotFilled(){
        isFree= false;
    }
    int getSlotNumber(){
        return slotNumber;
    }
    void setSlotNumber(int inputslotNumber){
        slotNumber =inputslotNumber;
    }
    Car getCar(){
        return car;
    }
    void setCar(Car car1){
        car = car1;
    }
};
vector<ParkingSlot> ParkingLot;
public:
    void getSlotNumber(string inputregistrationNumber){
        for(int i=0;i<ParkingLot.size();i++){
            if(!ParkingLot[i].isSlotAvailable() && 
               ParkingLot[i].getCar().getRegistrationNumber() == inputregistrationNumber){
                cout<<ParkingLot[i].getSlotNumber()<<endl;
                return;
            }
        }    
        cout<<"Not Found"<<endl;
        return;
    }

  void parkCar(string inputregistrationNumber, string inputcolor, int entryPoint){
    for(int i=0;i<ParkingLot.size();i++){
         if(ParkingLot[i].isSlotAvailable()){
             Car car1;
             car1.setRegistrationNumber(inputregistrationNumber);
             car1.setColor(inputcolor);
             ParkingLot[i].setSlotFilled();
             ParkingLot[i].setCar(car1);
             cout<<"Allocated slot number:"<<ParkingLot[i].getSlotNumber()<<endl;
             return;
         }
    }
    cout<<"Sorry, Parking lot is full"<<endl;
  }
    
    void createParkingLot(int slots, int entryPoints){
      for(int i=0;i<slots;i++){
          ParkingSlot parkingsSlot;
          parkingsSlot.setSlotNumber(i+1);
          ParkingLot.push_back(parkingsSlot);
      }
      cout<<"Created parking lot with "<<slots<<" slots and "<<entryPoints<<" entrypoints"<<endl;
    }
    
    void leave(int slotNumber){
      ParkingLot[slotNumber-1].setSlotAvailable();
      cout<<"Slot number "<<slotNumber<<" is empty"<<endl;
    }

    void status(){
      cout<<"No   RegNumber   SlotNumber   Color"<<endl; 
      for(int i=0;i<ParkingLot.size();i++){
           if(!ParkingLot[i].isSlotAvailable()){
               string regNumber = ParkingLot[0].getCar().getRegistrationNumber();
               cout<<i+1<<"       "<<ParkingLot[i].getCar().getRegistrationNumber()<<"        "<<ParkingLot[i].getSlotNumber()<<"     "<<ParkingLot[i].getCar().getColor()<<endl;
           }
       }
    }

    void getAllRegNumberofColor(string inputcolor){
      int flag =0;
      for(int i=0;i<ParkingLot.size();i++){
           if(!ParkingLot[i].isSlotAvailable() && ParkingLot[i].getCar().getColor()==inputcolor){
               if(flag==1)cout<<",";
               cout<<ParkingLot[i].getCar().getRegistrationNumber()<<" ";
               flag=1;
           }
       }
      cout<<endl;
    }

    void getAllSlotsofColor(string inputcolor){
      int flag =0;
      for(int i=0;i<ParkingLot.size();i++){
           if(!ParkingLot[i].isSlotAvailable() && ParkingLot[i].getCar().getColor()==inputcolor){
               if(flag==1)cout<<",";
               cout<<ParkingLot[i].getSlotNumber()<<" ";
               flag=1;
           }
       }
      cout<<endl;
    }
};

int main() {
    EntireParkingLot entireParkingLot;
    entireParkingLot.createParkingLot(6,3);
    entireParkingLot.parkCar("ka-1234","white", 3);
    entireParkingLot.parkCar("ka-9999","white", 3);
    entireParkingLot.parkCar("ka-0001","black", 3);
    entireParkingLot.parkCar("ka-7777","red", 3);
    entireParkingLot.parkCar("ka-2701","blue", 3);
    entireParkingLot.parkCar("ka-3141","black", 3);
    entireParkingLot.parkCar("ka-3143","black", 3);
    entireParkingLot.leave(4);
    entireParkingLot.status();
    entireParkingLot.parkCar("ka-333","white", 3);
    entireParkingLot.parkCar("ka-9999","white", 3);
    entireParkingLot.getAllRegNumberofColor("white");
    entireParkingLot.getAllSlotsofColor("brown");
    entireParkingLot.getSlotNumber("ka-3141");
    entireParkingLot.getSlotNumber("ka-1111");
}
