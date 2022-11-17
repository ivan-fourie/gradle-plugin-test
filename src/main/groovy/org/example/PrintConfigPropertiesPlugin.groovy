package org.example

import org.gradle.api.Plugin
import org.gradle.api.Project

class PrintConfigPropertiesPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('printConfigProperties') {
            def warProjects = project.hasProperty('warProjects') ? project.warProjects.collect{ project.project(":${it}") }
                    : project.subprojects.findResults { it.plugins.hasPlugin("war") ? it : null }

            if (project.hasProperty('configProperty'))
                println project.configProperty
            else
                println 'Could not find property "configProperty"'

            doLast {
                if (project.hasProperty('taskProperty'))
                    println project.taskProperty
                else
                    println 'Could not find property "taskProperty"'
            }
        }
    }
}
