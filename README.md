<div align="center">

# Modulo
[![Build App](https://github.com/ChillyCheesy/Modulo/actions/workflows/app-build.yml/badge.svg?branch=master)]()
[![maven-central](https://maven-badges.herokuapp.com/maven-central/com.chillycheesy/modulo-api/badge.svg?style=flat)](https://search.maven.org/artifact/com.chillycheesy/modulo-api)
[![Release](https://img.shields.io/github/v/release/ChillyCheesy/modulo.svg)](https://github.com/ChillyCheesy/Modulo/releases)

</div>


<div align="center">

![License](https://img.shields.io/github/license/ChillyCheesy/Modulo.svg)
![Stars](https://img.shields.io/github/stars/ChillyCheesy/modulo.svg)
![Forks](https://img.shields.io/github/forks/chillycheesy/modulo.svg)

</div>

---

## Table of contents
* [Description](#Description)
* [Getting started](#GettingStarted)
  * [Step 1](#GettingStarted-1)
  * [Step 2](#GettingStarted-2)
  * [Step 3](#GettingStarted-3)
  * [Step 4](#GettingStarted-4)
  * [Step 5](#GettingStarted-5)
* [How to use it](#HowToUseIt)
* [See also](#SeeAlso)
* [Cute cats](https://www.youtube.com/watch?v=VZrDxD0Za9I)

## Description 🐭 <a id="Description"></a>
What is modulo...
## Getting started 🚀 <a id="GettingStarted"></a>
**How to use Modulo ?**
### Step 1:<a id="GettingStarted-1"></a>
* Create a new java project in your favorite IDE.  
* Add the modulo-api dependency to your project.
  * Manually with your IDE.
  * With Maven add the following dependency to your pom.xml:
    ```xml
    <dependency>
      <groupId>com.chillycheesy</groupId>
      <artifactId>modulo-api</artifactId>
      <version>0.1.2</version>
    </dependency>
    ```
  * With Gradle add the following dependency to your build.gradle: *(recommended)*
    ```gradle
    dependencies {
      implementation 'com.chillycheesy:modulo-api:0.1.2'
    }
    ```
### Step 2:<a id="GettingStarted-2"></a>
* Create a new java class in your project. The class must inherit from the Module class. This will become your main class.
* Implement the needed methods.
  ```java
  package com.chillycheesy.modulo;
  
  import com.chillycheesy.modulo.modules.Module;
  
  public class HelloModule extends Module {
      
      @Override
      protected void onLoad() {
          ModuloAPI.getLogger().info(this, "HelloModule is loaded");
      }
  
      @Override
      protected void onStart() {
          ModuloAPI.getLogger().info(this, "HelloModule is started");
      }
      
      @Override
      protected void onStop() {
          ModuloAPI.getLogger().info(this, "HelloModule is stopped");
      }
  
  }
  ```
### Step 3:<a id="GettingStarted-3"></a>
* Create the module.yml file in your project resources folder.
  * The module.yml file must contain the following information:
    ```yml
    name: HelloModule
    description: This is a hello module
    version: 1.0.0
    authors:
      - ChillyCheesy
    dependencies: [] # Optional
    softDependencies: [] # Optional
    ```
### Step 4:<a id="GettingStarted-4"></a>
* Download the [modulo-server jar file](https://github.com/ChillyCheesy/Modulo/releases).
* Build your project and drop it in the ```modules``` folder inside your server folder. 
* run the server with the following command:
  ```bash
  $> java -jar modulo-server.jar
  ```

### Step 5:<a id="GettingStarted-5"></a>
**Enjoy 🌶 🧀 !**

*If you use gradle, you can use the [ModuloGradleApplication](https://github.com/ChillyCheesy/ModuloGradleApplication).*

## How to use it 📕 <a id="HowToUseIt"></a>
See the documentation page [here](https://chillycheesy.github.io/Modulo/).  
You can also check the [wiki](https://github.com/ChillyCheesy/Modulo/wiki).

## See also 🌶 🧀 <a id="SeeAlso"></a>
* [ModuloGradleApplication](https://github.com/ChillyCheesy/ModuloGradleApplication)

<pre>
              ..----.._    _
            .' .--.    "-.(O)_
'-.__.-'"'=:|  ,  _)_ \__ . c\'-..
            ''------'---''---'-"
            Despelette was here !
</pre>