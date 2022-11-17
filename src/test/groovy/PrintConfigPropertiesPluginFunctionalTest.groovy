import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class PrintConfigPropertiesPluginFunctionalTest extends Specification {
    @TempDir File testProjectDir
    File settingsFile
    File buildFile


    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        buildFile = new File(testProjectDir, 'build.gradle')
    }

    def "print-config-properties plugin prints 'Hello from the org.example.PrintConfigPropertiesPlugin'"() {
        given:
        settingsFile << "rootProject.name = 'hello-property-plugin'"
        buildFile << """         
            buildscript {
                ext {
                    configProperty = "This property should by loaded during configuration"
                }
            }
            
            plugins {
                id 'org.example.print-config-properties'
            }
            
            ext {
                taskProperty = "This property should be lazy loaded"
            }     
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('printConfigProperties')
                .withPluginClasspath()
                .forwardOutput()
                .withDebug(true)
                .build()

        then:
        result.output.contains('This property should by loaded during configuration')
        result.output.contains('This property should be lazy loaded')
        result.task(":printConfigProperties").outcome == SUCCESS
    }
}
