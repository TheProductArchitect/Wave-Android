with open('app/src/main/java/com/example/wave/ui/screens/SecureScanScreen.kt', 'r') as f:
    text = f.read()

import re
old_text = r'val bodyText = errorMessage \?: payload \?: "Tag programmed successfully!"'
new_text = r'''val bodyText = if (isSuccessWrite && errorMessage == null) {
                if (payload != null) "Tag programmed successfully!\n\nHere's what is written on to the NFC tag:\n" + payload else "Tag programmed successfully!\n\nHere's what is written on to the NFC tag."
            } else {
                errorMessage ?: payload ?: "Tag programmed successfully!"
            }'''

text = re.sub(old_text, new_text, text)

with open('app/src/main/java/com/example/wave/ui/screens/SecureScanScreen.kt', 'w') as f:
    f.write(text)
