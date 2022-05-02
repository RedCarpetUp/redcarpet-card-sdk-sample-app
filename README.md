# Redcarpet SDK for Android

Redcarpet SDK  allows you to integrate Redcarept into your Android app. this repository demonstrates how to integrate Redcarpet Android SDK.

The apk built with code from this repository is published on the Play Store.Please download Redcarpet android app from play store https://play.google.com/store/apps/details?id=com.redcarpetup.rewardpay

Learn more about the provided samples, documentation, integrating the SDK into your app, accessing source code, and more at https://www.redcarpet.ai/sdk-documentation

FEATURES
--------
* [Customer Onboarding](https://www.redcarpet.ai/sdk-documentation)
* [Redcarpet Card Managemet](https://www.redcarpet.ai/sdk-documentation)
* [Money Management](https://www.redcarpet.ai/sdk-documentation)
* [App Bill/Emi Payment](https://www.redcarpet.ai/sdk-documentation)



INSTALLATION
------------
Facebook SDKs are published to Maven as independent modules. To utilize a feature listed above
include the appropriate dependency (or dependencies) listed below in your `app/build.gradle` file.
```gradle
dependencies {
 
 // Redcarpet Core
 
    implementation "com.redcpt:sdk:0.0.1"
    
 // Redcarpet Expense Manager 
 
    implementation "com.redcpt:sdk-expensemanager:0.0.1"
    implementation "com.redcpt:sdk-utilitiessdk:0.0.1"
    
 // Redcarpet Onboarding   
 
      implementation "com.redcpt:sdk-otp:0.0.1"
      implementation "com.redcpt:sdk-permissions:0.0.1"
      implementation "com.redcpt:sdk-verification:0.0.1"
    
//  Redcarpet Card Management 

    implementation "com.redcpt:sdk-services:0.0.1"
}
```

You may also need to add the following to your project/build.gradle file.
```gradle
buildscript {
    repositories {
        mavenCentral()
    }
}
```
