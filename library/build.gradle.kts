plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.cherry.lib.luban"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // 协程支持
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    // liveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    //图片解码
    implementation ("androidx.exifinterface:exifinterface:1.3.2")
    //进程级别
    implementation ("androidx.lifecycle:lifecycle-process:2.2.0")
    implementation ("androidx.fragment:fragment-ktx:1.2.5")
    compileOnly ("com.github.bumptech.glide:glide:4.11.0@aar")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.Victor2018" //groupId 随便取
                artifactId = "cherry"  //artifactId 随便取
                version = "1.0.0"
            }
        }
    }
}