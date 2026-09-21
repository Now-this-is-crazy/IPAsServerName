plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.3"

tasks.register("chiseledPublishModrinth") {
    group = "publishing"
    description = "Publishes every supported Minecraft version to Modrinth."
    dependsOn(stonecutter.tasks.named("modrinth"))
}
