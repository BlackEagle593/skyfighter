# Skyfighter [![Build Status](https://travis-ci.com/BlackEagleEF/Skyfighter.svg?branch=master)](https://travis-ci.com/BlackEagleEF/Skyfighter)

Skyfighter is a minigame plugin for a minecraft server where you flight around with an elytra and shoot other players.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development.

### Installing and Running

1. Clone the git repository to your local machine.
    ```
    git clone https://github.com/BlackEagleEF/Skyfighter.git
    ```

2. Open the directory with the IDE of your choice as a gradle project.
3. Open terminal in the directory and build the gradle project
    ```
    gradlew build
    ```
    To build a jar with all dependencies execute the shadow task
    ```
   gradlew shadow
   ```
   You can find the plugin in ``/build/libs/``
4. Build and run the dockerfile for an image with a paperspigot server and the plugin
    ```
    docker build . -t paper/skyfighter
    docker run -p 25565:25565 paper/skyfighter
    ```

## Built With

- [Gradle](https://gradle.org/) - Build Management Tool
- [Paper 1.14.4](https://papermc.io) - Minecraft Server
- [Docker](http://docker.io) - Container Virtualization
