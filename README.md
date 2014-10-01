Lazybones template project
--------------------------

You have just created a simple project for managing your own Lazybones project
templates. You get a build file (`build.gradle`) and a directory for putting
your templates in (`templates`).

To get started, simply create new directories under the `templates` directory
and put the source of the different project templates into them. You can then
package and install the templates locally with the command:

    ./gradlew installAllTemplates

You'll then be able to use Lazybones to create new projects from these templates.
If you then want to distribute them, you will need to set up a Bintray account,
populate the `repositoryUrl`, `repositoryUsername` and `repositoryApiKey` settings
in `build.gradle`, add new Bintray packages in the repository via the Bintray
UI, and finally publish the templates with

    ./gradlew publishAllTemplates

To experiment with a locally installed template, try something like this:

`lazybones create jvm-guy-amqp-micro-service 1.0.0 my-new-project`

You can find out more about creating templates on [the GitHub wiki][1].

To publish an updated version, update the VERSION file and issue `./gradlew publishTemplateJvmGuyAmqpMicroService`.
[1]: https://github.com/pledbrook/lazybones/wiki/Template-developers-guide

To publish to Bintray create `~/.lazybones/config.groovy` so it looks like this:

bintrayRepositories = [
      "kurron/lazybones",
      "kyleboon/lazybones",
      "pledbrook/lazybones-templates"
]

options.logLevel = "INFO"

and then `./gradlew publishAllTemplates`.  You can use the templates by issuing something similar to `lazybones create jvm-guy-amqp-micro-service  amqp` or `lazybones create jvm-guy-rest-api rest`
