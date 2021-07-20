## 1.0.2
***This version only has minor fixes***
### Added
- `JsonParserWrapper` covers the old `Gson`'s `JsonParser` which don't have a static `parseString` or `parseReader` method.
- When creating an empty json file if file doesn't exist on `LocalDatabase` initialize, it adds an empty json object (`{}`)
### Modified
- Jaw no longer shades `Gson`. It has to be provided separately during runtime.
### BugFix
- Fixed the bug - module `jar-all` was created instead of `jaw-all` due to a spelling mistake.
## 1.0.1
***This project has moved from dolphin2410 to TeamCheeze***
### Added
- Automatically create a database file if doesn't exist in `LocalDatabase`
### Removed
- Removed Dokka since this is a java project.
- Created submodules for developers who don't use the database feature
### Modified
- Updated `build.gradle.kts`
### Beta
*This version is beta now*
You can separately access the features now.
- Database only

`implementation("io.github.teamcheeze:jaw:1.0.1:database")`.
- Core features only

`implementation("io.github.teamcheeze:jaw:1.0.1:core")`.

- All features

`implementation("io.github.teamcheeze:jaw:1.0.1")`
or
`implementation("io.github.teamcheeze:jaw:1.0.1:jaw-all")`
## 1.0.0
### Added
- Translated, fixed bugs and upgraded the reflection feature of kolleague
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
