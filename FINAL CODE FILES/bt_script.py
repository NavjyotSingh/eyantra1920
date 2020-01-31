"""
    Project Name: AIDE

    This script collects accelerometer data for Human Activity Recogniton
    The data is collected via Bluetooth and saved to a file

"""


import serial
import time
serial_port = 'COM5'
baud_rate = 9600 
write_to_file_path = "data_lying31.txt"

output_file = open(write_to_file_path, "a")
ser = serial.Serial(serial_port, baud_rate)
while True:
    line = ser.readline()
    line = line.decode("utf-8") 

    ts = time.time()
    row = str(ts) + ","+ line
    output_file.write(row)
    print(line)





    