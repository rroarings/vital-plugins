version = "0.1.0"

project.extra["PluginName"] = "Vital Fisher"
project.extra["PluginDescription"] = "Fishes angler fish only for now"

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
                "Plugin-Dependencies" to
                        arrayOf(
                            nameToId("vital-quester")).joinToString(),
            ))
        }
    }
}
