// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradleVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath(ClassPathPlugins.hilt)
        classpath(ClassPathPlugins.oss)
        classpath("com.google.gms:google-services:${Versions.googleServiceVersion}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsVersion}")
    }

    allprojects {
        repositories {
            maven { setUrl("https://devrepo.kakao.com/nexus/content/groups/public/") }
            google()
            mavenCentral()
        }
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
