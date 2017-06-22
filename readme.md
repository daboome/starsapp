This is a concept-proof StarsApp

To clone the application to your working directory type

    git clone https://github.com/daboome/starsapp.git

To build the application change current directory to starsapp folder

    cd starsapp

And then type

    ./gradlew assemble -DserverAddress=[http://starsback_ip_address]

This would build apk artifact with the ip address of running starsback server

To install application on modile device type wihtin your starsapp directory

    cd app/build/outputs/apk;

And then

    adb install app-debug.apk

CAUTION:
This application requires starback backend server to be started first.