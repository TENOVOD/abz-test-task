# 🚀 Test Assignment for Abz Company

This is a completed test assignment for the company **Abz**.

---

## 🛠 Configuration Options and Customizations

1. **Kotlin Version**: `2.0.21` 📌
2. **Splash Screen Updated** 🎨
3. **Application Icon Replaced** 🆕
4. **Nunito Sans Font Added** ✨
5. **Permissions Added to Manifest** 📄:
   - 📷 **Camera Permission** (`CAMERA`)
   - 📂 **Read External Storage** (`READ_EXTERNAL_STORAGE`)
   - 💾 **Write External Storage** (`WRITE_EXTERNAL_STORAGE`)
   - 🌐 **Internet Permission** (`INTERNET`)
   - 📶 **Access Network State** (`ACCESS_NETWORK_STATE`)
6. **Content Provider Authorities and File Path Added** 🗂️:
   - For storing images in the content provider.

---

## 📚 Dependencies and External Libraries Used

1. **Hilt** 🛠️
   - A library for convenient dependency injection.
   - Lifecycle-aware, helping to avoid memory leaks.
   - Allows injecting dependencies directly into `ViewModel`.
   - Provides a convenient network dependency for background monitoring of network connections.
2. **Ktor** 🌐
   - Used for building client-server architecture.
   - Fully written in Kotlin with built-in support for coroutines.
   - Cross-platform and doesn't rely on additional libraries unlike Retrofit.
3. **Coil** 🖼️
   - Library used for asynchronous image loading.
4. **Accompanist Permissions** 🔐
   - Used for convenient handling of permissions.
5. **Kotlinx Serialization Json** 📋
   - Used for serializing data from JSON into Kotlin classes.
6. **Navigation Compose** 🚦
   - Used for navigation and transition animations between screens.

---

## 🐞 Troubleshooting Tips and Common Issues

- **Internet Connection Required** 🌐
   - For the intended operation of the application, an internet connection is required.
   - If the **"There is no internet connection"** screen appears, please ensure you are connected to the internet and press the **"Try Again"** button.

---

Feel free to explore the project and provide any feedback!
