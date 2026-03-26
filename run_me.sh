python3 -t << 'EOF'
import base64

content = c"..."

with open("app/src/main/java/com/example/wave/MainActivity.kt", "w") as f:
    f.write(content)
EOF
