import os

old_pkg = "com.example.wave"
new_pkg = "com.theproductarchitect.wave"
old_dir = "app/src/main/java/com/example/wave"
new_dir = "app/src/main/java/com/theproductarchitect/wave"

# 1. Move directories
os.makedirs("app/src/main/java/com/theproductarchitect", exist_ok=True)
if os.path.exists(old_dir):
    os.rename(old_dir, new_dir)

# 2. Update all files in the new directory
for root, dirs, files in os.walk(new_dir):
    for f in files:
        if f.endswith(".kt") or f.endswith(".java") or f.endswith(".xml"):
            filepath = os.path.join(root, f)
            with open(filepath, 'r') as file:
                content = file.read()
            
            content = content.replace(old_pkg, new_pkg)
            
            with open(filepath, 'w') as file:
                file.write(content)

# 3. Update build.gradle.kts
gradle_path = "app/build.gradle.kts"
with open(gradle_path, 'r') as file:
    content = file.read()
content = content.replace(old_pkg, new_pkg)
with open(gradle_path, 'w') as file:
    file.write(content)

print("Package renamed successfully!")
