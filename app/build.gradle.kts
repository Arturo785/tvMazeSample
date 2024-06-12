plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.arturo.tvmazesample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.arturo.tvmazesample"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        android.buildFeatures.buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_URL","\"https://api.tvmaze.com/\"")
        }

        release {
            isMinifyEnabled = false
            buildConfigField("String", "API_URL", "\"https://api.tvmaze.com/\"")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /**
     * Since it's a simple app no need to have buildSrc module to control dependencies
     * In case it's needed it can be migrated
    * */
    //Gson
    implementation (libs.gson)

    // dagger
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)
    kapt (libs.androidx.hilt.compiler)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Glide
    implementation(libs.compose)

    // Compose lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}