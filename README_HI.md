# NOVA OS — पहला Android प्रोटोटाइप

यह **NOVA Launcher 1.0** है — पूरा operating system या custom ROM नहीं। यह मौजूदा Android के ऊपर Home app की तरह चलता है और फोन में पहले से इंस्टॉल launcher apps की सूची दिखाकर उन्हें खोल सकता है।

## फोन पर इसका क्या फायदा होगा?
- NOVA नाम और Black / Purple / Neon Blue थीम वाला Home screen
- इंस्टॉल किए हुए Android apps के icons और names
- ऐप पर टैप करने से वही ऐप खुलेगा
- इसे Android की Default Home app चुना जा सकता है

## APK कैसे बनाएं (सिर्फ फोन से)
सबसे आसान रास्ता GitHub Actions से cloud build करना है:

1. GitHub पर नया खाली repository बनाएं, नाम `nova-launcher` रखें।
2. इस ZIP को extract करें और इसके अंदर की सभी files repository में upload करें।
3. `Actions` tab खोलें। `Build NOVA Launcher APK` workflow चलाएं (या main branch में files push करें)।
4. Workflow पूरा होने पर नीचे `Artifacts` में `NOVA-Launcher-debug` डाउनलोड करें।
5. ZIP खोलकर `app-debug.apk` अपने फोन में डाउनलोड करें और इंस्टॉल करें।
6. Android पूछे तो install की अनुमति दें। फिर Home button दबाकर **NOVA Launcher** चुनें।

## जरूरी सीमाएं और सुरक्षा
- यह prototype Android launcher है; kernel, drivers, modem firmware या system partition नहीं बदलता।
- PhonePe, Google Pay और banking apps फोन के मौजूदा Android/Google Play Services पर ही चलते रहेंगे। Launcher बदलने से वे custom ROM नहीं बनते और न ही बैंकिंग compatibility खराब होनी चाहिए।
- Banking apps की सुरक्षा के लिए bootloader unlock/root/custom ROM न करें। भविष्य में ROM बनाने से पहले banking compatibility को अलग से जांचना होगा।
- यह शुरुआती build है; AI, notifications panel, app store और वास्तविक system settings integration अभी शामिल नहीं हैं।

## Build जानकारी
- Package: `com.nova.launcher`
- Minimum Android: 8.0 (API 26)
- Target SDK: 35
- Device target: Samsung Galaxy Note 9 SM-N960F सहित Android 8+ डिवाइस (अंतिम व्यवहार फोन/OS पर निर्भर)।
