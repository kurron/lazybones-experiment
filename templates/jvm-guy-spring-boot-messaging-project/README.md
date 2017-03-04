To refresh the Gradle wrapper script use `gradle wrapper --gradle-version 3.4 --distribution-type all` You need to have a
current version of Gradle installed.

1. Setting `GRADLE_OPTS` and `JAVA_OPTS`
1. Gradle dry run
1. `gradle wrapper --gradle-version 2.0 --distribution-type --gradle-distribution-url http://foo.bar`
1. `org.gradle.daemon=false` only on build machine in `~/.gradle/gradle.properties`
1. `gradle --stop`
1. multi-project build?
1. `rootProject.name` ins `settings.gradle`
1. `gradle build --continuous`
1. ` gradle --include-build ../my-utils run`
1. `gradle.properties`
    * `org.gradle.daemon`
    * `org.gradle.java.home`
    * `org.gradle.jvmargs`
    * `org.gradle.debug`
    * `org.gradle.daemon.performance.enable-monitoring`
1. plugins
    * java
    * groovy
    * maven-publish
    * build-announcements
    * checkstyle
    * codenarc
    * findbugs
    * jdepend
    * pmd
    * project-report
    * jacoco
    * build-dashboard
1. `java-library`
1.     