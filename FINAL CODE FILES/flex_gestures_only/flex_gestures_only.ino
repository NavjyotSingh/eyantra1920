/*
  Project Name: AIDE
  Variables for: Pin defintions, Resistors, buffers and Moving Average array

  The variables stand for flex sensors on the respective fingers:
  index: olive and a0
  middle: white and a1
  ring: black and a2
  little: red and a3

  The average and total variables are used for calculating moving average

  This code gives the actual gestures being detected and transmits them over bluetooth to raspberry pi

*/


int olive =0;
int white = 0;
int black=0;
int red=0;

const int a0= A0;
const int a1= A1;
const int a2= A2;
const int a3= A3;

int Vin= 5;
float R1= 47000;
const int numReadings = 50;
float readings[numReadings];
float readings1[numReadings];
float readings2[numReadings];
float readings3[numReadings]; // the readings from the analog input
int readIndex = 0;              // the index of the current reading
float total = 0;                  // the running total
float average = 0;                // the average
float total1 = 0;                  // the running total
float average1 = 0;
float total2 = 0;                  // the running total
float average2 = 0;
float total3 = 0;                  // the running total
float average3 = 0;

float buffer = 0;
float buffer1 = 0;
float buffer2 = 0;
float buffer3 = 0;


unsigned long int prev;


void setup() {
    Serial.begin(9600);
    // initialize all the readings to 0:
    for (int thisReading = 0; thisReading < numReadings; thisReading++) {
       readings[thisReading] = 0;
       readings1[thisReading] = 0;
       readings2[thisReading] = 0;
       readings3[thisReading] = 0;
    }
    prev = millis();
}

void loop() {
  // subtract the last reading:
  total = total - readings[readIndex];
  total1 = total1 - readings1[readIndex];
  total2 = total2 - readings2[readIndex];
  total3 = total3 - readings3[readIndex];
  
  // read from the sensor:
  readings[readIndex] = analogRead(a0);
  readings1[readIndex] = analogRead(a1);
  readings2[readIndex] = analogRead(a2);
  readings3[readIndex] = analogRead(a3);
  
  buffer = readings[readIndex] *Vin;
  buffer1 = readings1[readIndex] *Vin;
  buffer2 = readings2[readIndex] *Vin;
  buffer3 = readings3[readIndex] *Vin;
  
  readings[readIndex] = (buffer)/1024.0;
  readings1[readIndex] = (buffer1)/1024.0;
  readings2[readIndex] = (buffer2)/1024.0;
  readings3[readIndex] = (buffer3)/1024.0;
  
  // add the reading to the total:
  total = total + readings[readIndex];
  total1 = total1 + readings1[readIndex];
  total2 = total2 + readings2[readIndex];
  total3 = total3 + readings3[readIndex];
  
  // advance to the next position in the array:
  readIndex = readIndex + 1;

  // if we're at the end of the array...
  if (readIndex >= numReadings) {
    // ...wrap around to the beginning:
    readIndex = 0;
  }

  // calculate the average:
  average = total / numReadings;
  average1 = total1 / numReadings;
  average2 = total2 / numReadings;
  average3 = total3 / numReadings;

//Threshold values in terms of Voltages

 if(average && average1 && average2 && average3) 
    {
       if(average<=1.20)
            olive=0;
          else
            olive=1;
    
          if(average1<=1.30)
            white=0;
          else
            white=1;
    
          if(average2<=1.00)
            red=0;
          else
            red=1;
    
          if(average3<=1.50)
            black=0;
          else
            black=1;
    }


// Executes every 5 seconds for stability
   if( millis() - prev >= 5000UL)   
  {
    prev += 5000UL;
  Serial.print(olive);
  Serial.print(white);
  Serial.print(red);
  Serial.println(black);
  }


  delay(100);        // delay in between reads for stability
}
