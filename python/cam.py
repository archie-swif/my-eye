import time
import picamera

with picamera.PiCamera() as camera:
#    camera.resolution = (1280, 1024)
    camera.led = False
    camera.resolution = (2592, 1944)
    camera.color_effects= (128,128)
    camera.start_preview()
    time.sleep(2)
    for filename in camera.capture_continuous('images/{timestamp:%H-%M-%S}-{counter:d}.jpg',use_video_port=True,resize=(1280, 1024),quality=15,thumbnail=None):
        print('Captured %s' % filename)
	time.sleep(0.2)
       
