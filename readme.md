[![Build Status](https://ci.cloudnetservice.eu/buildStatus/icon?job=CloudNetService/v2-cloudflaresystem/master)](https://ci.cloudnetservice.eu/job/CloudNetService/job/v2-cloudflaresystem/master)
[![star this repo](http://githubbadges.com/star.svg?user=CloudNetService&repo=v2-cloudflaresystem)](https://github.com/CloudNetService/v2-cloudflaresystem)
[![fork this repo](http://githubbadges.com/fork.svg?user=CloudNetService&repo=v2-cloudflaresystem)](https://github.com/CloudNetService/v2-cloudflaresystem/fork)
[![GitHub license](https://img.shields.io/github/license/CloudNetService/v2-cloudflaresystem.svg)](https://github.com/CloudNetService/v2-cloudflaresystem/blob/master/LICENSE)

[![DepShield Badge](https://depshield.sonatype.org/badges/CloudNetService/v2-cloudflaresystem/depshield.svg)](https://depshield.github.io)
[![GitHub issues](https://img.shields.io/github/issues/CloudNetService/v2-cloudflaresystem.svg)](https://github.com/CloudNetService/v2-cloudflaresystem/issues)
[![GitHub contributors](https://img.shields.io/github/contributors/CloudNetService/v2-cloudflaresystem.svg)](https://github.com/CloudNetService/v2-cloudflaresystem/graphs/contributors)
[![Github All Releases](https://img.shields.io/github/downloads/CloudNetService/v2-cloudflaresystem/total.svg)](https://github.com/CloudNetService/v2-cloudflaresystem/releases)
[![GitHub release](https://img.shields.io/github/release/CloudNetService/v2-cloudflaresystem.svg)](https://github.com/CloudNetService/v2-cloudflaresystem/releases)


# Clouflare System | The Cloud Network Environment Technology 2
![Image of CloudNet](https://cdn.discordapp.com/attachments/325383142464552972/354670548292206594/CloudNet.png)

This is the Clouflare integration for CloudNet 2.2.0
 
---
 ### Support
 
 #### Minecraft-Support
 | Minecraft-Server-Version | 1.8.X | 1.9.X | 1.10.X | 1.11.X | 1.12.X | 1.13.X | 1.14.X | 1.15.X |
 |----------------|-------|-------|--------|--------|--------|--------|--------|--------|
 | [Spigot](https://www.spigotmc.org/wiki/about-spigot/) | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
 | [PaperSpigot](https://github.com/PaperMC/Paper) | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
 | [Hose(Not Tested)](https://github.com/softpak/HOSE) | :interrobang: | :interrobang: | :interrobang: | :x: | :x: | :x: | :x: | :x: |
 | [Akarin(Not Tested)](https://github.com/Akarin-project/Akarin) | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :x: |
 | [Glowstone(Not Tested)](https://www.glowstone.net/) | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: |


 #### Proxy-Support
 | Proxy-Version(Latest Only) | 1.8.X | 1.9.X | 1.10.X | 1.11.X | 1.12.X | 1.13.X | 1.14.X | 1.15.X |
 |----------------|-------|-------|--------|--------|--------|--------|--------|--------|
 | [BungeeCord](https://github.com/SpigotMC/BungeeCord) | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
 | [Waterfall](https://github.com/PaperMC/Waterfall) | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
 | [Travertine](https://github.com/PaperMC/Travertine) | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
 | [Hexacord](https://github.com/HexagonMC/BungeeCord) | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: | :heavy_check_mark: |
 | [FlameCord(Not Tested)](https://www.mc-market.org/resources/13492/) | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: | :interrobang: |
 
 #### CloudNet-Support
 | CloudNet-Support | Supported | 
 |------------------|-----------|
 | 2.1.17 below | :x: |
 | 2.2 above| :heavy_check_mark: |
 | Complete generation 3 | :x: |
  
___
    
### Discord
 *  [Discord Invite](https://discord.gg/CPCWr7w)
 
---
### Developer
If you would like to contribute to this repository, feel free to fork the repo and then create a pull request to our current dev branch. 
 
##### Maven:
```xml
<repositories>
    <repository>
        <id>cloudnet-repo</id>
        <url>https://repo.cloudnetservice.eu/repository/snapshots</url>
    </repository>
</repositories>

<dependencies>
    <!-- RootProject -->
    <dependency>
        <groupId>eu.cloudnetservice</groupId>
        <artifactId>CloudflareSystem</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

##### Gradle:
```groovy
repositories {
    maven {
        url "https://repo.cloudnetservice.eu/repository/snapshots"
    }
}
dependencies {
    compileOnly group: 'eu.cloudnetservice', name: 'CloudflareSystem', version: '1.0.0-SNAPSHOT'
}
```
