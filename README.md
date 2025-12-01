# TodoApp
A simple todo app built using spring boot, react, mysql

React app is hosted on netlify

Spring boot is hosted on render

MySQL is hosted on railway app

React URL - https://jolly-licorice-c47c57.netlify.app/

Note:

After making changes in react app, run "npm run build" to build the app. After that create a file called netlify.toml (file name can be anything with extension .toml) and paste the following:

[[redirects]]

    from = "/*"
    to = "/index.html"
    status = 200
