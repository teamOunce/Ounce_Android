plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = Constants.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Constants.packageName
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk
        versionCode = Constants.versionCode
        versionName = Constants.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = project.properties["SIGNED_STORE_FILE"]?.let { file(it) }
            storePassword = project.properties["SIGNED_STORE_PASSWORD"].toString()
            keyAlias = project.properties["SIGNED_KEY_ALIAS"].toString()
            keyPassword = project.properties["SIGNED_KEY_PASSWORD"].toString()
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }

        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(KotlinDependencies.kotlin)

    // AndroidX
    implementation(AndroidXDependencies.coreKtx)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.constraintLayout)
    implementation(AndroidXDependencies.fragment)
    implementation(AndroidXDependencies.legacy)
    implementation(AndroidXDependencies.security)
    implementation(AndroidXDependencies.legacy)
    implementation(AndroidXDependencies.lifeCycleKtx)
    implementation(AndroidXDependencies.livedataLifeCycle)
    implementation(AndroidXDependencies.lifecycleJava8)

    // Material Design
    implementation(MaterialDesignDependencies.materialDesign)

    // Test Dependency
    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.androidTest)
    androidTestImplementation(TestDependencies.espresso)

    implementation("com.tbuonomo:dotsindicator:4.2")

    //Activity-KTX
    implementation("androidx.activity:activity-ktx:1.4.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}")

    // Kakao Login - Gson, Retrofit, OkHttp
    implementation(ThirdPartyDependencies.kakaoLogin)

    // TedKeyboardObserver
    implementation("io.github.ParkSangGwon:tedkeyboardobserver:1.0.1")

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:27.0.0"))

    // Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Firebase(Google) Auth
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.android.gms:play-services-auth:20.0.1")

    // Glide
    implementation(ThirdPartyDependencies.glide)
    kapt(KaptDependencies.glide)

    // Retrofit
    implementation(ThirdPartyDependencies.gson)
    implementation(platform(ThirdPartyDependencies.okHttpBom))
    implementation(ThirdPartyDependencies.okHttp)
    implementation(ThirdPartyDependencies.okHttpLoggingInterceptor)
    implementation(ThirdPartyDependencies.retrofit)
    implementation(ThirdPartyDependencies.retrofitGsonConverter)

    // Android Dagger-Hilt
    implementation(AndroidXDependencies.hilt)
    kapt(KaptDependencies.hiltCompiler)

    // Android Dagger-Hilt for ViewModelInject
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Lottie
    implementation("com.airbnb.android:lottie:4.2.1")

    // TedPermission
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")

    // TedImagePicker
    implementation("io.github.ParkSangGwon:tedimagepicker:1.2.7")

    // Firebase Crashlytics
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
}