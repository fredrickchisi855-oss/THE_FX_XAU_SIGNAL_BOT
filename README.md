# THE_FX_XAU_BOT

Strictly XAUUSD Signal Bot for Android.

## Current build
- Jetpack Compose UI foundation
- Existing supplied logo used unchanged
- Professional black/gold splash screen
- XAUUSD dashboard foundation
- Market status area
- Upcoming signal area with start/end fields
- All planned timeframe buttons
- Indicators/news placeholders
- Smooth vertical scrolling and horizontal timeframe scrolling
- Bottom navigation foundation

## Important
Live Deriv market data and the production signal engine are intentionally not hard-coded into this first UI milestone. The next milestone will connect the data layer and replace demo/placeholder values with live, validated values.

Never commit Deriv API tokens, passwords, or private credentials to GitHub.

## Termux prerequisites
Use a supported JDK (AGP 9.4 requires JDK 17+; Android documentation lists JDK 17 for AGP 9.4). Install Android SDK command-line tools and the required SDK packages, then run:

```bash
cd THE_FX_XAU_BOT
./gradlew :app:assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## GitHub
```bash
git init
git add .
git commit -m "Initial THE_FX_XAU_BOT Android foundation"
git branch -M main
git remote add origin YOUR_GITHUB_REPOSITORY_URL
git push -u origin main
```

Copyright: THE_FX_XAU_TRADERS
Contact: +263 784 552 452
