plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt") apply false
    id("com.google.devtools.ksp") // Thêm plugin KSP
    id ("kotlin-parcelize")
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
    buildFeatures{
        viewBinding=true
    }
}

dependencies {
    implementation ("com.github.bumptech.glide:glide:4.13.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.2")

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


    // Thư viện cho việc kiểm tra
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    // Room Database
    implementation("androidx.room:room-runtime:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")

//    kapt("androidx.room:room-compiler:2.5.0")

    // Thư viện Lifecycle cho ViewModel và LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")


    // Room KTX giúp sử dụng Room dễ dàng hơn với Kotlin
    implementation("androidx.room:room-ktx:2.5.0")
    implementation ("com.google.firebase:firebase-auth:21.0.1")  // Firebase Authentication
    implementation ("com.google.firebase:firebase-database:20.0.3")  // Firebase Realtime Database
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation ("com.google.firebase:firebase-firestore-ktx")// Firebase FireStore

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0-alpha01") // Hoặc phiên bản mới nhất
    implementation(libs.firebase.analytics)

    implementation ("com.github.bumptech.glide:glide:4.16.0")

}
