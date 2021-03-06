import libjevois as jevois
import cv2
import numpy as np
import json
import math
import time

#MOVE TARGET ANGLE TO FIRST FOR LOOP
#Draw contours in first for loop
#

class TapeDetectCody:
	# ###################################################################################################
	## Constructor
	
	
	def __init__(self):
		# Instantiate a JeVois Timer to measure our processing framerate:
		self.timer = jevois.Timer("processing timer", 100, jevois.LOG_INFO)
		
		self.draw = True
				
	# ###################################################################################################
	## Process function with USB output
	def process(self, inframe, outframe):
		out = self.UniversalProcess(inframe)
		outframe.sendCv(out, 50)
		

	def processNoUSB(self, inframe):
		out = self.UniversalProcess(inframe)
		
		
	def sortContours(self, cntArray):
		arraySize = len(cntArray)
		if arraySize == 0:
			return []
		
		sortedArray = [cntArray[0]]
		for i in range(1, arraySize):
			rectangle = cv2.minAreaRect(cntArray[i])
			
			for j in range(len(sortedArray)):
				sortedRect = cv2.minAreaRect(sortedArray[j])
				if rectangle[0][0] <= sortedRect[0][0]:
					sortedArray.insert(j, cntArray[i])
					break
				if j == (len(sortedArray) - 1):
					sortedArray.append(cntArray[i])
		
		
		return sortedArray
	
	def isTape(self, contour, hsv, draw = False):
		foundTape = False
		left = False
		right = False
		angle = -45
		cntArea = cv2.contourArea(contour)
		if cntArea > 25 and cntArea < 2500:
			left, angle = self.isLeft(contour, hsv, draw)
			if not left:
				right, angle = self.isRight(contour, hsv, draw)
			foundTape = left or right
		
		return foundTape, left, right, angle
		
	def isAngle(self, contour, hsv, minAngle, maxAngle, goodColor, draw):
		rectangle = cv2.minAreaRect(contour)
		box = cv2.boxPoints(rectangle)
		boxInts = np.int0(box)

		rows,cols = hsv.shape[:2]
		[vx,vy,x,y] = cv2.fitLine(contour, cv2.DIST_L2,0,0.01,0.01)
		leftY = int((-x*vy/vx) + y)
		rightY = int(((cols-x)*vy/vx)+y)
		
		#jevois.sendSerial("vx:" + str(vx) + " vy" + str(vy) + " x" + str(x) + " y:" + str(y))
		
		absY = math.fabs(vy)
		absX = math.fabs(vx)
		
		angle = math.atan(vy/vx) * 180 / 3.1415
		
		inBounds = False
		color = (0, 0, 255)

		if angle >= minAngle and angle <= maxAngle:
			color = goodColor
			inBounds = True
		if (draw):	
			try:
				cv2.line(hsv,(cols-1,rightY),(0,leftY),color,2)
			except:
				jevois.sendSerial("LeftY: " + str(leftY))
				jevois.sendSerial("RightY: " + str(rightY))
			
		return inBounds, angle
		
	def isLeft(self, contour, hsv, draw = False):
		return self.isAngle(contour, hsv, -90, -45, (85, 255, 255), draw)

	def isRight(self, contour, hsv, draw = False):
		return self.isAngle(contour, hsv, 45, 90, (170, 255, 255), draw)

	def UniversalProcess(self, inframe):
		#jevois.sendSerial("Hello World")
		#jevois.sendSerial("Hello World")
		runcount = 0
		#get image
		inimg = inframe.getCvBGR()
		outimg = inimg
		
		
		#change to hsv
		hsv = cv2.cvtColor(inimg, cv2.COLOR_BGR2HSV)
		
		oKernel = np.ones((5, 5), np.uint8)
		#cKernel = np.ones((5, 5), np.uint8)
		cKernel = np.array([
		[0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0],
		[0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0],
		[0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
		[0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0],
		[0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0]], dtype = np.uint8)
		
		
		#threshold colors to detect - Green: First value decides color, second val determines intensity, third val decides brightness
		lowerThreshold = np.array([65, 150, 150])
		upperThreshold = np.array([105, 255, 255])
		
		#check if color in range
		mask = cv2.inRange(hsv, lowerThreshold, upperThreshold)
		
		#create blur on image to reduce noise
		blur = cv2.GaussianBlur(mask,(5,5),0)
		
		ret,thresh = cv2.threshold(blur,180,255,0)
		
		#closes of noise from inside object
		closing = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, cKernel)
		
		#takes away noise from outside object
		opening = cv2.morphologyEx(closing, cv2.MORPH_OPEN, cKernel)
		
		#find contours
		
		contours, _ = cv2.findContours(opening, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)
		
		cntArray = []
		yesNo = " "
		pairsList = []
		
		#sift through contours and add 4 sided contours to cntArray
		for contour in contours:
			peri = cv2.arcLength(contour, True)
			approx = cv2.approxPolyDP(contour, 0.04 * peri, True)
			cntArea = cv2.contourArea(contour)
			rotatedRect = cv2.minAreaRect(contour)
			box = cv2.boxPoints(rotatedRect)
			boxArray = np.int0(box)
			boxColor = (0,0,255)
			cv2.drawContours(hsv,[boxArray],0,boxColor,2)

			tape, _, _, angle = self.isTape(contour, hsv, True)
			jevois.sendSerial("Angle: " + str(angle))
			if len(approx) == 4 and tape:
				cntArray.append(contour)
		
		#jevois.sendSerial("")

		sortedArray = self.sortContours(cntArray)
		
		outimg = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
		#outimg = cv2.cvtColor(mask, cv2.COLOR_GRAY2BGR)

		if len(sortedArray) < 2:
			jevois.sendSerial('{"Distance":-11, "Angle":-100}')
			return outimg
			
		isFirstRight, _ = self.isRight(sortedArray[0], hsv)
		isLastLeft, _ = self.isLeft(sortedArray[len(sortedArray) - 1], hsv)

		if isFirstRight:
			sortedArray.pop(0)
		if isLastLeft:
			sortedArray.pop(len(sortedArray) - 1)   
		
		if len(sortedArray) < 2:
			return outimg
		
		boxColor = (0,255, 255)
		
		xArray = []
		
		for index in range(0, len(sortedArray), 2):
			if ((index + 1) >= len(sortedArray)):
				break;
			firstLeft, _ = self.isLeft(sortedArray[index], hsv)
			secondRight, _ = self.isRight(sortedArray[index + 1], hsv)
			if ((not firstLeft) or (not secondRight)):
				break;
			leftRect = cv2.minAreaRect(sortedArray[index])
			rightRect = cv2.minAreaRect(sortedArray[index + 1])
			points_A = cv2.boxPoints(leftRect)
			points_1 = np.int0(points_A)
			points_B = cv2.boxPoints(rightRect)
			points_2 = np.int0(points_B)
			leftX = leftRect[0][0]
			rightX = rightRect[0][0]
			cv2.drawContours(hsv, [points_1], 0, boxColor, 2)
			cv2.drawContours(hsv, [points_2], 0, boxColor, 2)
			centerX = (leftX + rightX)/2
			centerX = math.fabs(centerX - 160)
			xArray.append(centerX)
		
		minX = np.argmin(xArray)
		
		#jevois.sendSerial("Hello")
		
		#tracker = cv2.TrackerMil_Create()
		#bbox = (287, 23, 86, 320)
		
		
		centerPairLeft = sortedArray[minX * 2]
		centerPairRight = sortedArray[(minX * 2) + 1]
		
		rightRect = cv2.minAreaRect(centerPairRight)
		leftRect = cv2.minAreaRect(centerPairLeft)
		leftPoints = cv2.boxPoints(leftRect)
		rightPoints = cv2.boxPoints(rightRect)
		leftPoints_1 = np.int0(leftPoints)
		rightPoints_1 = np.int0(rightPoints)
		
		boxColor = (240, 255, 255)
		
		cv2.drawContours(hsv, [leftPoints_1], 0, boxColor, 2)
		cv2.drawContours(hsv, [rightPoints_1], 0, boxColor, 2)
		
		leftY = leftRect[0][1]
		rightY = rightRect[0][1]
		leftX = leftRect[0][0]
		rightX = rightRect[0][0]
		
		
		centerY = (leftY + rightY)/2
		centerX = (leftX + rightX)/2
		yawAngle = (centerX - 159.5) * 0.203125
		distance = -0.0002102406* centerY **3 + 0.1209320294 * centerY ** 2 -23.58615878 * centerY + 1578.801145
		# Old Formula
		#distance = -(0.0004445927112 * (centerY **3)) + (0.1857477797 * (centerY ** 2)) - (26.22856387 * centerY) + 1297.652952
		
		
		realWorldPointY = (centerX - 159.5)/251
		realWorldPointY = realWorldPointY * distance
		
		
		#change vals to string to send over serial
		centerY = str(centerY)
		distance = str(distance)
		yawAngle = str(yawAngle)
		leftY = str(leftY)
		rightY = str(rightY)
		realWorldPointY = str(realWorldPointY)
		centerX = str(centerX)
		
		
		JSON = '{"Distance":' + distance + ', "Angle":' + yawAngle + '}'
		
		
		#send vals over serial
		jevois.sendSerial(JSON)
		#jevois.sendSerial("Hello World")
		outimg = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)		
		#opening2 = cv2.cvtColor(opening, cv2.COLOR_GRAY2BGR)
		#outimg = opening2
		return outimg