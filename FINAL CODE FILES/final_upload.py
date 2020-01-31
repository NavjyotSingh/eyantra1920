"""
    Project Name: AIDE

    This script runs on the Raspberry Pi 
    It connects to the database using the firebase_admin package and updates the respective device status and logs on the cloud
    It also controls all the devices using GPIO pins on the RPI

"""



import RPi.GPIO as GPIO
import serial
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import datetime


#FIREBASE VARIABLES 
cred_path = r"/home/pi/Desktop/aide-d9b83-firebase-adminsdk-zjsxv-31389bf455.json" #path according to your json file
cred = credentials.Certificate(cred_path)
firebase_admin.initialize_app(cred)

db = firestore.client()

date = str(datetime.date.today())
houseID  = u'1234567890'

dev ={}
doc_id = u''
device =u''
status = u''

#BLUETOOTH COMMUNICATION PORT AND PIN SETUP
serial_port = '/dev/rfcomm1'
baud_rate = 9600

in1 = 16
in2 = 18
in3 = 22

GPIO.setmode(GPIO.BOARD)
GPIO.setup(in1, GPIO.OUT)
GPIO.setup(in2, GPIO.OUT)
GPIO.setup(in3, GPIO.OUT)
GPIO.output(in1, True)
GPIO.output(in2, False)
GPIO.output(in3, True)


#INFINITE LOOP THAT RUNS AND BREAKS ON KEYBOARD INTERRUPT

#THIS CODE OPERATES THE DEVICES, UPDATES DEVICE STATUS AND ALSO CREATES LOG
try:
    ser = serial.Serial(serial_port, baud_rate)
    while True:
        line = ser.readline()
        line = line.decode("utf-8")
        if line == '0000\r\n':
            while True:
                print(line)
                line = ser.readline()
                line = line.decode("utf-8")
                if line == '1000\r\n':
                    print(line)
                    GPIO.output(in1,False)
                    device = u'D1'
                    status = u'ON'
                if line == '1100\r\n':
                    print(line)
                    GPIO.output(in1,True)
                    device = u'D1'
                    status = u'OFF'
                if line == '1110\r\n':
                    print(line)
                    GPIO.output(in2,True)
                    device = u'D2'
                    status = u'ON'
                if line == '0111\r\n':
                    print(line)
                    GPIO.output(in2,False)
                    device = u'D2'
                    status = u'OFF'
                if line == '1101\r\n':
                    print(line)
                    GPIO.output(in3,False)
                    device = u'D3'
                    status = u'ON'
                if line == '1011\r\n':
                    print(line)
                    GPIO.output(in3,True)
                    device = u'D3'
                    status = u'OFF'
                if line == '1001\r\n':
                    print(line)
                    device = u'D4'
                    status = u'ON'
                if line == '0001\r\n':
                    print(line)
                    device = u'D4'
                    status = u'OFF'
                dev_ref = db.collection(u'Devices').where(u'HOUSE_ID',u'==',houseID).where(u'DEVICE_ID',u'==',device).stream()
                for doc in dev_ref:
                    doc_id = doc.id
                    dev = doc.to_dict()
                name = dev.get('NAME')


                log_ref = db.collection(u'Logs').document(houseID).collection(u'Logs')

                log_ref.add({
                    u'device' : device,
                    u'name' : name,
                    u'timestamp' :  str(datetime.datetime.now()),
                    u'status' : status
                })

                db.collection(u'Devices').document(doc_id).update({u'STATUS':status})
                if line == '0000\r\n':
                    print(line)
                    break
                

except KeyboardInterrupt:
    GPIO.cleanup()        
