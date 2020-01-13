
   

const int flexPin = A0; //pin A0 to read analog input
const int flexPin1 = A1;
const int flexPin2 = A2;
const int flexPin3 = A3;
//Variables:
int value;
int value1;
int value2;
int value3;//save analog value
/*TAKE CARE OF MAPPING FUNCTION
 * IT WILL CHANGE ACCORDING TO THE RESISTANCE 
 * USE THE SENSORS IN VOLTAGE DIVIDER CIRCUITS ONLY
 */
void setup(){
 
  Serial.begin(9600);       //Begin serial communication

}

void loop(){
  
  value = analogRead(flexPin);         //Read and save analog value from potentiometer
  value = constrain(value, 200,300);
  //Serial.println(value);               //Print value
  value = map(value, 200, 300, 0, 100 );//Map value 0-1023 to 0-255 (PWM)
  //Serial.println("Mapped value:");
  value = constrain(value,0,100);
  Serial.println(value);

    value1 = analogRead(flexPin1);         //Read and save analog value from potentiometer
  value1 = constrain(value1, 400,500);
//  //Serial.println(value);               //Print value
 value1 = map(value1, 400, 500, 0, 100 );//Map value 0-1023 to 0-255 (PWM)
//  //Serial.println("Mapped value:");
  value1 = constrain(value1,0,100);
  Serial.println(value1);

    value2 = analogRead(flexPin2);         //Read and save analog value from potentiometer
   //value2 = constrain(value2, 200,300);
//  //Serial.println(value);               //Print value
  //value2 = map(value2, 200, 300, 0, 100 );//Map value 0-1023 to 0-255 (PWM)
//  //Serial.println("Mapped value:");
  //value2 = constrain(value2,0,100);
  Serial.println(value2);

    value3 = analogRead(flexPin3);         //Read and save analog value from potentiometer
  value3 = constrain(value3, 300,400);
  //Serial.println(value);               //Print value
  value3 = map(value3, 300, 400, 0, 100 );//Map value 0-1023 to 0-255 (PWM)
  //Serial.println("Mapped value:");
  value3 = constrain(value3,0,100);
  Serial.println(value3);
  
  delay(3000);                          //Small delay
  
}
