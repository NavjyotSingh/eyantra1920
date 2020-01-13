void setup() {
  Serial.begin(9600);

}
int a = 1;
void loop() {
  // put your main code here, to run repeatedly:
  Serial.println(a);
  delay(1000);
}
