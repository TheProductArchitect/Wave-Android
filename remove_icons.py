import os
import glob

files = glob.glob('app/src/main/res/mipmap-*/*.png')
for f in files:
    if 'ic_launcher' in f:
        os.remove(f)
