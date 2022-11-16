package org.example

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('helloPlugin') {
            doLast {
                println 'Hello from the org.example.GreetingPlugin'
            }
        }
    }
}
