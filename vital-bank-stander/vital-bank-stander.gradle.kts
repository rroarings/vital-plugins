version = "0.0.30"

project.extra["PluginName"] = "Vital Bank Stander"
project.extra["PluginDescription"] = "cool stuff"

dependencies {
    compileOnly(project(":vital-quester"))
}

tasks {
    jar {
        manifest {
            attributes(mapOf(
                    "Plugin-Version" to project.version,
                    "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                    "Plugin-Provider" to project.extra["PluginProvider"],
                    "Plugin-Description" to project.extra["PluginDescription"],
                    "Plugin-License" to project.extra["PluginLicense"],
                    "Plugin-Dependencies" to arrayOf(nameToId("vital-quester")).joinToString(),
            ))
        }
    }
}
