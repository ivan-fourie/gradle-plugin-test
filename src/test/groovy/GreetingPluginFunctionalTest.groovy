import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class GreetingPluginFunctionalTest extends Specification {
    @TempDir File testProjectDir
    File settingsFile
    File buildFile

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        buildFile = new File(testProjectDir, 'build.gradle')

        //GradleRunner.withDebug(true)
    }

    def "greeting plugin prints 'Hello from the org.example.GreetingPlugin'"() {
        given:
        settingsFile << "rootProject.name = 'hello-plugin'"
        'org.example.greeting'
        buildFile << """         
            plugins {
                id 'org.example.greeting'
            }   
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('helloPlugin')
                .withPluginClasspath()
                .forwardOutput()
                .withDebug(true)
                .build()

        then:
        result.output.contains('Hello from the org.example.GreetingPlugin')
        result.task(":helloPlugin").outcome == SUCCESS
    }
}
