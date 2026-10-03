# SolarCloud Widget companion

This Android companion adds a home-screen widget for SolarCloud (`com.solarcloud`).

## What it does
- Displays PV, battery %, battery kW, Smart Load, Backup Load and Grid.
- Tapping the widget title opens SolarCloud.
- An Android AccessibilityService reads the text currently visible in SolarCloud Overview and updates the widget.

## Important limitation
This build does **not** call a private SolarCloud cloud API. It uses Android Accessibility to read values visible in the SolarCloud app. Therefore SolarCloud must be opened (and its Overview screen visible) for fresh values to be captured. This avoids asking for or storing your SolarCloud password.

## Install/build
Open this folder in Android Studio (Ladybug or newer), let Gradle sync, then Build > Build APK(s). The generated debug APK will be under `app/build/outputs/apk/debug/`.

After installing:
1. Open SolarCloud Widget.
2. Tap **Enable SolarCloud access**.
3. Enable **SolarCloud Widget** in Android Accessibility settings.
4. Add the **SolarCloud Widget** to the Samsung home screen.
5. Open SolarCloud and leave it on Overview for the first sync.

The service is limited to package `com.solarcloud` and only reads text from that app.


## Widget design
The widget layout has been updated to visually match the SolarCloud Overview screen: PV, Grid, inverter, Smart Load, Battery and Backup Load nodes.

Note: live values depend on Android Accessibility reading the SolarCloud screen; the widget cannot obtain private cloud data without a supported API.
