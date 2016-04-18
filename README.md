# Cordova Bug Report Plugin

Simple plugin that takes a bitbucket repository and returns an issueid in a JSON structure. This is based on a ["hello world" plugin](https://github.com/don/cordova-plugin-hello) that we also looked at in class. I have used ionic rather than cordova, so that there is a bit of a user interface.

## Using
Clone the plugin

    $ git clone https://github.com/rhildred/cordovaLogcatBugReport.git

Create a new Ionic Project

    $ ionic start bugreporttest
    
Install the plugin

    $ cd bugreporttest
    $ ionic plugin add ../cordovaLogcatBugReport
    

Edit `www/templates/tab-dash.html` and add the following button `<button ng-click="sendBugReport()">Send Bug Report</button>`. Then in `www/js/controllers.js` add the following code

```js
.controller('DashCtrl', function($scope) {
    $scope.sendBugReport = function(){
        var success = function(message) {
            alert(message);
        }

        var failure = function() {
            alert("Error calling BugReport Plugin");
        }

        bugreport.report("rhildred/logcattest", success, failure);
    };

})
```

Install Android platform

    ionic platform add android
    
Run the code

    ionic run android 

## More Info

For more information on setting up Cordova see [the documentation](http://cordova.apache.org/docs/en/4.0.0/guide_cli_index.md.html#The%20Command-Line%20Interface)

For more info on plugins see the [Plugin Development Guide](http://cordova.apache.org/docs/en/4.0.0/guide_hybrid_plugins_index.md.html#Plugin%20Development%20Guide)
