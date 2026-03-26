import xml.etree.ElementTree as ET
from urllib.request import urlopen
import argparse

# In reality, converting a raw XML vector to PNG automatically without standard
# tools (like ImageMagick/librsvg) is hard on a plain mac, so to fix "blank" icons
# we explicitly force Android Studio to generate these fallback PNGs via gradle plugin properties
