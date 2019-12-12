#include<Wire.h>
const int MPU6050_addr=0x68;
int16_t AccX,AccY,AccZ,Temp,GyroX,GyroY,GyroZ;


const int flexPin = A0; //pin A0 to read analog input
const int flexPin1 = A1;
const int flexPin2 = A2;
const int flexPin3 = A3;
//Variables:
int value;
int value1;
int value2;
int value3;//save analog value

/*
  The olive, white, black, red pattern is the same as old pattern.
  No changes over there.

*/
int olive =0;
int white = 0;
int black=0;
int red=0;


void setup(){
  Wire.begin();
  Wire.beginTransmission(MPU6050_addr);
  Wire.write(0x6B);
  Wire.write(0);
  Wire.endTransmission(true);
  Serial.begin(9600);
}



void loop(){

  value = analogRead(flexPin);         //Read and save analog value from potentiometer
  value = constrain(value, 200,300);
  value = map(value, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
  value = constrain(value,0,50);


  value1 = analogRead(flexPin1);         //Read and save analog value from potentiometer
  value1 = constrain(value1, 400,500);
  value1 = map(value1, 400, 500, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
  value = constrain(value1,0,50);


  value2 = analogRead(flexPin2);         //Read and save analog value from potentiometer
  value2 = constrain(value2, 200,300);
  value2 = map(value2, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
  value2 = constrain(value2,0,50);


  value3 = analogRead(flexPin3);         //Read and save analog value from potentiometer
  value3 = constrain(value3, 300,400);
  value3 = map(value3, 300, 400, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
  value3 = constrain(value3,0,50);


 if(value && value1 && value2 && value3) 
    {
       if(value<=20)
            olive=0;
          else
            olive=1;
    
          if(value1<=15)
            white=0;
          else
            white=1;
    
          if(value2<=5)
            red=0;
          else
            red=1;
    
          if(value3<=20)
            black=0;
          else
            black=1;
    }

   if(olive==0 && white==0 && red==0 && black==0)
      {
        delay(2000);
        Serial.println("Device Control Mode");
        Serial.println("Conditions :");
        Serial.println("1. Light 1 ON: Index finger open, others closed\n2. Light 1 OFF: Index and middle finger open, others closed\n");
        Serial.println("3. Fan 1 ON: Little finger closed, others open\n4. Fan 1 OFF: Index finger closed, others open\n");
        Serial.println("5. Window 1 OPEN: Ring finger closed, others open\n6. Window 1 CLOSE: IMiddle finger closed, others open\n");
        Serial.println("7. Fan 1 speed HIGH: Move hand to the right\n8. Fan 1 speed LOW: Move hand downwards\n");
        for(;;)
        {
           delay(3000);
             
            value = analogRead(flexPin);         //Read and save analog value from potentiometer
            value = constrain(value, 200,300);
            value = map(value, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
            value = constrain(value,0,50);
          
          
            value1 = analogRead(flexPin1);         //Read and save analog value from potentiometer
            value1 = constrain(value1, 400,500);
            value1 = map(value1, 400, 500, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
            value = constrain(value1,0,50);
          
          
            value2 = analogRead(flexPin2);         //Read and save analog value from potentiometer
            value2 = constrain(value2, 200,300);
            value2 = map(value2, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
            value2 = constrain(value2,0,50);
          
          
            value3 = analogRead(flexPin3);         //Read and save analog value from potentiometer
            value3 = constrain(value3, 300,400);
            value3 = map(value3, 300, 400, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
            value3 = constrain(value3,0,50);

    
           if(value<=20)
                  olive=0;
                else
                  olive=1;
          
                if(value1<=15)
                  white=0;
                else
                  white=1;
          
                if(value2<=5)
                  red=0;
                else
                  red=1;
          
                if(value3<=20)
                  black=0;
                else
                  black=1;


    
    //          Serial.print("Olive: ");
    //          Serial.print(olive);
    //          Serial.print("  White: ");
    //          Serial.print(white);
    //          Serial.print("  Red: ");
    //          Serial.print(red);
    //          Serial.print("  Black: ");
    //          Serial.println(black);
                 
              if(olive==1 && white==0 && red==0 && black==0){
                Serial.println("Light ON");
    //            digitalWrite(mcuOlive,HIGH);digitalWrite(mcuWhite,LOW);digitalWrite(mcuRed,LOW);digitalWrite(mcuBlack,LOW);
    //            Serial.print(digitalRead(mcuOlive));Serial.print(digitalRead(mcuWhite));Serial.print(digitalRead(mcuRed));Serial.println(digitalRead(mcuBlack));
                delay(5000);
                }
              if(olive==1 && white==1 && red==0 && black==0){
                Serial.println("Light OFF");
    //            digitalWrite(mcuOlive,HIGH);digitalWrite(mcuWhite,HIGH);digitalWrite(mcuRed,LOW);digitalWrite(mcuBlack,LOW);
    //            Serial.print(digitalRead(mcuOlive));Serial.print(digitalRead(mcuWhite));Serial.print(digitalRead(mcuRed));Serial.println(digitalRead(mcuBlack));
                delay(5000);
                }
              if(olive==1 && white==1 && red==1 && black==0){
                Serial.println("Fan ON");
    //            digitalWrite(mcuOlive,HIGH);digitalWrite(mcuWhite,HIGH);digitalWrite(mcuRed,HIGH);digitalWrite(mcuBlack,LOW);
    //            Serial.print(digitalRead(mcuOlive));Serial.print(digitalRead(mcuWhite));Serial.print(digitalRead(mcuRed));Serial.println(digitalRead(mcuBlack));
                delay(5000);
                }
              if(olive==0 && white==1 && red==1 && black==1){
                Serial.println("Fan OFF");
    //            digitalWrite(mcuOlive,LOW);digitalWrite(mcuWhite,HIGH);digitalWrite(mcuRed,HIGH);digitalWrite(mcuBlack,HIGH);
    //            Serial.print(digitalRead(mcuOlive));Serial.print(digitalRead(mcuWhite));Serial.print(digitalRead(mcuRed));Serial.println(digitalRead(mcuBlack));
                delay(5000);
                }
              if(olive==1 && white==1 && red==0 && black==1){
                Serial.println("Window OPEN");
    //            digitalWrite(mcuOlive,HIGH);digitalWrite(mcuWhite,HIGH);digitalWrite(mcuRed,LOW);digitalWrite(mcuBlack,HIGH);
    //            Serial.print(digitalRead(mcuOlive));Serial.print(digitalRead(mcuWhite));Serial.print(digitalRead(mcuRed));Serial.println(digitalRead(mcuBlack));
                delay(5000);
                }
              if(olive==1 && white==0 && red==1 && black==1){
                Serial.println("Window CLOSE");
    //            digitalWrite(mcuOlive,HIGH);digitalWrite(mcuWhite,LOW);digitalWrite(mcuRed,HIGH);digitalWrite(mcuBlack,HIGH);
    //            Serial.print(digitalRead(mcuOlive));Serial.print(digitalRead(mcuWhite));Serial.print(digitalRead(mcuRed));Serial.println(digitalRead(mcuBlack));
                delay(5000);
                }
            
                if(olive==1 && white==0 && red==0 && black==1){
                  Serial.println("FAN CONTROL MODE");
                  while(1){
                    
                        /* 
                          DEFINE FAN CONTROL MODE. 
                          MOSTLY TAKE IT AWAY FROM ACCELEROMETER
                        */
                            value = analogRead(flexPin);         //Read and save analog value from potentiometer
                            value = constrain(value, 200,300);
                            value = map(value, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                            value = constrain(value,0,50);
                          
                          
                            value1 = analogRead(flexPin1);         //Read and save analog value from potentiometer
                            value1 = constrain(value1, 400,500);
                            value1 = map(value1, 400, 500, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                            value = constrain(value1,0,50);
                          
                          
                            value2 = analogRead(flexPin2);         //Read and save analog value from potentiometer
                            value2 = constrain(value2, 200,300);
                            value2 = map(value2, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                            value2 = constrain(value2,0,50);
                          
                          
                            value3 = analogRead(flexPin3);         //Read and save analog value from potentiometer
                            value3 = constrain(value3, 300,400);
                            value3 = map(value3, 300, 400, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                            value3 = constrain(value3,0,50);
  
               
          
                           if(value<=20)
                              olive=0;
                            else
                              olive=1;
                      
                            if(value1<=15)
                              white=0;
                            else
                              white=1;
                      
                            if(value2<=5)
                              red=0;
                            else
                              red=1;
                      
                            if(value3<=20)
                              black=0;
                            else
                              black=1;
      
                       
                           if(olive==1 && white==0 && red==0 && black==0){
                                Serial.println("FAN SLOW");
                    //            digitalWrite(mcuOlive,HIGH);digitalWrite(mcuWhite,LOW);digitalWrite(mcuRed,LOW);digitalWrite(mcuBlack,LOW);
                    //            Serial.print(digitalRead(mcuOlive));Serial.print(digitalRead(mcuWhite));Serial.print(digitalRead(mcuRed));Serial.println(digitalRead(mcuBlack));
                                delay(5000);
                                }
                          if(olive==1 && white==1 && red==0 && black==0){
                                Serial.println("FAN FAST");
                    //            digitalWrite(mcuOlive,HIGH);digitalWrite(mcuWhite,HIGH);digitalWrite(mcuRed,LOW);digitalWrite(mcuBlack,LOW);
                    //            Serial.print(digitalRead(mcuOlive));Serial.print(digitalRead(mcuWhite));Serial.print(digitalRead(mcuRed));Serial.println(digitalRead(mcuBlack));
                                delay(5000);
                                }
                     
                                value = analogRead(flexPin);         //Read and save analog value from potentiometer
                                value = constrain(value, 200,300);
                                value = map(value, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                                value = constrain(value,0,50);
                              
                              
                                value1 = analogRead(flexPin1);         //Read and save analog value from potentiometer
                                value1 = constrain(value1, 400,500);
                                value1 = map(value1, 400, 500, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                                value = constrain(value1,0,50);
                              
                              
                                value2 = analogRead(flexPin2);         //Read and save analog value from potentiometer
                                value2 = constrain(value2, 200,300);
                                value2 = map(value2, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                                value2 = constrain(value2,0,50);
                              
                              
                                value3 = analogRead(flexPin3);         //Read and save analog value from potentiometer
                                value3 = constrain(value3, 300,400);
                                value3 = map(value3, 300, 400, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                                value3 = constrain(value3,0,50);


                          if(value<=20)
                            olive=0;
                          else
                            olive=1;
                    
                          if(value1<=15)
                            white=0;
                          else
                            white=1;
                    
                          if(value2<=5)
                            red=0;
                          else
                            red=1;
                    
                          if(value3<=20)
                            black=0;
                          else
                            black=1;

                            if(olive==0 && white==0 && red==0 && black==0)
                              {
                                delay(2000);
                                if(olive==0 && white==0 && red==0 && black==0){
                                      Serial.println("Out Of Fan Control Mode");
                                      break;}
                              }
                  }
                    
              }
                       delay(2000);
                        value = analogRead(flexPin);         //Read and save analog value from potentiometer
                        value = constrain(value, 200,300);
                        value = map(value, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                        value = constrain(value,0,50);
                      
                      
                        value1 = analogRead(flexPin1);         //Read and save analog value from potentiometer
                        value1 = constrain(value1, 400,500);
                        value1 = map(value1, 400, 500, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                        value = constrain(value1,0,50);
                      
                      
                        value2 = analogRead(flexPin2);         //Read and save analog value from potentiometer
                        value2 = constrain(value2, 200,300);
                        value2 = map(value2, 200, 300, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                        value2 = constrain(value2,0,50);
                      
                      
                        value3 = analogRead(flexPin3);         //Read and save analog value from potentiometer
                        value3 = constrain(value3, 300,400);
                        value3 = map(value3, 300, 400, 0, 50 );//Map value 0-1023 to 0-255 (PWM)
                        value3 = constrain(value3,0,50);


                          
                                if(value<=20)
                                  olive=0;
                                else
                                  olive=1;
                          
                                if(value1<=15)
                                  white=0;
                                else
                                  white=1;
                          
                                if(value2<=5)
                                  red=0;
                                else
                                  red=1;
                          
                                if(value3<=20)
                                  black=0;
                                else
                                  black=1;

                          if(olive==0 && white==0 && red==0 && black==0)
                          {
                            delay(2000);
                            if(olive==0 && white==0 && red==0 && black==0){
                              Serial.println("Out Of Mode");
                              break;}
                            }
                          }
        }

        
        else {
                Wire.beginTransmission(MPU6050_addr);
                Wire.write(0x3B);
                Wire.endTransmission(false);
                Wire.requestFrom(MPU6050_addr,14,true);
                AccX=Wire.read()<<8|Wire.read();
                AccY=Wire.read()<<8|Wire.read();
                AccZ=Wire.read()<<8|Wire.read();
                Temp=Wire.read()<<8|Wire.read();
                GyroX=Wire.read()<<8|Wire.read();
                GyroY=Wire.read()<<8|Wire.read();
                GyroZ=Wire.read()<<8|Wire.read();
                Serial.print("AccX = "); Serial.print(AccX);
                Serial.print(" || AccY = "); Serial.print(AccY);
                Serial.print(" || AccZ = "); Serial.print(AccZ);
                Serial.print(" || Temp = "); Serial.print(Temp/340.00+36.53);
                Serial.print(" || GyroX = "); Serial.print(GyroX);
                Serial.print(" || GyroY = "); Serial.print(GyroY);
                Serial.print(" || GyroZ = "); Serial.println(GyroZ);
                
          }
         delay(3000);
}
