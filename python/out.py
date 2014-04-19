import time
import picamera
import io

with picamera.PiCamera() as camera:
    camera.led = False
    camera.resolution = (2592, 1944)
    camera.color_effects= (128,128)
#    camera.start_preview()
#    time.sleep(2)
    my_stream = io.BytesIO()
    camera.capture(my_stream,format='jpeg',use_video_port=True,resize=(1280, 1024),quality=15,thumbnail=None)
    print(my_stream.getvalue())


       
