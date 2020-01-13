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


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  value = analogRead(flexPin); 
  value1 = analogRead(flexPin1);
  value2 = analogRead(flexPin2);  
  value3 = analogRead(flexPin3);

//  Serial.print(0);
//  Serial.print(" ");
//  Serial.print(500);
//  Serial.print(" ");
//  Serial.print(value);
//  Serial.print(" ");
//  Serial.print(value1);
//  Serial.print(" ");
//  Serial.print(value2);
//  Serial.print(" ");
//  Serial.println(value3);
  Serial.print(value);
  Serial.print(","); Serial.print(value1);
  Serial.print(","); Serial.print(value2);
  Serial.print(","); Serial.println(value3);
  delay(1000);
}
