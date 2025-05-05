# Commander

**Commander** is a Java command framework built for Bukkit/Spigot/Paper Minecraft plugins. It provides a clean, class-based system for registering commands with full tab-completion and parameter validation — all while being easy to use and extend.

---

## ✨ Features

- 🌲 **Hierarchical command trees** with parent/child nodes
- 🎯 **Automatic tab completion** via optional dynamic suggestion lambdas
- 🧠 **Typed arguments** with validation and suggestion support
- 📚 **Automatic help command** for any subcommand tree
- 🔧 Easy registration with your plugin's `plugin.yml` commands

---

## 📦 Installation
1. Add the cloudsmith repository to your Gradle config

```groovy
repositories {
    maven {
        name = "commander"
        url = "https://dl.cloudsmith.io/qshTFUucaaD2Gctc/lilbrocodes/commander/maven/"
    }
}
```

2. Add as a dependency using Gradle (if you deploy to a maven repo):

```groovy
dependencies {
    implementation 'org.lilbrocodes:commander:<version>'
}
