const int flexPin = A0; //pin A0 to read analog input
const int flexPin1 = A1;
const int flexPin2 = A2;
const int flexPin3 = A3;
//Variables:
int value;
int value1;
int value2;
int value3;//save analog value
int v1;
int v;
int v2;
int v3;


int mv1;
const int numReadings = 1;
int readings[numReadings];      // the readings from the analog input
int readIndex = 0;              // the index of the current reading
int total = 0;                  // the running total
int average = 0;                // the average




void setup() {
  Serial.begin(9600);
   for (int thisReading = 0; thisReading < numReadings; thisReading++) {
    readings[thisReading] = 0;
  }

}

int AvgFunc(int CurrentVal){
    int AverageVal = 0;
    int MeasurementsToAverage = 50;
    for(int i = 0; i < MeasurementsToAverage; ++i)
    {
      AverageVal += CurrentVal;
      
    }
    AverageVal /= MeasurementsToAverage;
    return AverageVal;
  }



int MovingAvg(int CurrentVal) {
  // subtract the last reading:
  total = total - readings[readIndex];
  // read from the sensor:
  readings[readIndex] = CurrentVal;
  //readings[readIndex] = analogRead(inputPin);
  // add the reading to the total:
  total = total + readings[readIndex];
  // advance to the next position in the array:
  readIndex = readIndex + 1;

  // if we're at the end of the array...
  if (readIndex >= numReadings) {
    // ...wrap around to the beginning:
    readIndex = 0;
  }

  // calculate the average:
  average = total / numReadings;
  // send it to the computer as ASCII digits
  return average;
}



void loop() {
  value = analogRead(flexPin); 
  value1 = analogRead(flexPin1);
  value2 = analogRead(flexPin2);  
  value3 = analogRead(flexPin3);  
//
  v = AvgFunc(value);
  v1 = AvgFunc(value1);
  v2 = AvgFunc(value2);
  v3 = AvgFunc(value3);
//
 Serial.print(0);  // To freeze the lower limit
Serial.print(" ");
Serial.print(500);  // To freeze the upper limit
 Serial.print(" ");
//  Serial.print(v);
// Serial.print(" ");
//  Serial.print(v1);
//  Serial.print(" ");
 Serial.println(value2);
//  Serial.print(" ");
//  Serial.println(v3);
delay(3);

//
//  mv1 = MovingAvg(value);
//  Serial.println(mv1);
//  delay(1);








}
