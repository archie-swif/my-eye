my-eye
======

My eye!
This is an java app for tracking what's going on on the ip camera.

What it does is getting an jpg image from the camera by http, 
applying some motion detection and saving the image if any motion is found.

<img src="https://github.com/archie-swif/my-eye/blob/development/doc/dog1.gif?raw=true" width="200"/> <img src="https://github.com/archie-swif/my-eye/blob/development/doc/diff_greyscale.gif?raw=true" width="200"/> <img src="https://github.com/archie-swif/my-eye/blob/development/doc/diff_bin2.gif?raw=true" width="200"/> <img src="https://github.com/archie-swif/my-eye/blob/development/doc/diff_bin.gif?raw=true" width="200"/>

First, it's a sequence of grayscale images, with 0..255 brightness for each pixel.

Then, it's a "diff image", when you subtract two subsequent images, pixel by pixel. Now you can see motion!

Bot how much "motion" is this? We can sum brightness of all pixels and get a value. It works too well, now you can detect changes in shadows and lighting, even when someone walks outside of the image.

To make it less sensitive, we can apply binarization. If pixel brightness is bigger then threshold, make it's brightness 255. Otherwise, make it 0. You can see how binarization with different levels of threshold looks like, and which values work better.

Finally, a diff image with binarization, we filtered out some noise, and "motion" is more visible. So you (and your fellow ai) can tell for sure that dog is moving!
