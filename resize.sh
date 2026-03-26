#!/bin/bash
IMG="Wave-icon.icon/Assets/Gemini_Generated_Image_vkelq1vkelq1vkel.png"
if [ -f "$IMG" ]; then
  mkdir -p app/src/main/res/mipmap-mdpi
  mkdir -p app/src/main/res/mipmap-hdpi
  mkdir -p app/src/main/res/mipmap-xhdpi
  mkdir -p app/src/main/res/mipmap-xxhdpi
  mkdir -p app/src/main/res/mipmap-xxxhdpi

  sips -z 48 48 "$IMG" --out app/src/main/res/mipmap-mdpi/ic_launcher.png
  sips -z 72 72 "$IMG" --out app/src/main/res/mipmap-hdpi/ic_launcher.png
  sips -z 96 96 "$IMG" --out app/src/main/res/mipmap-xhdpi/ic_launcher.png
  sips -z 144 144 "$IMG" --out app/src/main/res/mipmap-xxhdpi/ic_launcher.png
  sips -z 192 192 "$IMG" --out app/src/main/res/mipmap-xxxhdpi/ic_launcher.png

  sips -z 48 48 "$IMG" --out app/src/main/res/mipmap-mdpi/ic_launcher_round.png
  sips -z 72 72 "$IMG" --out app/src/main/res/mipmap-hdpi/ic_launcher_round.png
  sips -z 96 96 "$IMG" --out app/src/main/res/mipmap-xhdpi/ic_launcher_round.png
  sips -z 144 144 "$IMG" --out app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png
  sips -z 192 192 "$IMG" --out app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png
  
  rm -f app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml
  rm -f app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml
  echo "Success"
fi
