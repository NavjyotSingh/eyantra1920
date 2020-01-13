
import serial
import time
serial_port = 'COM4'
baud_rate = 9600 



ser = serial.Serial(serial_port, baud_rate)
while True:
    line = ser.readline()
    line = line.decode("utf-8") 
    if line == '0000':
    	while True:
	    	#Enters into device control mode
	    	line = ser.readline()
	    	line = line.decode("utf-8")
	    	if line == '1000':
	    	#Set Light pin HIGH
	    	if line == '1100':
	    	#light LOW
	    	if line == '1110':
	    	#Fan HIGH
	    	if line == '0111':
	    	#FAN LOW
	    	if line == '1101':
	    	#WINDOW OPEN
	    	if line == '1011':
	    	#WINDOW CLOSE
	    	if line == '1001':
	    	#FAN CONTROL MODE
	    		while True:
	    			if line == '1000':
	    				#SLOW DOWN
	    			if line == '1100':
	    				#FAST
	    			if line == '0000':
	    				break;
	    	if line == '0000':
	    		break;
	    else:
	    	#PUT IT INTO MODEL 
	    	#STORE INTO DB AND LOG FILE