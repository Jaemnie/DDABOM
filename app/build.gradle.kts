plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.team"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.team"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    // Gson
    implementation ("com.google.code.gson:gson:2.11.0")
    // OkHttp
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    // Okio
    implementation ("com.squareup.okio:okio:3.9.0")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    // Jsoup
    implementation ("org.jsoup:jsoup:1.17.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    // SQLite
    implementation ("androidx.sqlite:sqlite:2.1.0")
    // Custom CalendarView
    implementation("com.prolificinteractive:material-calendarview:1.4.3")
}

