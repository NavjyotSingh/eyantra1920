
#include "MegunoLink.h"
#include "Filter.h"
 
// Create a new exponential filter with a weight of 5 and an initial value of 0. 

 
void setup() 
{
  Serial.begin(9600);

}
 
void loop() 
{
  int CurrentVal = analogRead(A0);
//  int AverageVal = 0;
//  int MeasurementsToAverage = 10;
//  for(int i = 0; i < MeasurementsToAverage; ++i)
//  {
//    AverageVal += CurrentVal;
//    delay(1);
//  }
//  AverageVal /= MeasurementsToAverage;
//  Serial.print(0);  // To freeze the lower limit
//  Serial.print(" ");
//  Serial.print(1023);  // To freeze the upper limit
//  Serial.print(" ");
    Serial.print(0);
  Serial.print(" ");
  Serial.print(-1023);
  Serial.print(" ");
  Serial.print(1023);
  Serial.print(" ");
  Serial.println(CurrentVal);
  delay(1);
}
