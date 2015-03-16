    app/                        --> all of the files to be used in production
      assets/                   --> all static asset files
        css/                    --> css files
          app.css               --> default stylesheet
        img/                    --> image files
      bower_components/         --> all client side dependencies
      common/                   --> all components that are shared across the application
        directives/             --> a folder for application level directives
        filters/                --> a folder for application level filters
          timeConversion.js     --> custom filter for converting time
        services/               --> a folder for application level services
        values/                 --> a folder for application level values
          userData.js           --> custom value for tracking the user's data
      modules/                  --> all custom modules, associated by a feature or view
        activityFeed/           --> all components related to the activityFeed module
          activityFeed.html     --> the template rendered for this module
          activityFeed.js       --> the application logic for this module
          activityService.js    --> a service only used by this module
          activityPostDir.html  --> the template rendered for a module specific directive
          activityPostDir.js    --> the application logic for a module specific directive
        login/                  --> a custom module for this application
          login.html            --> the template rendered for this module
          login.js              --> the application logic for this module
          loginService.js       --> a service only used by this module
      app.js                    --> application
      index.html                --> app layout file (the main html template file of the app)
    test/                       --> test config and source files
      protractor-conf.js        --> config file for running e2e tests with Protractor
      e2e/                      --> end-to-end specs
        scenarios.js            --> end-to-end tests
      karma.conf.js             --> config file for running unit tests with Karma
      unit/
        --> Under this folder should be an exact structural copy
            of the app, replacing *.js files with *_test.js testing files

