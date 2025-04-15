plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.kotlin.serializer.plugin)
}

android {
    namespace = "com.tonyxlab.scribbledash"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tonyxlab.scribbledash"
        minSdk = 24
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
dependencies {

    implementation(AndroidX.core.ktx)
    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.activity.compose)
    implementation(platform(AndroidX.compose.bom))
    implementation(AndroidX.compose.ui)
    implementation(AndroidX.compose.ui.graphics)
    implementation(AndroidX.compose.ui.toolingPreview)

    // Material 3
    implementation(AndroidX.compose.material3)

    // Material Extended Icons
    implementation(AndroidX.compose.material.icons.extended)

    // Splash Screen
    implementation(AndroidX.core.splashscreen)

    // Room
    implementation(AndroidX.room.ktx)
    ksp(AndroidX.room.compiler)

    // Data Store
    implementation(AndroidX.dataStore.preferences)

    // Kotlinx Serialization
    implementation(KotlinX.serialization.json)

    // Compose Navigation
    implementation(AndroidX.navigation.compose)

    // Koin
    implementation(Koin.android)
    implementation(Koin.compose)

    // Accompanist Permissions
    implementation(Google.accompanist.permissions)

    // Logging
    implementation(JakeWharton.timber)

    // Testing
    testImplementation(libs.junit)

    // Android Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
