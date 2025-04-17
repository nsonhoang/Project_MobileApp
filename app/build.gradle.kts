plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt") apply false
    id("com.google.devtools.ksp") // Thêm plugin KSP
}

android {
    namespace = "com.example.projecttest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projecttest"
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
        viewBinding = true
    }
}

dependencies {

    // Firebase BoM để quản lý các phiên bản
    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))

    // Các thư viện Firebase
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-analytics")

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Facebook login
    implementation("com.facebook.android:facebook-login:latest.release")
    implementation("com.facebook.android:facebook-android-sdk:[4,5)")

    // Các thư viện AndroidX và UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)

    // Thư viện tải ảnh
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(libs.androidx.webkit)
    implementation(libs.firebase.messaging)

    // Thư viện cho việc kiểm tra
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Room Database
    implementation("androidx.room:room-runtime:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")

//    kapt("androidx.room:room-compiler:2.5.0")

    // Thư viện Lifecycle cho ViewModel và LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")


    // Room KTX giúp sử dụng Room dễ dàng hơn với Kotlin
    implementation("androidx.room:room-ktx:2.5.0")
}
