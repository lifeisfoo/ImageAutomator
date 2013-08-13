## Image Automator
Resize and (optionally) watermark your photos.
By now is only a nice wrapper around [thumbnailator](https://code.google.com/p/thumbnailator/).

### Usage
```sh
ImageAutomator IMGP4311.jpg
```
This command will create ``thumbnail.IMGP4311.jpg`` in the same directory of the fullsize photo.

### Advanced usage
```sh
usage: ImageAutomator [options] <inputImage>
 -o <arg>    output file name (e.g. thumb.jpg), default
             thumbnail.filename.ext
 -q <arg>    jpeg quality (e.g. 0.9, max 1.0), default 0.97
 -s <arg>    output max side length in px (e.g. 800), default 1200
 -w <arg>    watermark image file
 -wp <arg>   watermark position (e.g. 0.9, max 1.0), default BOTTOM_RIGHT
 -wt <arg>   watermark transparency (e.g. 0.5, max 1.0), default 1.0
```
E.g.
```sh
ImageAutomator -q 1.0 -w watermark.png -wt 0.5 -o resized.jpg IMGP4492.jpg
```
This command will create ```resized.jpg``` in the current directory, with a watermark in the bottom right corner.
The watermark will be 50% opaque.

Accepted values for the watermark position are:
* TOP_LEFT
* TOP_CENTER
* TOP_RIGHT
* CENTER_LEFT
* CENTER
* CENTER_RIGHT
* BOTTOM_LEFT
* BOTTOM_CENTER
* BOTTOM_RIGHT

### Planned features
* drag and drop support
* upload to flickr

### Author and License
Alessandro Miliucci, GPL v3