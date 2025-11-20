# 𝐅𝐥𝐨𝐰𝐬 𝐦𝐚𝐤𝐞 𝐭𝐡𝐢𝐧𝐠𝐬 𝐞𝐚𝐬𝐲 ⚡
Form validation with a maximum UX becomes much cleaner when driven by Kotlin Flows. Flows allow each input to reactively validate itself and update UI state in a predictable, scalable way, validation logic can be in the domain layer inside individual usecases. Below is a simple example code for an email + password sign-up form using Flow operators like 𝘥𝘪𝘴𝘵𝘪𝘯𝘤𝘵𝘜𝘯𝘵𝘪𝘭𝘊𝘩𝘢𝘯𝘨𝘦𝘥𝘉𝘺, 𝘮𝘢𝘱, and 𝘰𝘯𝘌𝘢𝘤𝘩 inside a ViewModel.

𝐖𝐡𝐲 𝐅𝐥𝐨𝐰𝐬?
* ✅ Each field becomes a self-contained reactive stream
* ✨ Validation logic stays out of the UI
* 🔄 UI state updates automatically as inputs change (Declarative)
* 🧱 Code remains in Clean Architecture.

# Animated Check ↔ Cross (Jetpack Compose)
````Kotlin
@Composable
AnimatedCheckCross(
    isChecked = isChecked,
    modifier = Modifier.size(48.dp)
)
````
https://github.com/user-attachments/assets/3ac76dfd-d86d-4c42-8652-ddab2ff9e5a3
