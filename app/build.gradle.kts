plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mealbox"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mealbox"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation ("com.sun.mail:android-mail:1.6.5")
    implementation ("com.sun.mail:android-activation:1.6.5")
    implementation("de.svenkubiak:jBCrypt:0.4.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
}