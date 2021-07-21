# Jaw v1.0.1
### Introduction
Jaw is a java library, created in attempt to make a better library for all JVM developers. 
In order to remove unnecessary additional dependencies like 'kotlin stdlib' in the development environment, and java-incompatible features like reified generics, I decided to translate the 'dolphin2410/kolleague' project to java, while also fixing issues and adding new features.

### Notice
- I have finally finished the first version of this project
- ***Find the easter egg!***
- For more information, check out the CHANGELOG.md file!

### Usage
***This project is published in mavenCentral()***
To use only the core features, like Reflection, Collection and Utility functions, use this.

```kotlin
implementation("io.github.teamcheeze:jaw:1.0.1:core")
```
If the core feature is already provided during runtime, and you want to use only the database functions,

```kotlin
implementation("io.github.teamcheeze:jaw:1.0.1:database")
```
Otherwise, to use them all, either of the following will work.

```kotlin
implementation("io.github.teamcheeze:jaw:1.0.1:sources")
implementation("io.github.teamcheeze:jaw:1.0.1:jaw-all")
implementation("io.github.teamcheeze:jaw:1.0.1")
```

### Contributing
If you have any ideas for new features, create a pull request. I need your help guys!

### License
The MIT license allows you to do anything if you just add a copyright notice.

### Dependencies
- Gson: The Gson library, made by Google, is used to parse json data from the library's built-in rest api client. ***Must*** be provided during runtime.
- FirebaseAdminSDK: The SDK that accesses the firebase database made by Google. You must provide this in runtime to use the database feature
- Sqlite: A sqlite library made by Taro L. Saito, in the Xerial project.
- JetbrainsAnnotations: For simple Nullable and NotNull annotations.
