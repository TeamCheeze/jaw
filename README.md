# Jaw
### Introduction
Jaw is a java library, created in attempt to make a better library for all JVM developers. 
In order to remove unnecessary additional dependencies like 'kotlin stdlib' in the development environment, I decided to translate the 'kolleague' project to java, while also fixing issues and adding new features.
This library is currently under development. The first beta release is going to be released in the end of July.


### Notice
- This library is still in construction.
- ***Find the easter egg!***

### Contributing
If you have any ideas for new features, create a pull request. I need your help guys!

### License
The MIT license allows you to do anything if you just add a copyright notice.

### Dependencies
- Gson: The Gson library, made by Google, is used to parse json data from the library's built-in rest api client.
- FirebaseAdminSDK: The SDK that accesses the firebase database made by Google. \[ Not provided in runtime \]
- Sqlite: A sqlite library made by Taro L. Saito, in the Xerial project.
- JetbrainsAnnotations: For simple Nullable and NotNull annotations.

### Development progress notes
- Translated, fixed bugs and upgraded the reflection feature of kollegaue
- Translated and added java support to asynchronous http request
- Translated and added java support to asynchronous action
- Created a SimpleIterator class to develop custom collection easily
- Added enum boolean
- Added kotlin's nothing
- Added Number parsing feature
- Added support for indexed list in java
- Added kotlin's IntRange feature
- Added some core kotlin features in KWrapper class
- Added OnlineFile class that allows you to handle online files
- Added Firebase database managing feature
- Added Local json database managing feature
- TODO: Add sqlite support in database
- TODO: Add mongodb support in database