# Web to Native - Android Interview Assignment

A modern Android application that demonstrates WebView integration, local storage management, and API connectivity. Built with Kotlin and Material Design 3 components.

## 📱 Features

1. Home Screen
- Image Slider: Auto-scrolling carousel with 3 images and dot indicators
- URL Input Field: Material Design text input with validation
- Navigation Drawer: Side menu for easy navigation
- URL Validation:
  - Empty input detection
  - Invalid URL format checking
  - Automatic `https://` prepending
  - Leading/trailing space trimming
- **Local Storage**: Automatically saves every opened URL with timestamp

2. WebView Screen
- Dynamic URL Loading: Opens URLs from Home Screen
- Top App Bar with:
  - Back button (retains URL in Home Screen)
  - Close button (clears URL in Home Screen)
- Live URL Display: Updates as user navigates between pages
- Progress Bar: Shows page loading progress
- WebView Navigation: Back button navigates through browsing history
- Full JavaScript Support: Enables interactive web content

3. History Screen
- RecyclerView List: Displays all browsing history with timestamps
- Empty State: Friendly message when no history exists
- Click to Open: Tap any history item to open in WebView
- Clear All: Delete entire browsing history with confirmation
- Upload to API: Send history data to Beeceptor endpoint
- Auto-refresh: Updates history when returning to screen

## 🛠️ Technology Stack

- Language: Kotlin
- Min SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- **Architecture**: Activity-based with MVVM principles
- UI Framework: Material Design 3



🚀 Getting Started

Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or higher
- Android SDK 34
- Minimum device API 24+

📖 How to Use

Opening a Website
1. Launch the app
2. Enter a URL in the input field (e.g., `google.com` or `https://github.com`)
3. Tap "Open" button
4. Website loads in WebView

Navigation
- Back Button (WebView)**: Returns to Home Screen with URL retained
- Close Button (WebView)**: Returns to Home Screen with URL cleared
- System Back: Navigates through WebView history, then exits

Viewing History
1. Open navigation drawer (☰ icon)
2. Tap "History"
3. View all previously opened URLs with timestamps
4. Tap any item to reopen in WebView

Managing History
- Clear All: Tap "Clear All" → Confirm → History deleted
- Upload: Tap "Upload" → Sends data to Beeceptor API


⚠️ Known Issues & Limitations

1. HTTP Support: Some websites may not load if they enforce HTTPS
2. Download Handling: File downloads from WebView not implemented
3. History Limit: Maximum 50 entries stored locally
4. Offline Mode: Requires internet connection to load websites

🐛 Troubleshooting
 WebView not loading
- Check internet connection
- Verify URL format is correct
- Ensure INTERNET permission is granted

Upload fails
- Verify Beeceptor endpoint URL is correct
- Check internet connectivity
- Review API response in Logcat

🔄 Version History

- ✅ Home Screen with URL input
- ✅ WebView integration
- ✅ History management
- ✅ Local storage with SharedPreferences
- ✅ API upload functionality
- ✅ Material Design 3 UI

👨‍💻 Developer

Abhijeetk  
Android Developer Assignment for Orufy Technologies Pvt. Ltd.

This project is created as part of an interview assignment for Orufy Technologies Pvt. Ltd.

 🙏 Acknowledgments

- Material Design 3 Guidelines
- Android Developers Documentation
- Beeceptor for API testing
- ViewPager2 & DotsIndicator libraries

---

**Built with ❤️ using Kotlin & Material Design**
